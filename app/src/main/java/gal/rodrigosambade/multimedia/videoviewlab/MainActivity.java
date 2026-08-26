package gal.rodrigosambade.multimedia.videoviewlab;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_POSITION = "position";
    private VideoView videoView;
    private SeekBar seekBar;
    private TextView info;
    private CheckBox loop;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int restoredPosition;
    private int videoWidth;
    private int videoHeight;

    private final Runnable progressUpdater = new Runnable() {
        @Override public void run() {
            if (videoView != null && videoView.getDuration() > 0) {
                int position = videoView.getCurrentPosition();
                seekBar.setMax(videoView.getDuration());
                seekBar.setProgress(position);
                updateInfo(position, videoView.getDuration());
            }
            handler.postDelayed(this, 250);
        }
    };

    private final ActivityResultLauncher<String[]> openVideo =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
                if (uri != null) {
                    getContentResolver().takePersistableUriPermission(
                            uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    load(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        seekBar = findViewById(R.id.seekBar);
        info = findViewById(R.id.tvInfo);
        loop = findViewById(R.id.cbLoop);
        Button playPause = findViewById(R.id.btnPlayPause);
        Button back = findViewById(R.id.btnBack);
        Button forward = findViewById(R.id.btnForward);
        Button open = findViewById(R.id.btnOpen);

        MediaController controller = new MediaController(this);
        controller.setAnchorView(videoView);
        videoView.setMediaController(controller);

        if (savedInstanceState != null) {
            restoredPosition = savedInstanceState.getInt(KEY_POSITION, 0);
        }

        videoView.setOnPreparedListener(mp -> {
            videoWidth = mp.getVideoWidth();
            videoHeight = mp.getVideoHeight();
            mp.setLooping(loop.isChecked());
            seekBar.setMax(mp.getDuration());
            if (restoredPosition > 0) {
                videoView.seekTo(restoredPosition);
                restoredPosition = 0;
            }
            videoView.start();
        });

        videoView.setOnCompletionListener(mp -> updateInfo(videoView.getDuration(), videoView.getDuration()));

        loop.setOnCheckedChangeListener((buttonView, checked) -> {
            if (videoView.isPlaying()) {
                // VideoView no expone MediaPlayer directamente; se aplicará con certeza en la próxima preparación.
                info.setText("Bucle " + (checked ? "activado" : "desactivado") + " (se aplica al preparar)");
            }
        });

        playPause.setOnClickListener(v -> {
            if (videoView.isPlaying()) videoView.pause();
            else videoView.start();
        });

        back.setOnClickListener(v -> videoView.seekTo(Math.max(0, videoView.getCurrentPosition() - 10_000)));
        forward.setOnClickListener(v -> videoView.seekTo(
                Math.min(videoView.getDuration(), videoView.getCurrentPosition() + 10_000)));
        open.setOnClickListener(v -> openVideo.launch(new String[]{"video/*"}));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) videoView.seekTo(progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        Uri bundled = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample_video);
        load(bundled);
    }

    private void load(Uri uri) {
        videoView.setVideoURI(uri);
        videoView.requestFocus();
    }

    private void updateInfo(int position, int duration) {
        info.setText(TimeFormatter.format(position) + " / " + TimeFormatter.format(duration)
                + "  ·  " + videoWidth + "×" + videoHeight + " px"
                + "  ·  " + (videoView.isPlaying() ? "playing" : "paused"));
    }

    @Override protected void onStart() {
        super.onStart();
        handler.post(progressUpdater);
    }

    @Override protected void onStop() {
        handler.removeCallbacks(progressUpdater);
        super.onStop();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_POSITION, videoView.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }
}

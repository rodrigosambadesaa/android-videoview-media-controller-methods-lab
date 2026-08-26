package gal.rodrigosambade.multimedia.videoviewlab;

final class TimeFormatter {
    private TimeFormatter() {}

    static String format(int millis) {
        int totalSeconds = Math.max(0, millis / 1000);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format(java.util.Locale.ROOT, "%02d:%02d", minutes, seconds);
    }
}

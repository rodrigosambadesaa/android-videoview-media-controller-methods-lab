# VideoView + MediaController methods lab

Laboratorio reconstruido a partir del bloque de reproducción del módulo de Programación Multimedia y
Dispositivos Móviles. A diferencia de los repos que proceden de un enunciado concreto, este repo está
etiquetado como **laboratorio didáctico reconstruido**.

## Métodos y eventos ejercitados

- `VideoView.start()`
- `pause()`
- `isPlaying()`
- `getCurrentPosition()`
- `getDuration()`
- `seekTo()`
- `setVideoURI()`
- `MediaController`
- `MediaPlayer.OnPreparedListener`
- `MediaPlayer.OnCompletionListener`
- tamaño real del vídeo mediante `MediaPlayer.getVideoWidth()/getVideoHeight()`
- bucle opcional con `MediaPlayer.setLooping()`
- restauración de posición tras cambios de configuración

## Selección de vídeo

Se usa `ActivityResultContracts.OpenDocument` para elegir un vídeo mediante el selector del sistema.
Esto evita pedir acceso general a todo el almacenamiento y sirve como contraste con los ejercicios antiguos.

El repositorio incluye además un clip MP4 ligero derivado del vídeo histórico de los ejercicios de repaso,
para poder probar la aplicación sin buscar un archivo externo.

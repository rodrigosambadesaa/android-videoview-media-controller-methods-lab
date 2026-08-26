# Enunciado didáctico reconstruido — VideoView y MediaController

Este repositorio es un **laboratorio reconstruido** a partir del bloque de reproducción multimedia de Programación Multimedia y Dispositivos Móviles; no procede de un único PDF de enunciado independiente.

El objetivo es crear una aplicación Android que permita practicar de forma explícita los métodos y eventos principales de `VideoView` y `MediaController`:

- cargar un vídeo mediante `setVideoURI()`;
- iniciar y pausar con `start()` y `pause()`;
- consultar `isPlaying()`, `getCurrentPosition()` y `getDuration()`;
- desplazarse mediante `seekTo()`;
- asociar un `MediaController`;
- reaccionar a `OnPreparedListener` y `OnCompletionListener`;
- consultar el tamaño real del vídeo mediante `getVideoWidth()` y `getVideoHeight()`;
- activar opcionalmente reproducción en bucle;
- restaurar la posición de reproducción tras cambios de configuración.

El vídeo puede seleccionarse mediante el selector del sistema (`OpenDocument`) y el proyecto incluye un clip didáctico local para permitir pruebas sin depender de una URL externa.

No existe una base de datos asociada.

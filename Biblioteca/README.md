# Sistema de búsqueda de libros para la biblioteca universitaria

Proyecto de Programación III de la Universidad de Las Américas (UDLA). Este segundo avance incluye una aplicación de escritorio desarrollada en Java con interfaz gráfica Swing. Permite registrar libros, agregar ejemplares y consultar en qué estante se encuentra cada copia.

## Integrantes del grupo 6

- Camilo Guzman
- Mathias Quillupangui
- Eybraham Saavedra

## Funciones principales

- **Catálogo:** registra una edición mediante su ISBN-13 y crea su primera copia. Valida el ISBN, detecta registros duplicados y permite agregar ejemplares con un código y una ubicación propios.
- **Búsqueda:** permite combinar filtros por ISBN, título, autor, tema, editorial y año. Cada resultado muestra las copias y sus ubicaciones. También se pueden consultar hasta tres títulos del mismo tema.

La aplicación guarda los datos en memoria durante la sesión. Al cerrarla, se pierden los registros ingresados.

## Cómo ejecutar la aplicación

1. Instala Java 21.
2. Descarga el proyecto y descomprime el archivo ZIP.
3. Abre una terminal en la carpeta `Biblioteca_Progreso_2/Biblioteca`, donde se encuentra `Biblioteca.jar`.
4. Ejecuta el siguiente comando:

```bash
java -jar Biblioteca.jar
```

Se abrirá una ventana con las pestañas **Catálogo** y **Búsqueda**.

## Cómo probar los dos módulos

1. En **Catálogo**, registra el ISBN `9780306406157` con el título `Redes de campus`, autor `Ana Pérez`, tema `Redes`, editorial `Aula`, año `2024` y ubicación `Sistemas A1`.
2. Pulsa **Registrar título**. La aplicación asignará a la primera copia el código `9780306406157-1`. Si vuelves a pulsar el mismo botón, aparecerá el mensaje `ISBN ya registrado.`
3. Cambia la ubicación a `Sistemas A2` y pulsa **Agregar ejemplar**. La segunda copia recibirá el código `9780306406157-2`.
4. En **Búsqueda**, escribe `perez` en el campo de autor y `redes` en el de tema. Pulsa **Buscar**. El resultado mostrará una edición con sus dos copias y sus respectivos estantes.

## Verificación automática

El archivo `pruebas/Verificacion.java` comprueba nueve casos funcionales mediante los botones de la interfaz. Para repetirlos necesitas el **JDK 21**. Desde la misma carpeta donde se encuentra `Biblioteca.jar`, ejecuta:

```bash
mkdir clases
javac -encoding UTF-8 -d clases src/*.java pruebas/Verificacion.java
java -Djava.awt.headless=true -cp clases Verificacion
```

Si la carpeta `clases` ya existe, empieza por el comando `javac`. Los resultados de los casos `VF01` a `VF09` aparecerán en la terminal.
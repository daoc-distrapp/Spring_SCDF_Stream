# Spring_SCDF_Stream
Ejemplo Spring Cloud Data Flow (SCDF): stream con source, processor y sink

### Instalación de SCDF

Para tener un sistema SCDF funcional, utilizando Docker, le recomendamos ver la siguiente serie de videos:

https://www.youtube.com/playlist?list=PLhL1PUqTH4-mbre7rcbQh3HzLIsjFs610

## Ejemplo de stream

Este ejemplo consta de tres programas: source=dirmonitor, processor=filewordcount y sink=wordcountfilesink

Es necesario que se cree el directorio shared en /root/scdf. Recuerde que /root/scdf se mapea al directorio en el cual se arrancó el servicio Docker, en el Host.

### dirmonitor

Es la *source* de la stream. Monitorea el directorio /root/scdf/shared, en el servicio Docker, por cualquier nuevo archivo con extensión .txt, y cuando el evento ocurre, pasa al *processor* el nombre del archivo

### filewordcount

Es el *processor* de la stream, y recibe desde la *source* mensajes, que deben contener el path completo a un archivo de texto

Al recibir el mensaje, abre el archivo, y cuenta la ocurrencia de cada palabra en un Map<String, Integer>. En el Map incluye también una entrada con el path del archivo, para referencia: `__/root/scdf/shared/README.txt=null`

Al terminar, envía el Map, como mensaje, al *sink*

### wordcountfilesink

Es el *sink* de la stream, y recibe desde el *processor* mensajes, que deben contener un Map<String, Integer>.

Al recibir el mensaje, abre el archivo de log: WordCountFileSink.log, en /root/scdf/shared, y añade el contenido del Map

## Instalación del ejemplo en el servidor SCDF

Compile cada uno de los tres ejemplos con `mvn package` y copie los jar al directorio desde donde arrancó el servicio Docker, para que pueda acceder a ellos desde /root/scdf en SCDF

Si no desea compilar puede descargarse los jar desde:

https://github.com/daoc/maven-repository/raw/master/daoc/scdf/stream/dirmonitor/0.0.1-SNAPSHOT/dirmonitor-0.0.1-SNAPSHOT.jar
https://github.com/daoc/maven-repository/raw/master/daoc/scdf/stream/filewordcount/0.0.1-SNAPSHOT/filewordcount-0.0.1-SNAPSHOT.jar
https://github.com/daoc/maven-repository/raw/master/daoc/scdf/stream/wordcountfilesink/0.0.1-SNAPSHOT/wordcountfilesink-0.0.1-SNAPSHOT.jar

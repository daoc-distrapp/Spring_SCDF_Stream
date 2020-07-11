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

Es el *processor* de la stream. Cuando recibe desde la *source* el mensaje, el cual debe contener el path completo 

# Backend JAX-RS (Java API for RESTful Web Services) proyecto DEMO

Necesita de un wildfly 20.x 

Para verificacion simple:

```bash
./mvnw verify
```

Para compilar se incluye el plugins de docker para empaquetar todo el proyecto:

```bash
./mvnw clean package docker:build
```

Para correr solo el contenedor de la aplicacion se debe ejecutar lo siguiente:

```bash
docker run -p 8080:8080 -p 9990:9990 --rm -it ups/demoappdocker
```

Para levantar el contenedor de la base de datos y el de aplicaciones, además de las dependencias; se debe ubicarse en el directorio donde se encuentra el archivo docker-compose.yml y ejecutar

```bash
docker-compose up
```

Para determinas las direcciones IP de los contenedores se debe consultar de la siguiente manera:

```bash
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' srvwildfly
```

Con estas direcciones se debe registrar en la maquina fisica en el archivo host para no tener problemas con validaciones de origenes en las cabeceras HTTP

Se debe acceder al servidor keycloak por medio de la siguiente URL

http://srvwildfly:8080


```
Para hacer una peticion GET de Hola mundo

```bash
curl -L -X GET http://srvwildfly:8080/demoappdocker/rs/hola
```

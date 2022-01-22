echo "========================================================="
echo "COMPILANDO ./mvnw clean package docker:build"
echo "========================================================="
./mvnw clean package docker:build

echo "========================================================="
echo "ARRANCANDO IMAGENES DOCKER docker-compose  up -d"
echo "========================================================="
cd src/main/docker
docker-compose  up -d

echo "========================================================="
echo "log srvwildfly sudo docker logs -f srvwildfly"
echo "========================================================="
#sudo docker logs -f srvwildfly 

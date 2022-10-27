docker build . -t fotos
docker tag fotos us-west1-docker.pkg.dev/cloud-computing-pre/fotos/image
docker push us-west1-docker.pkg.dev/cloud-computing-pre/fotos/image

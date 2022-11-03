sh scripts/gcloud/push-current.sh
sh pull-credentials.sh
gcloud run deploy fotos --image us-west1-docker.pkg.dev/cloud-computing-pre/fotos/image:latest --max-instances=3 --memory=1024Mi --min-instances=1 --port=9000 --region=us-west1 --allow-unauthenticated

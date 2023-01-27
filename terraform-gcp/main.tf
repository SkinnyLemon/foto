terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "3.5.0"
    }
  }
}

provider "google" {
  credentials = file("~/.gc/cloud-computing-pre-5df7ea9a7c2c.json")

  project = "cloud-computing-pre"
  region  = "us-west1"
  zone    = "us-west1-c"
}

#resource "google_app_engine_application" "app" {
#  project     = "cloud-computing-pre"
#  location_id = "us-west1"
#}

resource "google_storage_bucket" "static-site" {
  name          = "cloud-computing-fotos"
  location      = "us-west1"
  storage_class = "REGIONAL"
  force_destroy = true
}

resource "google_cloud_run_service" "default" {
  name     = "fotos"
  location = "us-west1"
  template {
    metadata {
      annotations = {
        "autoscaling.knative.dev/minScale" = 1
        "autoscaling.knative.dev/maxScale" = 3
      }
    }
    spec {
      containers {
        image = "us-west1-docker.pkg.dev/cloud-computing-pre/fotos/image@sha256:8e45ed486a27af04f81514e1b269741bfdcf2794b02f3d6e4cb3dd0d2b529d6a"
        resources {
          limits = {
            "memory" : "1Gi"
          }
        }
        args    = ["-Dhttp.port=8080"]
        command = ["/fotos/bin/fotos"]
      }
    }
  }
  traffic {
    percent         = 100
    latest_revision = true
  }
}

resource "google_cloud_run_service_iam_binding" "default" {
  location = google_cloud_run_service.default.location
  service  = google_cloud_run_service.default.name
  role     = "roles/run.invoker"
  members  = ["allUsers"]
}

🔔 **Notification Service**

End-to-End Development & GitOps Deployment using Java, Docker, Kubernetes, GitHub Actions & Argo CD

📌 **Overview**

This project demonstrates the design, development, containerization, and GitOps-based deployment of a Notification Service built using Java (Spring Boot) and deployed on Kubernetes (Minikube) running on an AWS EC2 Ubuntu instance.

The focus of this project is to showcase:

Backend service development

REST-based inter-service communication

CI/CD automation

Kubernetes deployment

GitOps using Argo CD

🏗️ **System Architecture (REST-based Communication)**

Client
  |
  v
Notification Controller (Spring Boot)
  |
  v
Notification Service
  |
  v
External Service / Mock API

🔹 **Architecture Explanation**

Client sends notification requests via REST API

Spring Boot Controller handles incoming requests

Business logic is processed in the Service layer

Communication with downstream services is done using RestTemplate

Application is containerized using Docker

Deployed to Kubernetes via Argo CD (GitOps)

🧰 **Tech Stack**

Backend

Java 21

Spring Boot

REST APIs

RestTemplate

DevOps & Cloud

Docker

Kubernetes (Minikube)

GitHub Actions (CI)

Argo CD (CD / GitOps)

AWS EC2 (Ubuntu)

🚀 **Features**

REST-based Notification Service

Dockerized Spring Boot application

Kubernetes manifests for deployment

CI pipeline using GitHub Actions

GitOps-based continuous deployment with Argo CD

Declarative Kubernetes configuration

Automated image version updates

🧪 **API Endpoints (Sample)**

POST /notifications/send

Sample Request

{
    "messageId": 123,
    "sender": "no-reply@example.com",
    "recipient": "user@example.com",
    "title": "Welcome",
    "message": "Welcome to the service! Your account is ready.",
    "sentAt": "2025-12-03T14:30:00"
}

Sample Response

{
  "Notification sent"
}

POST /notifications/getMessage/1

Sample Response

{
  "Message found with Id: 1"
}

🐳 **Dockerization**

Build Docker Image
docker build -t notification-service:latest .

Run Locally
docker run -p 8080:8080 notification-service

☸️ **Kubernetes Deployment**

Kubernetes manifests are maintained under:

manifests/


Includes:

Deployment

Service

ConfigMap

Secret

Image tags are dynamically updated during CI/CD.

🔁 **CI/CD Pipeline (GitHub Actions)**

CI Flow

Code pushed to GitHub

GitHub Actions triggered

Maven build & test

Docker image build

Image pushed to container registry

Deployment manifests updated with new image tag

🔄 **GitOps with Argo CD**

Argo CD continuously monitors the GitHub repository and ensures:

Kubernetes cluster state matches Git state

Automatic application sync on manifest changes

Rollbacks via Git history

🖥️ **Setting up Minikube & Argo CD on AWS EC2 (Ubuntu)**

Install Docker

sudo apt update

sudo apt install -y docker.io

sudo usermod -aG docker ubuntu

newgrp docker

docker ps

Install kubectl

curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl

chmod +x kubectl

sudo mv kubectl /usr/local/bin/

kubectl version --client

Install Minikube

curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64

chmod +x minikube-linux-amd64

sudo mv minikube-linux-amd64 /usr/local/bin/minikube

minikube version

Start Minikube

minikube start \
--driver=docker \
--cpus=2 \
--memory=6000

kubectl get nodes

Enable Ingress

minikube addons enable ingress

🚦 Install Argo CD

kubectl create namespace argocd

kubectl apply -n argocd \
-f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

Access Argo CD UI

kubectl port-forward svc/argocd-server -n argocd 8080:443

https://<EC2-PUBLIC-IP>:8080

Get Admin Password

kubectl get secret argocd-initial-admin-secret \
-n argocd -o jsonpath="{.data.password}" | base64 -d

📷 **Screenshots**

<img width="600" height="467" alt="image" src="https://github.com/user-attachments/assets/f77f6cb6-4045-48e8-8796-2fe4d8cfc185" />

Argo CD UI

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/32a261f0-57d9-48ff-80f1-3311fabf4319" />

Kubernetes Resources

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/4af0f7f1-903f-4f9f-b5ca-1918de89e90d" />

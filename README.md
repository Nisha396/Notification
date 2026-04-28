# 🚀 Spring Boot Notification Service on Kubernetes (GitOps with Argo CD)

## 📌 Overview

This project demonstrates deploying a Spring Boot application on Kubernetes using **GitOps principles with Argo CD** and an automated **CI/CD pipeline**. The application is containerized, built via CI, and deployed automatically to Kubernetes.

---

## 🏗️ Architecture

```text
Developer → GitHub → CI Pipeline → Docker Image → Argo CD → Kubernetes Cluster
```

---

## ⚙️ Tech Stack

* Java (Spring Boot)
* Docker
* Kubernetes (Minikube on EC2)
* Argo CD (GitOps)
* CI/CD Pipeline (GitHub Actions / similar)
* AWS EC2

---

## 🔄 CI/CD Pipeline

The CI/CD pipeline automates build and deployment:

### CI (Continuous Integration)

* Code pushed to GitHub triggers pipeline
* Application is built using Maven
* Docker image is created
* Image is pushed to Docker registry

### CD (Continuous Deployment via Argo CD)

* Argo CD watches Git repository
* Detects updated image/tag in manifests
* Automatically syncs changes to Kubernetes cluster

---

## 🔧 Features Implemented

* ✅ Containerized Spring Boot application using Docker
* ✅ Automated CI/CD pipeline for build and deployment
* ✅ GitOps-based deployment using Argo CD
* ✅ Kubernetes Deployment & Service configuration
* ✅ Externalized configuration using ConfigMaps and Secrets
* ✅ Environment variable mapping using Spring Boot conventions
* ✅ Health checks using Actuator endpoints

---

## 🐞 Key Debugging & Fixes

* Fixed **annotation too long error** during Argo CD install
* Resolved **Argo CD OutOfSync issues** due to config drift
* Fixed **port mismatch (80 vs 8080)** in Kubernetes
* Debugged **startup/liveness probe failures**
* Resolved **Hibernate dialect error** by correct env variable mapping
* Fixed **NodePort & Minikube networking issues**
* Enabled **H2 console and actuator endpoints**

---

## 📁 Project Structure
.
├── k8s/
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap.yaml
│   ├── secret.yaml
├── Dockerfile
├── application.properties
├── .github/workflows/   # CI/CD pipeline
└── README.md

---

## 🔐 Configuration Management

### ConfigMap

* Stores non-sensitive configuration (DB URL)

### Secret

* Stores sensitive data (username, password)

### Environment Variables


SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD

---

## 🚀 Deployment Flow

1. Developer pushes code to GitHub
2. CI pipeline builds Docker image
3. Image pushed to registry
4. Argo CD detects changes in manifests
5. Argo CD syncs and deploys to Kubernetes

---

## 🌐 Access Application


kubectl port-forward svc/notification-service -n notification-namespace 8081:80 --address 0.0.0.0

Access:
http://EC2-PUBLIC-IP:8081

---

## ❤️ Health Checks

* `/actuator/health`
* Liveness & Readiness probes configured

---

## 🧪 Testing

* APIs tested using Postman
* Verified endpoints via port-forward and NodePort

---

## 📸 Screenshots

* CI pipeline run (GitHub Actions)
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/a7692c7d-7dbd-40fe-bd8c-85354ed01493" />

* Argo CD dashboard (Synced/Healthy)
  <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/546b08fc-f95d-4afa-bcfa-f77c78754d2f" />

* Kubernetes pods running
  <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/9d709d5a-b1eb-4280-b25c-49f4a89382ea" />

* API response in Postman
  <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/da864b42-c038-4be1-aac3-067b43e31eb5" />

---

## 📈 Future Enhancements

* 🔜 Prometheus & Grafana monitoring
* 🔜 Ingress for domain-based routing
* 🔜 Deployment on AWS EKS

---

## 🎯 Key Learnings

* GitOps workflow using Argo CD
* Kubernetes networking and service exposure
* Debugging real-world deployment issues
* Managing configuration using Secrets and ConfigMaps
* Integrating CI/CD with Kubernetes deployments

---

## 🙌 Conclusion

This project demonstrates a complete DevOps workflow:

* CI pipeline builds and pushes images
* Argo CD handles continuous deployment
* Kubernetes runs and manages the application

---

## 📬 Contact

Feel free to connect for feedback or collaboration!

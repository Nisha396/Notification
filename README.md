# 🚀 Notification Service - End-to-End DevOps Project  

## 📌 Overview

This project demonstrates a complete end-to-end DevOps workflow by deploying a Spring Boot-based notification service on Kubernetes using GitOps principles, along with monitoring and observability.    

## ⚙️ Tech Stack

**Backend**: Spring Boot
**Containerization**: Docker
**Orchestration**: Kubernetes (Minikube on EC2)
**CI/CD**: GitHub Actions
**GitOps**: Argo CD
**Monitoring**: Prometheus
**Visualization**: Grafana

## 🔧 Features Implemented

* ✅ Containerized Spring Boot application using Docker
* ✅ Automated CI/CD pipeline for build and deployment
* ✅ GitOps-based deployment using Argo CD
* ✅ Kubernetes Deployment & Service configuration
* ✅ Externalized configuration using ConfigMaps and Secrets
* ✅ Environment variable mapping using Spring Boot conventions
* ✅ Health checks using Actuator endpoints
* ✅ Prometheus monitoring with ServiceMonitor
* ✅ Grafana dashboard for observability

## 🏗️ Architecture
  
<img width="1536" height="1024" alt="Notification-Service-Architecture" src="https://github.com/user-attachments/assets/a490c67d-42cb-44cd-8ade-7f44cb1430a4" />

## 🚀 Deployment Flow

* Code pushed to GitHub
* GitHub Actions builds Docker image
* Image pushed to Docker Hub
* Argo CD syncs manifests
* Kubernetes deploys application
* Prometheus scrapes metrics
* Grafana visualizes metrics

## ☸️ Kubernetes Resources

* Deployment
* Service (NodePort)
* ConfigMap
* Secret
* HorizontalPodAutoScaler
* ServiceMonitor (for Prometheus)

## 🔐 Environment Configuration

Environment variables are managed using:
* Secrets → for sensitive data (username, password)
* ConfigMap → for application configuration

## 📊 Monitoring Setup

Prometheus
Metrics exposed via: /actuator/prometheus
Scraped using ServiceMonitor

## Grafana Dashboard

The dashboard includes:
✅ Application Health (up)
✅ Requests per second
✅ CPU Usage (container-level)
✅ JVM Memory usage

## 📸 Screenshots

🔹 CI pipeline run (GitHub Actions)

 <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/a7692c7d-7dbd-40fe-bd8c-85354ed01493" />
 

🔹 Argo CD dashboard (Synced/Healthy)

 <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/546b08fc-f95d-4afa-bcfa-f77c78754d2f" />
 

🔹 Kubernetes pods running

 <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/9d709d5a-b1eb-4280-b25c-49f4a89382ea" />
 

🔹 API response in Postman

 <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/da864b42-c038-4be1-aac3-067b43e31eb5" />
 

🔹 Prometheus Targets (UP)

 <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/99657ee6-7a56-45a5-b22e-09f8de5ec6ba" />
 

🔹 Grafana Dashboard

 <img width="1920" height="1080" alt="grafana-dashboard" src="https://github.com/user-attachments/assets/ee6ebb25-0909-4172-a9c0-3abde3ee4969" />
 


## 📈 Sample PromQL Queries

**Application Health**

min(up{namespace="notification-namespace", job="notification-service"})

**Request Rate**

rate(http_server_requests_seconds_count{namespace="notification-namespace", pod=~"notification-deployment-.*"}[1m])

**CPU Usage** 

sum by (pod)(
  rate(container_cpu_usage_seconds_total{
    namespace="notification-namespace",
    pod=~"notification-deployment-.*"
  }[1m])
)

**Memory Usage**

sum(jvm_memory_used_bytes{namespace="notification-namespace})

## 🛠️ Setup Instructions (High-Level)

1. Start Kubernetes Cluster
minikube start
2. Install Argo CD
kubectl apply -n argocd -f <argo-cd-install.yaml>
3. Install Monitoring Stack
helm install monitoring prometheus-community/kube-prometheus-stack
4. Deploy Application via Argo CD
Create Application YAML
Sync via Argo CD UI

## 🔍 Real-World Challenges & Fixes

🔴 1. Argo CD Sync Issues (OutOfSync state)
Problem:
Application showed OutOfSync even after successful deployment.

Cause:
Dynamic environment variables (ConfigMap & Secret) caused drift detection.

Solution:
Used ignoreDifferences in Argo CD Application to ignore env field differences.

🔴 2. Missing argocd-initial-admin-secret
Problem:
Secret not created when installing Argo CD using kubectl apply.

Cause:
Used --server-side apply which skipped some resources.

Solution:
Re-applied manifests using standard kubectl apply without server-side flag.

🔴 3. Startup Probe Failures
Problem:
Pods running but showing startup probe failed.

Cause:
Incorrect port configuration (Spring Boot running on 8080, but defined as 80).

Solution:
Aligned container port with application port (8080).

🔴 4. Service Connectivity Issues
Problem:
Unable to access the application using NodePort from external system.

Cause:
Kubernetes cluster was running inside Minikube on an EC2 instance, which uses a separate network layer.
NodePort was not directly accessible from outside due to networking limitations.

Solution:
Used port forwarding to expose the service externally:

kubectl port-forward -n notification-namespace svc/notification-service 8081:80 --address 0.0.0.0

Then accessed the application via:

http://<EC2-Public-IP>:8081

🔴 5. Prometheus Not Scraping Metrics
Problem:
Metrics not visible in Prometheus targets.

Cause:

Incorrect ServiceMonitor labels
Wrong port name mapping

Solution:

Matched Service labels with ServiceMonitor selector
Used correct port name (not port number)

🔴 6. Grafana Showing No Data
Problem:
Queries working in Prometheus but not in Grafana.

Cause:

Time range mismatch
Incorrect PromQL label filtering
Wrong regex usage (* instead of .*)

Solution:

Increased dashboard time window
Fixed label filters
Used proper regex (=~ with .*)

🔴 7. CPU Metrics Confusion
Problem:
CPU panel showing kubelet/system metrics instead of application metrics.

Cause:
Incorrect PromQL filters and misunderstanding of metric labels.

Solution:

Inspected raw metrics
Removed invalid filters (container, image)
Used correct label-based filtering

🔴 8. Spring Boot Configuration Failure (JPA)
Problem:
Application failed with:

Unable to determine Dialect without JDBC metadata

Cause:
Missing DB configuration when switching to Kubernetes ConfigMap/Secrets.

Solution:
Properly injected DB properties using environment variables.
  
## 🔐 Configuration Management

### ConfigMap

* Stores non-sensitive configuration (DB URL)

### Secret

* Stores sensitive data (username, password)

### Environment Variables


SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD

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
  

## 📈 Future Enhancements

🔔 Add Prometheus alerting rules
🌐 Add Ingress for external access
☁️ Deploy on AWS EKS

---

## 🎯 Key Learnings

* GitOps workflow using Argo CD
* Kubernetes networking and service exposure
* Debugging real-world deployment issues
* Managing configuration using Secrets and ConfigMaps
* Integrating CI/CD with Kubernetes deployments
* Prometheus requires correct label filtering for meaningful metrics
* Grafana queries depend on time range and resolution
* Kubernetes metrics are noisy and require filtering

---

## 🙌 Conclusion

This project demonstrates a complete DevOps workflow:

* CI pipeline builds and pushes images
* Argo CD handles continuous deployment
* Kubernetes runs and manages the application
* GitOps workflows
* Monitoring & observability

---

## 📬 Contact

Feel free to connect for feedback or collaboration!

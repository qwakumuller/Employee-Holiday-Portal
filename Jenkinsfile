pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'echo "Test Branches"'
        withMaven() {
          sh 'mvn test'
        }

      }
    }

    stage('Build') {
      when {
        branch 'master'
      }
      steps {
        withMaven() {
          sh 'mvn clean package'
        }

      }
    }

    stage('Build Docker Image') {
      when {
        branch 'master'
      }
      steps {
        sh 'echo "Building Docker Image"'
        script {
          dockerImage = docker.build "$registry"
        }

      }
    }

    stage('Delivering Docker Image') {
      when {
        branch 'master'
      }
      steps {
        sh 'echo "Delivering Docker Image"'
        script {
          docker.withRegistry('', dockerHubCreds) {
            dockerImage.push("$currentBuild.number")
            dockerImage.push("latest")

          }
        }

      }
    }

    stage('Deploy into Kubernetes') {
      when {
        branch 'master'
      }
      steps {
        sh 'echo "Starting Deployment to Kubernetes"'
        sh 'sed -i "s/%TAG%/$BUILD_NUMBER/g" ./Kubernetes/deployment.yml'
        sh 'cat ./Kubernetes/deployment.yml'
        sh 'echo "Starting Deployment to Kubernetes"'
                        sh 'sed -i "s/%TAG%/$BUILD_NUMBER/g" ./Kubernetes/deployment.yml'
                        sh 'cat ./Kubernetes/deployment.yml'
                        step([$class: 'KubernetesEngineBuilder',
                                              projectId: 'intrepid-memory-344002',
                                              clusterName: 'intrepid-memory-344002-gke',
                                              zone: 'us-central1',
                                              manifestPattern: 'Kubernetes/',
                                              credentialsId: 'intrepid-memory-344002',
                                              verifyDeployments: true
                                        ])
      }
    }


  }
  environment {
    registry = 'project2team4/java'
    dockerHubCreds = 'Docker_Credential'
    dockerImage = ''
    deploymentFile = 'Kubernetes/deployment.yml'
  }
}
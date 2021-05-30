pipeline {
    agent any

    stages {
        stage('init') {
            steps {
                cleanWs()
            }
        }
       stage('checkout') {
           steps {
               git url: 'https://github.com/skjalal/spring-data-batch.git'
               githubNotify status: 'PENDING', description: ''
               sh 'chmod +x /var/jenkins_home/workspace/spring-data-batch/gradlew'
           }
       }
       stage('build') {
           steps {
               echo 'Build Gradle Project'
               sh '/var/jenkins_home/workspace/spring-data-batch/gradlew clean build'
           }
       }
    }
    post {
        success {
            echo 'Build Succeeded'
            githubNotify status: 'SUCCESS', description: ''
        }
        failure {
            echo 'Build was Failed'
            githubNotify status: 'FAILURE', description: ''
        }
    }
 }
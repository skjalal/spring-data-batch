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
               script{
                    env.GIT_COMMIT = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
               }
               githubNotify account: 'skjalal', context: 'continuous-integration/jenkins', sha: "${GIT_COMMIT}", repo: 'spring-data-batch', status: 'PENDING', description: ''
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
            githubNotify account: 'skjalal', context: 'continuous-integration/jenkins', sha: "${GIT_COMMIT}", repo: 'spring-data-batch', status: 'SUCCESS', description: ''
        }
        failure {
            echo 'Build was Failed'
            githubNotify account: 'skjalal', context: 'continuous-integration/jenkins', sha: "${GIT_COMMIT}", repo: 'spring-data-batch', status: 'FAILURE', description: ''
        }
    }
 }
pipeline {
    agent any

    environment {
        JAVA_HOME = "C:\\Program Files\\Java\\jdk-17"
        PATH = "C:\\Program Files\\Java\\jdk-17\\bin;${env.PATH}"
    }

    stages {

        stage('Check Java Version') {
            steps {
                bat 'java -version'
            }
        }

        stage('Build') {
            steps {
                bat 'mvnw.cmd clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }

        failure {
            echo 'Build failed!'
        }
    }
}

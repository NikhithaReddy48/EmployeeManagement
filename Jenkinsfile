pipeline {
    agent any

    tools {
        jdk 'JDK17'
    }

    stages {

        stage('Check Java') {
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
}

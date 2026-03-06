pipeline {
    agent any

    stages {

        stage('Clone Repository') {
            steps {
                git 'https://github.com/NikhithaReddy48/EmployeeManagement.git'
            }
        }

        stage('Build Project') {
            steps {
                bat 'mvnw clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw test'
            }
        }

    }
}

pipeline {
    agent any

    environment {
        JAVA_HOME = "C:\\Program Files\\Java\\jdk-17"
        PATH = "C:\\Program Files\\Java\\jdk-17\\bin;${env.PATH}"
    }

    stages {

        stage('Build Project') {
            steps {
                bat '"%JAVA_HOME%\\bin\\java" -version'
                bat 'mvnw.cmd -Dmaven.compiler.release=17 clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
            }
        }

    }
}

pipeline {
    agent any

    environment {
        JAVA_HOME = "C:\\Program Files\\Java\\jdk-17"
        PATH = "C:\\Program Files\\Java\\jdk-17\\bin;${env.PATH}"
    }

    stages {

        stage('Build Project') {
            steps {
                bat 'java -version'
                bat 'mvnw.cmd clean install -Dmaven.compiler.proc=none -DskipTests'
            }
        }

    }
}

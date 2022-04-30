
pipeline {
    agent any
    
    tools {
        maven 'jenkins-maven'
        jdk 'jenkins-jdk'
        git 'git'
    }

    stages {
        stage('Compile') {
            steps {
                sh 'mvn -B -DskipTests clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
        }
        stage('Build Docker image') {
            steps {
                sh 'mvn -B -DskipTests package'
            }
        }
        stage('Deploy Docker image') {
            steps {
                sh """
                docker run -d --name kubia -p 8080 --rm krishnaramachandran1973/kubia:2.0
                """
            }
        }
    }
}

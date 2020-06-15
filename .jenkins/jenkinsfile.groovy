/**
 * Jenkins Pipeline
 *
 * - Generate app jars
 * - Build app docker images
 * - Start docker-compose WP
 */

pipeline {
    agent any
    tools {
        maven 'maven 3.6.3'
    }
    stages {
        stage('Generate app jars') {
            steps {
                dir('covid-evolution-diff') {
                    echo 'Generating jar and Dockerfile for covid-evolution-diff'
                    sh 'mvn clean package'
                }
                dir('covid-graph-spread') {
                    echo 'Generating jar and Dockerfile for covid-graph-spread'
                    sh 'mvn clean package'
                }
                dir('covid-query') {
                    echo 'Generating jar and Dockerfile for covid-query'
                    sh 'mvn clean package'
                }
                dir('covid-sci-discoveries') {
                    echo 'Generating jar and Dockerfile for covid-sci-discoveries'
                    sh 'mvn clean package'
                }
            }
        }
        stage('Build app docker images') {
            steps {
                dir('covid-evolution-diff') {
                    sh "docker build -t covid-evolution-diff:0.0.1 target/"
                }
                dir('covid-graph-spread') {
                    sh "docker build -t covid-graph-spread:0.0.1 target/"
                }
                dir('covid-query') {
                    sh "docker build -t covid-query:0.0.1 target/"
                }
                dir('covid-sci-discoveries') {
                    sh "docker build -t covid-sci-discoveries:0.0.1 target/"
                }
                dir('system-tests') {
                    sh "docker build -t vandabarataiscte/test-docker:1.0 ."
                }
            }
        }
        stage('Start docker-compose WP') {
            steps {
                sh "docker-compose -f ./docker-compose.yml up -d"
            }
        }
    }

}
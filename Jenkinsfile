pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'
    }

    environment {
        MYSQL_ROOT_LOGIN = credentials('mysql')
    }

    stages {
        stage('Build with Maven') {
            steps {
                sh 'mvn --version'
                sh 'java -version'
                sh 'mvn clean package -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Initialize') {
            steps {
                script {
                    def dockerHome = tool 'mydocker'
                    env.PATH = "${dockerHome}/bin:${env.PATH}"
                }
            }
        }

        stage('Packaging/Pushing image') {
            steps {
                script {
                    docker.withRegistry('https://hub.docker.com', 'dockerhub') {
                        sh 'docker build -t long10112002/springboot:1 .'
                        sh 'docker push long10112002/springboot:1'
                    }
                }
            }
        }

        stage('Deploy MySQL to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'docker image pull mysql:8.0'
                sh 'docker network create dev || echo "this network exists"'
                sh 'docker container stop nong-mysql || echo "this container does not exist" '
                sh 'echo y | docker container prune '
                sh 'docker volume rm nong-mysql-data || echo "no volume"'

                sh "docker run --name nong-mysql --rm --network dev -v nong-mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_LOGIN_PSW} -e MYSQL_DATABASE=shop_restful  -d mysql:8.0 "
                sh 'sleep 20'
                sh "docker exec -i nong-mysql mysql --user=root --password=${MYSQL_ROOT_LOGIN_PSW} < script"
            }
        }

        stage('Deploy Spring Boot to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'docker image pull long10112002/cicd-starter:1.3'
                sh 'docker container stop long10112002-springboot || echo "this container does not exist" '
                sh 'docker network create dev || echo "this network exists"'
                sh 'echo y | docker container prune '

                sh 'docker container run -d --rm --name nong-springboot -p 8081:8080 --network dev long10112002/cicd-starter:1.3'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
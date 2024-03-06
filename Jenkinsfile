pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'
    }

    environment {
        MYSQL_ROOT_LOGIN = credentials('mysql')
        REGISTRY_REPO = 'long10112002'
        IMAGE_HUB = 'springboot'
        BUILD_SCRIPTS_GIT="https://github.com/vietlong1011/cicd-jenkins.git"
        BUILD_SCRIPTS='cicd-jenkins'
        BUILD_HOME='/var/lib/jenkins/workspace'
        NAME_DB = 'shop_restful'
        NAME_MYSQL = 'mysql:8.0'
        CONTAINER_DB = 'nong-mysql'
        EMAIL = 'longlieuliny@gmail.com'
        USER_NAME = '10112002'
       
    }

    stages {

         stage('Checkout: Code') {
          steps {
            sh "mkdir -p $WORKSPACE/repo;\
                git config --global user.email $EMAIL;\
                git config --global user.name $USER_NAME;\
                git config --global push.default simple;\
                git clone $BUILD_SCRIPTS_GIT repo/$BUILD_SCRIPTS"
            sh "chmod -R +x $WORKSPACE/repo/$BUILD_SCRIPTS"
          }
    }
   
        stage('Build with Maven') {
            steps {
                sh 'mvn --version'
                sh 'java -version'
                sh 'mvn clean package -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Packaging/Pushing image') {
            steps {
                script {
                    
                     withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                        sh 'docker build -t ${REGISTRY_REPO}/${IMAGE_HUB}:${BUILD_NUMBER} .'
                        sh 'docker push ${REGISTRY_REPO}/${IMAGE_HUB}:${BUILD_NUMBER}'
                    }
                }
            }
        }

        stage('Deploy MySQL to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'docker image pull $NAME_MYSQL'
                sh 'docker network create dev || echo "this network exists"'
                sh 'docker container stop $CONTAINER_DB || echo "this container does not exist" '
                sh 'echo y | docker container prune '
                sh 'docker volume rm nong-mysql-data || echo "no volume"'

                sh "docker run --name $CONTAINER_DB --rm --network dev -v nong-mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_LOGIN_PSW} -e MYSQL_DATABASE=$NAME_DB  -d $NAME_MYSQL "
                sh 'sleep 20'
                sh "docker exec -i $CONTAINER_DB mysql --user=root --password=${MYSQL_ROOT_LOGIN_PSW} < script"
            }
        }

        stage('Deploy Spring Boot to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'docker image pull ${REGISTRY_REPO}/${IMAGE_HUB}:${BUILD_NUMBER}'
                // sh 'docker container stop long10112002-springboot:2 || echo "this container does not exist" '
                //  thay vi stop ta sẽ xóa container
                sh 'docker container rm -f ${REGISTRY_REPO}/${IMAGE_HUB}:${BUILD_NUMBER}|| echo "this container does not exist" '
                sh 'docker network create dev || echo "this network exists"'
                sh 'echo y | docker container prune '

                sh 'docker container run -d --rm --name nong-$IMAGE_HUB -p 8081:8080 --network dev ${REGISTRY_REPO}/${IMAGE_HUB}:$${BUILD_NUMBER}'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
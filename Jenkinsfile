pipeline {
    agent any

    parameters {
        string(name: 'REPO_URL', defaultValue: 'https://github.com/YOUR_USERNAME/jenkins-final-tests.git', description: 'GitHub repository URL')
        string(name: 'BRANCH_NAME', defaultValue: 'main', description: 'Branch to build')
    }

    environment {
        MAIN_BRANCH = 'main'
    }

    triggers {
        cron('30 5 * * 1') // כל יום שני ב-05:30
        cron('0 14 * * *') // כל יום ב-14:00
    }

    stages {
        stage('Clone repository') {
            steps {
                echo "Cloning repository ${params.REPO_URL} from branch ${params.BRANCH_NAME}"
                checkout([$class: 'GitSCM',
                    branches: [[name: "${params.BRANCH_NAME}"]],
                    userRemoteConfigs: [[url: "${params.REPO_URL}"]]
                ])
            }
        }

        stage('Compile') {
            steps {
                echo 'Starting compilation stage'
                timeout(time: 5, unit: 'MINUTES') {
                    sh 'mvn compile'
                }
                echo 'Compilation stage completed successfully'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Starting test stage'
                timeout(time: 5, unit: 'MINUTES') {
                    sh 'mvn test'
                }
                echo 'Test stage completed successfully'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully ✅'
        }
        failure {
            echo 'Pipeline failed ❌'
        }
    }
}

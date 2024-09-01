pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Build the changes'
                // Simulate a failure for demonstration
                // sh 'exit 1' // Uncomment to simulate failure
            }
        }
        
        stage('Test') {
            steps {
                echo 'Test Application'
            }
        }
        
        stage('Deploy') {
            steps {
                echo 'Deploying the feature'
            }
        }
    }

    post {
        always {
            emailext (
                to: 'mgs.milind@gmail.com',
                subject: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' ${currentBuild.currentResult}",
                body: """<p>Build Result:</p>
                         <p><b>Job:</b> ${env.JOB_NAME} [${env.BUILD_NUMBER}]</p>
                         <p><b>Status:</b> ${currentBuild.currentResult}</p>
                         <p><b>URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
    }
}

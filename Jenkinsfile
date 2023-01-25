pipeline {
    
    agent any
    stages {
        stage('install cdk') {
            steps {
                sh 'npm i -g aws-cdk'
                
           }
        }
        stage('CDK bootstrap') {
            steps {
                withAWS(credentials: 'jenkins-cdk', region: 'us-east-2') {
                      
                          sh 'cdk bootstrap'
                    
                }
                
           }
        }
    
        stage('CDK synth') {
            steps {
                withAWS(credentials: 'jenkins-cdk', region: 'us-east-2') {
                      
                          sh 'cdk synth'
                    
                }
                
           }
        }
    
        stage('CDK deploy') {
            steps {
                withAWS(credentials: 'jenkins-cdk', region: 'us-east-2') {
                      
                          sh 'cdk deploy --require-approval=never'
                    
                }
                
           }
        }

  }
}
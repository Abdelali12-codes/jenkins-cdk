pipeline {
    
    agent any
    stages {
        stage('CDK bootstrap') {
            steps {
                withAWS(credentials: 'jenkins-cdk', region: 'us-east-2') {
                      
                          sh 'cdk bootstrap'
                          echo 'bootstrap'
                    
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

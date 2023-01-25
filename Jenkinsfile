pipeline {
    agent any
    stage('CDK bootstrap') {
        withAWS(credentials: 'jenkins-cdk', region: 'us-east-2') {
              steps {
                  sh 'cdk bootstrap'
              }
        }
      
    }

    stage('CDK synth') {
        withAWS(credentials: 'jenkins-cdk', region: 'us-east-2') {
              steps {
                  sh 'cdk bootstrap'
              }
        }
    }

    stage('CDK deploy') {
        withAWS(credentials: 'jenkins-cdk', region: 'us-east-2') {
              steps {
                  sh 'cdk deploy --require-approval=never'
              }
        }
    }

  
}
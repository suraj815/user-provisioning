pipeline {
    agent none

    environment {
        LOGLEVEL = "info"
        USER_APPROVAL = "reject"
    }

    stages {
        stage('Fetch User Details') {
            steps {
                script { 
                    properties([
                        parameters([
                            choice(
                                choices: ['DEV', 'TST', 'STG'], 
                                name: 'ENVIRONMENT',
                                description: "Chhose the environment for user creation"
                            ),
                            string(
                                defaultValue: '',
                                name: 'optumId', 
                                trim: true,
                                description: "User One Healthcare Id"
                            ),
                            string(
                                defaultValue: '', 
                                name: 'emailId', 
                                trim: true,
                                description: "User email id as registered with One Healthcare Id"
                            ),
                            string(
                                defaultValue: '', 
                                name: 'firstName', 
                                trim: true,
                                description: "User first name"
                            ),
                            string(
                                defaultValue: '', 
                                name: 'lastName', 
                                trim: true,
                                description: "User last name"
                            )
                        ])
                    ])
                }
            }
        }
        stage('Validating Request') {
            steps{
                echo "Validating user provision details here"
            }
        }
        stage('call user provision request'){
            steps{
                echo "optum Id : " + params.optumId;
                echo "email Id : " + params.emailId;
                echo "first name : " + params.firstName;
                echo "last name : " + params.lastName;
            }
        }
        stage('User Business Approval') {
         agent none
         steps {

             echo 'Inside User Business Approval'
             //glApproval message: 'Approve User?', unit: 'HOURS', time: 3, defaultValue: 'Enter approval comments', submitter: 'EDPS_DEV'
             }
         }
        stage('Select Deployment Environment') {
            steps {
                script {
                    switch (params.ENVIRONMENT) {
                        case 'dev':
                                  final String url = "http://localhost:8080/job/Demos/job/maven-pipeline-demo/job/sdkman/2/api/json"

                                  final String response = sh(script: "curl -s $url", returnStdout: true).trim()

                                  echo response
                        break
                    }
                }
            }
        }

    }
}

def determineUserEnvBuildVersion() {

	echo "Build number is ${BUILD_NUMBER}"

	//def version =  params.ENVIRONMENT + "-" + params.User_Id +'-' + "${BUILD_NUMBER}"
	//For some unknown reason, setting artifact version works in maven only when set using a env variable like below


	env.PIPELINE_VERSION = version
}

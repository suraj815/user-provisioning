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
                                name: 'One Healthcare Id', 
                                trim: true,
                                description: "User One Healthcare Id"
                            ),
                            string(
                                defaultValue: '', 
                                name: 'Email Id', 
                                trim: true,
                                description: "User email id as registered with One Healthcare Id"
                            ),
                            string(
                                defaultValue: '', 
                                name: 'First Name', 
                                trim: true,
                                description: "Enter user first name"
                            ),
                            string(
                                defaultValue: '', 
                                name: 'Last Name', 
                                trim: true,
                                description: "Enter user last name"
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
                echo "optum Id : " + params['One Healthcare Id'];
                echo "email Id : " + params['Email Id'];
                echo "first name : " + params['First Name'];
                echo "last name : " + params['Last Name'];
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
                                  final String url = "http://localhost:8080/job/user_param_pipeline/api/json?pretty=true"

                                  final String response = sh(script: "curl -s $url", returnStdout: true).trim()

                                  echo "response = "+ response;
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

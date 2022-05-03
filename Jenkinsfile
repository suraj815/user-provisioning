#!/usr/bin/env groovy

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
                            ),
                            booleanParam(description: 'This is description', name: 'BSC_ALL - Y - ADD/UPDATE')
                        ])
                    ])
                }
            }
        }
        stage('Validating Request') {
            agent any
            steps{
                echo "Validating user provision details here";
                script{
                    def validator = load("./validator.groovy")
                    validator.checkParamInputs()
                }
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
	    stage('User Provision Approval') {
            steps {
                script {
                    CHOICES = ["Approve", "Reject"];
                    def INPUT_PARAMS = input(
                            message: 'Approve Reject User provisioning request',
                            ok: 'Approve',
                            id: 'tag_id',
                            submitter: "ssiyaram",
                            submitterParameter: 'SUBMITTER_RESPONSE',
                            parameters: [
                                        string(name: 'Enter Comments', defaultValue: '', trim: true,description: "Enter Comments for approve/reject. It is mandatory."),
                                        choice(choices: CHOICES, description: 'Select a tag for this build', name: 'ACTION')]
                    )
                    env.USER_APPROVE_REJECT_COMMENT = INPUT_PARAMS['Enter Comments']
                    env.USER_ACTION_TAKEN = INPUT_PARAMS['ACTION']
                    env.APPROVER_USERNAME = INPUT_PARAMS['SUBMITTER_RESPONSE']
                       
                }           
                //echo "${env.YourTag['SUBMITTER_RESPONSE']} took action ${env.YourTag.TAG} on user provision request."
                //echo "Deploying ${env.YourTag}. Have a nice day."
            }
        }
        stage('post user provision approval'){
            steps{
                script{
                    echo "${env.APPROVER_USERNAME} has ${env.USER_ACTION_TAKEN} with comment = ${env.USER_APPROVE_REJECT_COMMENT}"
                }
            }
        }
        
	stage('Select Deployment Environment') {
	    agent any 
            steps {
                script {
                    switch (params.ENVIRONMENT) {
                        case 'DEV':
                            final String url = "http://imccbcp29-msl1:8080/job/user-provision-pipeline/api/json?pretty=true"
                            final String basicAuth = "Authorization: Basic c3NpeWFyYW06c3NpeWFyYW0="
                            final String finalUrl = "\"$basicAuth\" $url"
                            
                            echo "finalUrl = ${finalUrl}"
                            
                            final def (String response, int code) = bat(script: "curl -L -X GET -H $finalUrl -w '\\n%{response_code}'", returnStdout: true).trim().tokenize("\n")
                            echo "code = "+code;
                            echo "response = ${response}"
                                 
                        break
                    }
                }
            }
        }
        stage('User Business Approval') {
         agent none
         steps {

             echo 'Inside User Business Approval'
             //glApproval message: 'Approve User?', unit: 'HOURS', time: 3, defaultValue: 'Enter approval comments', submitter: 'EDPS_DEV'
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

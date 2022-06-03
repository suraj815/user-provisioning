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
                                choices: ['TST', 'STG', 'PROD'], 
                                name: 'ENVIRONMENT',
                                description: "Choose the environment for user creation * "
                            ),
                            validatingString(defaultValue: '', description: 'User One Healthcare Id. It should be from 6 upto 50 characters long', failedValidationMessage: 'Please enter One Healthcare Id', name: 'One Healthcare Id', regex: '^[A-Za-z0-9_-]{6,50}$'),
                            validatingString(defaultValue: '', description: 'User email id as registered with One Healthcare Id.', failedValidationMessage: 'Please enter valid email id', name: 'Email Id', regex: '^([a-z0-9_\\.\\+-]{1,50}+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$'),
                            validatingString(defaultValue: '', description: 'Enter user first name. It should be from 1 upto 50 characters long', failedValidationMessage: 'First Name is not valid. It should be upto 50 characters long', name: 'First Name', regex: '^(?!\s*$).{1,50}$'),
                            validatingString(defaultValue: '', description: 'Enter user last name. It should be from 1 upto 50 characters long ', failedValidationMessage: 'Please enter last name', name: 'Last Name', regex: '^(?!\s*$).{1,50}$'),
                            validatingString(defaultValue: '', description: 'Enter Incident Id. It should be from 1 upto 100 characters long', failedValidationMessage: 'Please enter Incident Id', name: 'Incident Id', regex: '^[A-Za-z0-9_-]{1,100}$')

                            
                        ])
                    ])
                }
            }
        }
        stage('Validating Request') {
            agent any
            steps{
                echo "Validating user provision details here";
		    echo "optum Id : " + params['One Healthcare Id'];
                echo "email Id : " + params['Email Id'];
                echo "first name : " + params['First Name'];
                echo "last name : " + params['Last Name'];
                echo "Bearer Token : " + params['Bearer Token'];
                echo "CLIENT : " + params['CLIENT'];
                echo "Action : " + params['ACTION'];
                echo "ALL CHILD ACCESS : " + params['ALL CHILD ACCESS'];
                echo "Incident Id : " + params['Incident Id'];
                echo "build url : $BUILD_URL";
                echo "Job url : $JOB_URL";
                echo "currentBuild.getBuildCauses() : "+ currentBuild.getBuildCauses();
                echo "prtySk : "+ env.prtySk;
                echo "baseUrl : "+ env.baseUrl;
                script{
                    String baseUrl = "http://sp-edps.optum.com/api/submissions-portal/edps/reporting/v1/";
                    String environment = params["ENVIRONMENT"];
                    echo "env : "+ environment;
                    if("DEV" == environment){
                        baseUrl = "http://sp-edps-dev.optum.com/api/submissions-portal/edps/reporting/v1/";
                    }else if("TST" == environment){
                        baseUrl = "http://sp-edps-tst.optum.com/api/submissions-portal/edps/reporting/v1/";
                    }else if("STG" == environment){
                        baseUrl = "http://sp-edps-stg.optum.com/api/submissions-portal/edps/reporting/v1/";
                    }
                    echo "baseUrl = "+ baseUrl;
                    env.baseUrl = baseUrl;
                    //def secureGroupRef = load("./securegroupDl.groovy");
                    echo "env.baseUrl = "+ env.baseUrl;
                    
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

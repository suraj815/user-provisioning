#!/usr/bin/env groovy
@Library("com.optum.jenkins.pipeline.library@master") _
pipeline {
    agent none

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev','tst'],
            description: 'Environment to be deployed'
        ),

        string(
            defaultValue: '',
            description: 'Provide OnehealthcareId',
            name: 'User_Id'
        ),

        string(
            defaultValue: '',
            description: 'Please provide user email',
            name: 'User_Email'
        )

        string(
            defaultValue: '',
            description: 'Please provide PRTY SK for user needs access',
            name: 'CLIENT_SK'
        )
    }

    environment {
        LOGLEVEL = "info"
        USER_APPROVAL = "reject"
    }

    stages {
        stage ('Validate Inputs') {
            steps {
                script{
                    def validator = load("./validator.groovy")
                    validator.checkParamInputs()
                }
            }
        }
        stage('User Business Approval') {
         agent none
         steps {


             glApproval message: 'Approve User?', unit: 'HOURS', time: 3, defaultValue: 'Enter approval comments', submitter: 'EDPS_DEV'
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

	def version =  params.ENVIRONMENT) + "-" + params.User_Id +'-' + "${BUILD_NUMBER}"
	//For some unknown reason, setting artifact version works in maven only when set using a env variable like below


	env.PIPELINE_VERSION = version
}

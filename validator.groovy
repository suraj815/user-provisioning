#!/usr/bin/env groovy

def checkParamInputs(){
    // Check if all required params are valid
    def missingParams = [];
    if (!params.ENVIRONMENT) missingParams << "ENVIRONMENT"
    if (!params.User_Id) missingParams << "User_Id"
    if (!params.User_Email) missingParams << "User_Email"
    assert missingParams.isEmpty() : reportError("The following Parameters are required; check your Inputs: " + missingParams)

}



def reportError(details){
    error("User provision failed :\n" + details + "\n\n")
}

return this

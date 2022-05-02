#!/usr/bin/env groovy

def checkParamInputs(){
    // Check if all required params are valid
    def missingParams = [];
    if (!params.ENVIRONMENT) missingParams << "ENVIRONMENT"
    if (!params['One Healthcare Id']) missingParams << "One Healthcare Id"
    if (!params['Email Id']) missingParams << "Email Id"
    if (!params['First Name']) missingParams << "First Name"
    if (!params['Last Name']) missingParams << "Last Name"
    assert missingParams.isEmpty() : reportError("The following Parameters are required; check your Inputs: " + missingParams)

}



def reportError(details){
    error("User provision failed :\n" + details + "\n\n")
}

return this

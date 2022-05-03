#!/usr/bin/env groovy
import groovy.json.JsonOutput
def createUserOnboardingRequestBody(params) {
    println("Inside createUserOnboardingRequestBody")
     
    def prtySkReqList = createPrtySkReqList(params['Client Request List'])
    def bodyMap = [:];

    bodyMap.put("optumId", params['One Healthcare Id']);
    bodyMap.put("emailId", params['Email Id']);
    bodyMap.put("firstName", params['First Name']);
    bodyMap.put("lastName", params['Last Name']);
    bodyMap.put("levelOfApprover", createLevelOfApprover(params['Approvers Required']));
    bodyMap.put("prtySkReqList", prtySkReqList);
    
    return JsonOutput.toJson(bodyMap);
}

def createLevelOfApprover(selectedApprovers){
    def approverList = [];
    if(selectedApprovers?.trim()){
        println("inside if = "+ selectedApprovers?.trim());
        String[] approverArray = selectedApprovers.split(",")
        for(String approver : approverArray){
            approverList.add(approver);
        }
    }
    println("approverList = "+ JsonOutput.toJson(approverList));
    return approverList;
}
def createPrtySkReqList(clientSelectedVal){
    def prtySkReqList = [];
    String prtySkReqListUserInput = clientSelectedVal;
    String[] prtySkRequestArray = prtySkReqListUserInput.split(",");
    for(String prtySkRequest: prtySkRequestArray){
        
        def eachPrtySkMap = [:];
        String[] array = prtySkRequest.split("-");
        eachPrtySkMap.put("prtySk", array[0]);
        eachPrtySkMap.put("allChildAccess", array[1]);
        eachPrtySkMap.put("prtySkAction", array[2]);

        prtySkReqList.add(eachPrtySkMap);
        eachPrtySkMap = [];
    } 
    println("prtySkReqList : "+ JsonOutput.toJson(prtySkReqList));
    return prtySkReqList;
}

return this

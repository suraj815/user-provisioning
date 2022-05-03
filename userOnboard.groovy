#!/usr/bin/env groovy

def createUserOnboardingRequestBody(params) {
    println("Inside createUserOnboardingRequestBody")
     
    def prtySkReqList = [];
    String prtySkReqListUserInput = params['Client Request List'];
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
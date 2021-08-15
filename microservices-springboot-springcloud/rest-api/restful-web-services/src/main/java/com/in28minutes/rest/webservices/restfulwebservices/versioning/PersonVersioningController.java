package com.in28minutes.rest.webservices.restfulwebservices.versioning;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {


    // Firt form to control the version of apis --> Twitter
    @GetMapping("/v1/person")
    public PersonV1 personV1(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2(){
        return new PersonV2(new Name("Alexandre", "Rodrigues"));
    }
    // END

    // Second form to control the version of apis through Request Parameters --> Used by Amazon
    // http://localhost:8080/person/param?version=2
    @GetMapping(value ="person/param", params = "version=1")
    public PersonV1 paramV1(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "person/param", params = "version=2")
    public PersonV2 paramV2(){
        return new PersonV2(new Name("Alexandre", "Rodrigues"));
    }
    // END



    // Third form to control version of apis through Header Parameters --> Used by Microsoft
    // http://localhost:8080/person/header
    // Just add in the Request HEADER X-API-VERSION=1 OR 2
    @GetMapping(value ="person/header", headers = "X-API-VERSION=1")
    public PersonV1 headerV1(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "person/header", headers = "X-API-VERSION=2")
    public PersonV2 headerV2(){
        return new PersonV2(new Name("Alexandre", "Rodrigues"));
    }

    // END


    // Fourth form to control version of apis through Produces
    // Media type versioning (a.k.a "content negotiation" or "accept header" --> Used by GitHub
    // Produces is what type of response my api will return for e.g: application/json
    // http://localhost:8080/person/produces
    // Add in the HEADER (ACCEPT=application/vnd.company.app-v1+json)
    @GetMapping(value ="person/produces", produces = "application/vnd.company.app-v1+json")
    public PersonV1 producesV1(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(value = "person/produces", produces = "application/vnd.company.app-v2+json")
    public PersonV2 producesV2(){
        return new PersonV2(new Name("Alexandre", "Rodrigues"));
    }

    //END




}

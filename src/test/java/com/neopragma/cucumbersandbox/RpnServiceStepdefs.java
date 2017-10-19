package com.neopragma.cucumbersandbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import cucumber.api.java8.En;

public class RpnServiceStepdefs implements En {
	
    	private HttpResponse<JsonNode> jsonResponse;
    	private String valuesToPush;
    	private static final String RPN_SERVICE_BASE_URI = "http://rpn-service.herokuapp.com";
    	private static final String EMPTY_STRING = "";
    	private static final String UTF8 = "UTF-8";
    	private static final String slash = "/";

	    public RpnServiceStepdefs() {
     	    Given("^I want to know how to call the RPN service$", () -> {
    		    valuesToPush = EMPTY_STRING;
    	    });
     	    
     	    When("^I invoke the RPN service$", () -> {
				jsonResponse = get(RPN_SERVICE_BASE_URI + valuesToPush);
     	    });
     	    
     	    Then("^I receive usage documentation$", () -> {
     	    	assertTrue(jsonResponse
     	    			.getBody()
     	    			.getObject()
     	    			.getJSONArray("usage")
     	    			.getJSONObject(0)
     	    			.getString("description")
     	    			.startsWith("pass values in postfix order"));
     	    });
     	    
     	    Given("^I push \"([^\"]*)\"$", (String value) -> {
               valuesToPush = "/calc/" + encode(value);
     		});
     	    
     	    Given("^\"([^\"]*)\"$", (String value) -> {
               valuesToPush += slash + encode(value);
     		});

     	    Then("^the result is \"([^\"]*)\"$", (String result) -> {
               assertEquals(result, jsonResponse 
    	    			.getBody()
    	    			.getObject()
   			            .getJSONObject("rpn")
 			            .getString("result"));                        
     		});
    	   
	    }
	    
	    private HttpResponse<JsonNode> get(String uri) {
 	    	try {
				return Unirest.get(uri).asJson();
			} catch (UnirestException e) {
				throw new RuntimeException(e);
			}	    	
	    }
	    
	    private String encode(String value) {
	    	try {
				return URLEncoder.encode(value, UTF8);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
	    }
}

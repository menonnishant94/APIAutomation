package Rest.RestAPI;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class Basics {

	public static void main(String[] args) {
		// Validate if Add Place API is working
		
		//given - All input details
		//when - Submit the API - resource, http method
		//then - Validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Contet-type","application/json")
		.body("{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.383494,\r\n"
				+ "    \"lng\": 33.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Frontline house\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n"
				+ "}\r\n"
				+ "").when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response); // for parsing json
		String placeID = js.getString("place_id");
		
		System.out.println(placeID);
		
		String uAddress = "70 Summer walk, USA";
		//Update address
		given().log().all().queryParam("key", "qaclick123").header("Contet-type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+ placeID + "\",\r\n"
				+ "\"address\":\"" + uAddress + "\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String newaddress = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeID).when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(newaddress); // for parsing json
		String address = js1.getString("address");
		
		System.out.println(address);
		Assert.assertEquals(uAddress, address);
	}
}
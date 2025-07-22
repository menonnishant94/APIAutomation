package Rest.RestAPI;


import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

public class CourseDetails {
	
	@Test
	public void getCourseDetails() {
		String token = TokenGeneration.getToken();
		String response = given().queryParams("access_token", token)
		.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
	}
	
}

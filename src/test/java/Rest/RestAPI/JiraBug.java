package Rest.RestAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraBug {

		public static void main(String[] args) {		
			RestAssured.baseURI="https://menonnishant96.atlassian.net/";		
			String createIssueResponse 	= given().header("Content-Type","application/json")
					.header("Authorization","Basic bWVub25uaXNoYW50OTZAZ21haWwuY29tOkFUQVRUM3hGZkdGMExjcmtGWmdhZVEwazNyeXduaVI5bTdTYURsOE9zM1d3TmlELVdRQzBSRHNhc2NsZmVXeFZzUFl0M0U0N1BpN0hhMG9tOEl6LUVIMlk4YkhhdVJVNWZ3cGpnTV9BVTEzaVZqVEQ2QXZWSEhiNndpd0NRbmRNV3lTRTdUb2Nac1pVNFJ1X2VqbFloZFljNzlFVElBeEtibGJhVHYtZkRLLU5qRXVtVXloV2ladz02NEI3NTlFQQ==")		
					.body("{\n"
					+ "\"fields\": {\n"
					+ "\"project\":\n"			
					+ "{\n"		
					+ "\"key\": \"SCRUM\"\n"		
					+ "},\n"		
					+ "\"summary\": \"Website items are not working- automation Rest Assured\",\n"			
					+ "\"issuetype\": {\n"
					+ "\"name\": \"Bug\"\n"	
					+ "}\n"
					+ "}\n"				
					+ "}").log().all()
					.post("rest/api/3/issue").then().log().all().assertThat().statusCode(201).contentType("application/json").extract().response().asString();		 		 
			JsonPath js = new JsonPath(createIssueResponse);		 
			String issueId = js.getString("id");		 
			System.out.println(issueId);		 		 
			given().pathParam("key", issueId).header("X-Atlassian-Token","no-check")	
			.header("Authorization","Basic bWVub25uaXNoYW50OTZAZ21haWwuY29tOkFUQVRUM3hGZkdGMExjcmtGWmdhZVEwazNyeXduaVI5bTdTYURsOE9zM1d3TmlELVdRQzBSRHNhc2NsZmVXeFZzUFl0M0U0N1BpN0hhMG9tOEl6LUVIMlk4YkhhdVJVNWZ3cGpnTV9BVTEzaVZqVEQ2QXZWSEhiNndpd0NRbmRNV3lTRTdUb2Nac1pVNFJ1X2VqbFloZFljNzlFVElBeEtibGJhVHYtZkRLLU5qRXVtVXloV2ladz02NEI3NTlFQQ==")			
			.multiPart("file",new File("C:\\Users\\user\\Downloads\\Capture.PNG")).log().all()
			.post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);			
	 			 		 		 	 		 		 		 		 							
	}
}

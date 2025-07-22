package Rest.RestAPI;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import JsonFiles.jsonFile;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DynamicJson {

	@Test
	public void addBook() throws IOException {
		String responseJson = RestAssured.baseURI ="http://216.10.245.166";
		given().log().all().header("Content-type","application/json")
		.body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"\\src\\test\\java\\JsonFiles\\book.json"))))
		.when().post("Library/Addbook.php")
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(responseJson);
		String id = js.getString("ID");
		System.out.println(id);
	}
	
	@DataProvider(name="books")
	public Object[][] getData() {
		return new Object[][] {{"aaa","963"}, {"bbb","962"},{"ccc","961"}};
	}
}

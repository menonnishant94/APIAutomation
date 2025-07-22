package ECommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class ECommerceAPITest {
	
	public static void main(String[] args) {
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		LoginRequest login = new LoginRequest();
		
		login.setUserEmail("nm@gmail.com");
		login.setUserPassword("Nish@1994");
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(login);
		
		LoginResponse loginres = reqLogin.when().post("api/ecom/auth/login").then().log().all().extract()
				.response().as(LoginResponse.class);
		String token = loginres.getToken();
		String userID = loginres.getUserId();
		String message = loginres.getMessage();
		
		System.out.println(token);
		System.out.println(userID);
		System.out.println(message);
		
		//Add Product
		RequestSpecification addProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		
		RequestSpecification addProductreq=given().log().all().spec(addProduct).param("productName", "Laptop")
		.param("productAddedBy", userID).param("productCategory", "Fashion")
		.param("productSubCategory", "shirts").param("productPrice", "1000")
		.param("productDescription", "HP").param("productFor", "Anyone")
		.multiPart("productImage",new File("C:\\Users\\user\\Downloads\\Capture.png"));
		
		String response = addProductreq.when().post("api/ecom/product/add-product").then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String pID= js.get("productId");
		System.out.println(pID);
		
		//Create Order
		RequestSpecification createOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.setContentType(ContentType.JSON).build();
		
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderId(pID);
		List<OrderDetails> oDetails= new ArrayList<OrderDetails>();
		oDetails.add(orderDetails);
		
		Orders orders = new Orders();
		orders.setOrders(oDetails);
		
		RequestSpecification createOrderReq = given().log().all().spec(createOrderBase).body(orders);
		String response1 = createOrderReq.when().log().all().post("api/ecom/order/create-order").then().log().all().extract().response().asString();
		
		//Delete Product
		RequestSpecification deleteProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		RequestSpecification deleteReq = given().log().all().spec(deleteProduct).pathParam("productId", pID);
		String response2 = deleteReq.when().delete("api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(response2);
		String msg= js1.get("message");
		Assert.assertEquals("Orders Deleted Successfully.", msg);
	}

}

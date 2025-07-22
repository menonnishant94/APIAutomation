package Rest.RestAPI;

import org.testng.Assert;

import JsonFiles.jsonFile;
import io.restassured.path.json.JsonPath;

public class ComplexJson {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(jsonFile.prices());
		//1. Print No of courses returned by API
		//2. Print Purchase amount
		//3. Print title of First Course
		//4. Print all course titles and their respective prices
		//5. Print no of copies sold by RPA
		//6. Verify if sum of all copies matches with purchase amount
		
		//1. Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//2. Print Purchase amount
		int pcount = js.getInt("dashboard.purchaseAmount");
		System.out.println(pcount);
		
		//3. Print title of First Course
		String ctitle = js.getString("courses[0].title");
		System.out.println(ctitle);
		
		//4. Print all course titles and their respective prices
		for(int i=0;i<count;i++) {
			String ctitles = js.getString("courses["+i+"].title");
			String prices = js.getString("courses["+i+"].price");
			System.out.println(ctitles);
			System.out.println(prices);
		}
		
		//5. Print no of copies sold by RPA
		for(int i=0;i<count;i++) {
			String ctitles = js.getString("courses["+i+"].title");
			if(ctitles.equalsIgnoreCase("RPA")) {
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		
		//6. Verify if sum of all copies matches with purchase amount
		int total  = 0;
		for(int i=0;i<count;i++) {
			int prices = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			total = total + (prices * copies);
			System.out.println(total);
		}
		pcount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(pcount, total);
	}

}

package ECommerce;

import java.io.IOException;
import java.util.ArrayList;

public class TestDataSampleTest {

	public static void main(String[] args) throws IOException {
		ReadExcelData d=new ReadExcelData();
		ArrayList data=d.getData("Profile");
		
		System.out.println(data.get(0));
		System.out.println(data.get(1));
		System.out.println(data.get(2));
		System.out.println(data.get(3));


	}

}

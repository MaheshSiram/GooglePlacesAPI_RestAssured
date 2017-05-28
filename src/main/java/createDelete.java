import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.Test;

public class createDelete {

	@Test
	public void createAndDelete() {
		
		String placeAdd=("{"
						+ "\"location\": {"
						+ "\"lat\": -33.8669710,"
						+ "\"lng\": 151.1958750"
						+ "},"
						+ "\"accuracy\": 50,"
						+ "\"name\": \"Google Shoes!\","
						+ "\"phone_number\": \"(02) 9374 4000\","
						+ "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","
						+ "\"types\": [\"shoe_store\"],"
						+ "\"website\": \"http://www.google.com.au/\","
						+ "\"language\": \"en-AU\"" + "}");

		RestAssured.baseURI = "https://maps.googleapis.com";

		Response res = given().

		//Task-1 = Grab the response
				queryParam("key", "** Google key here **")
				.body(placeAdd).

				when().post("/maps/api/place/add/json").then().assertThat()
				.statusCode(200).and().contentType(ContentType.JSON).and()
				.body("status", equalTo("OK")).
		extract().response();
		
		//Task-2 Grap the place_ID
		String responseString = res.asString();
		System.out.println(responseString);
		JsonPath path = new JsonPath(responseString);
		String placeid = path.get("place_id");
		System.out.println(placeid);
		
		//Task-3 place this place_id in the Delete request
		given().
		queryParam("key", "** Google key here **").
		body("{"+
  "\"place_id\": \""+placeid+"\""+
"}").
	when().
	post("/maps/api/place/delete/json").
	then().assertThat()
	.statusCode(200).and().contentType(ContentType.JSON).and()
	.body("status", equalTo("OK"));
		

	}

}

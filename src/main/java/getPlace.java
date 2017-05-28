import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class getPlace {
	
	@Test
	public void Rest(){
		
		// BaseURI
	RestAssured.baseURI="https://maps.googleapis.com";
				
		       given().
						param("location","-33.8670522,151.1957362").
						param("radius","500").
						param("key","** Google key here **").
				when().
						get("/maps/api/place/nearbysearch/json").
				then().
				        assertThat().statusCode(200).and().contentType(ContentType.JSON).
				body("results[3].vicinity",equalTo("70 Murray Street, Pyrmont")).
				body("results[0].name",equalTo("Sydney")).
				body("results[0].place_id",equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM"));
		
		
	}

}

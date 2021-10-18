package interview

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

//Just a POGO to hold the data from our example json objects
@Introspected
public class Book {

	static contraints = {
		
	}

	@JsonProperty("userId")
	private Integer userId;

	@JsonProperty("title")
	private String title;

	@JsonProperty("body")
	private String body;

}

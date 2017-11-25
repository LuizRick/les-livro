package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;

public final class JsonUtils {
	
	private JsonUtils() {}
	
	public static String toJson(Object value) throws JsonGenerationException, JsonProcessingException, IOException {
		JacksonObjectMapper mapper = new JacksonObjectMapper();
		return mapper.writeValueAsString(value);
	}
}

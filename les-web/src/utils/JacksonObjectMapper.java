package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 1L;

	public JacksonObjectMapper() {
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
}

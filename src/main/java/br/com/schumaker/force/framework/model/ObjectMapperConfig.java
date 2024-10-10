package br.com.schumaker.force.framework.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * The ObjectMapperConfig class.
 * It is responsible for configuring the object mapper.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ObjectMapperConfig {
    private static final ObjectMapper INSTANCE = createObjectMapper();

    private ObjectMapperConfig() {}

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }
}
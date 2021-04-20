package io.velog.dragontiger.demorestapi.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

/**
 *  ObjectMapper 전용, org.springframework.validation.Errors 직렬화.
 */
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors errors, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeFieldName("errors");
        generator.writeStartArray();

        errors.getFieldErrors().forEach((error) -> {
            try {
                generator.writeStartObject();
                generator.writeObjectField("objectName", error.getObjectName());
                generator.writeObjectField("field", error.getField());
                generator.writeObjectField("code", error.getCode());
                generator.writeObjectField("defaultMessage", error.getDefaultMessage());
                Object rejectedValue = error.getRejectedValue();
                if (rejectedValue != null) generator.writeObjectField("rejectedValue", rejectedValue);
                generator.writeEndObject();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        errors.getGlobalErrors().forEach((error)-> {
            try {
                generator.writeStartObject();
                generator.writeObjectField("objectName", error.getObjectName());
                generator.writeObjectField("code", error.getCode());
                generator.writeObjectField("defaultMessage", error.getDefaultMessage());
                generator.writeEndObject();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        generator.writeEndArray();
    }
}

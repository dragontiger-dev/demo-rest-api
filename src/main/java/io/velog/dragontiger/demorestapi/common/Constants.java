package io.velog.dragontiger.demorestapi.common;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

public class Constants {
    String mediaTypes = MediaTypes.HAL_JSON_VALUE;

    public static final MediaType HAL_JSON_UTF8 = new MediaType(MediaTypes.HAL_JSON, StandardCharsets.UTF_8);
    public static final String HAL_JSON_UTF8_VALUE = HAL_JSON_UTF8.toString();
}

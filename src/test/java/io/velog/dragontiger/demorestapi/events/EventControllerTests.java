package io.velog.dragontiger.demorestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createTest() throws Exception {

        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development With Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 3, 29, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 4, 29, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 3, 30, 17, 0))
                .endEventDateTime(LocalDateTime.of(2021, 3, 30, 18, 0))
                .basePrice(1000)
                .maxPrice(10000)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event))
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").exists())
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("offline").exists())
                .andExpect(jsonPath("offline").value(Matchers.not(true)));
    }

    @Test
    public void createTestBadRequest() throws Exception {

        Event event = Event.builder()
                .id(10)
                .name("Spring")
                .description("REST API Development With Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 3, 29, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 4, 29, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2021, 3, 30, 17, 0))
                .endEventDateTime(LocalDateTime.of(2021, 3, 30, 18, 0))
                .basePrice(1000)
                .maxPrice(10000)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁")
                .free(true)
                .offline(true)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event))
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}

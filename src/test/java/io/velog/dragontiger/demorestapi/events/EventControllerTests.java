package io.velog.dragontiger.demorestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.velog.dragontiger.demorestapi.common.Constants;
import io.velog.dragontiger.demorestapi.common.RestDocConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfiguration.class)
public class EventControllerTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Nested
    @DisplayName("Event 생성 : \"POST /api/events\"")
    class CreateEvent {

        @BeforeEach
        public void printTestDescription(TestInfo testInfo) {
        }

        @Test
        @DisplayName("Event 정상 등록")
        public void createEvent(TestInfo testInfo) throws Exception {

            EventDto event = EventDto.builder()
                    .name("Spring")
                    .description("REST API Development With Spring")
                    .beginEnrollmentDateTime(LocalDateTime.of(2021, 3, 29, 0, 0))
                    .closeEnrollmentDateTime(LocalDateTime.of(2021, 4, 29, 0, 0))
                    .beginEventDateTime(LocalDateTime.of(2021, 5, 30, 17, 0))
                    .endEventDateTime(LocalDateTime.of(2021, 5, 30, 18, 0))
                    .basePrice(1000)
                    .maxPrice(10000)
                    .limitOfEnrollment(100)
                    .location("강남역 D2 스타텁")
                    .build();

            mockMvc.perform(post("/api/events")
                    .contentType(Constants.HAL_JSON_UTF8)
                    .characterEncoding("utf-8")
                    .content(objectMapper.writeValueAsString(event))
                    .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(mvcResult -> {
                        byte[] byteArray = mvcResult.getResponse().getContentAsByteArray();

                    })
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(header().exists(HttpHeaders.LOCATION))
                    .andExpect(header().string(HttpHeaders.CONTENT_TYPE, Constants.HAL_JSON_UTF8_VALUE))
                    .andExpect(jsonPath("id").value(Matchers.not(100)))
                    .andExpect(jsonPath("free").value(Matchers.is(false)))
                    .andExpect(jsonPath("offline").value(Matchers.is(true)))
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.query-events").exists())
                    .andExpect(jsonPath("_links.update-event").exists())
                    .andDo(document("create-event",
                            links(
                                    linkWithRel("self").description("link to self"),
                                    linkWithRel("query-events").description("link to query-events"),
                                    linkWithRel("update-event").description("link to update an existing event")
                            ),
                            requestHeaders(
                                    headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                            ),
                            requestFields(
                                    fieldWithPath("name").description("이벤트 명"),
                                    fieldWithPath("description").description("이벤트 설명"),
                                    fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작 일시"),
                                    fieldWithPath("closeEnrollmentDateTime").description("이벤트 등록 종료 일시"),
                                    fieldWithPath("beginEventDateTime").description("이벤트 시작 일시"),
                                    fieldWithPath("endEventDateTime").description("이벤트 종료 일시"),
                                    fieldWithPath("location").description("위치"),
                                    fieldWithPath("basePrice").description("기본 가격"),
                                    fieldWithPath("maxPrice").description("최대 가격"),
                                    fieldWithPath("limitOfEnrollment").description("등록 한도")
                            ),
                            responseHeaders(
                                    headerWithName(HttpHeaders.LOCATION).description("accept header"),
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                            ),
                            // relaxedResponseFields(   // 필요한 필드만 검증
                            responseFields(             // 모든 필드 검증
                                    fieldWithPath("id").description("이벤트 ID"),
                                    fieldWithPath("name").description("이벤트 명"),
                                    fieldWithPath("description").description("이벤트 설명"),
                                    fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작 일시"),
                                    fieldWithPath("closeEnrollmentDateTime").description("이벤트 등록 종료 일시"),
                                    fieldWithPath("beginEventDateTime").description("이벤트 시작 일시"),
                                    fieldWithPath("endEventDateTime").description("이벤트 종료 일시"),
                                    fieldWithPath("location").description("위치"),
                                    fieldWithPath("basePrice").description("참가비 기본 가격"),
                                    fieldWithPath("maxPrice").description("참가비 최대 가격"),
                                    fieldWithPath("limitOfEnrollment").description("등록 한도"),
                                    fieldWithPath("free").description("참가비 무료 여부"),
                                    fieldWithPath("offline").description("오프라인 여부"),
                                    fieldWithPath("eventStatus").description("이벤트 상태"),
                                    fieldWithPath("_links.self.href").description("self 링크"),
                                    fieldWithPath("_links.query-events.href").description("이벤트 목록 조회 링크"),
                                    fieldWithPath("_links.update-event.href").description("이벤트 갱신 링크")
                            )
                    ));
        }

        @Test
        @DisplayName("입력 받을 수 없는 값이 포함될 경우 에러 발생")
        public void createEventBadRequest() throws Exception {

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

        @Test
        @DisplayName("빈 값이 포함된 요청을 보낼 경우 에러 발생")
        public void createEventBadRequestEmptyInput() throws Exception {

            EventDto eventDto = EventDto.builder()
                    .build();

            mockMvc.perform(post("/api/events")
                    .contentType(MediaTypes.HAL_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("틀린 값이 포함된 경우 에러 발생")
        public void createEventBadRequestWrongInput() throws Exception {

            EventDto eventDto = EventDto.builder()
                    .name("Spring")
                    .description("REST API Development With Spring")
                    .beginEnrollmentDateTime(LocalDateTime.of(2021, 4, 29, 0, 0))
                    .closeEnrollmentDateTime(LocalDateTime.of(2021, 3, 29, 0, 0))
                    .beginEventDateTime(LocalDateTime.of(2021, 3, 30, 19, 0))
                    .endEventDateTime(LocalDateTime.of(2021, 3, 30, 17, 0))
                    .basePrice(10000)
                    .maxPrice(1000)
                    .limitOfEnrollment(100)
                    .location("강남역 D2 스타텁")
                    .build();

            mockMvc.perform(post("/api/events")
                    .contentType(MediaTypes.HAL_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$[0].objectName").exists())
                    .andExpect(jsonPath("$[0].code").exists())
                    .andExpect(jsonPath("$[0].defaultMessage").exists());
                    // .andExpect(jsonPath("$[0].field").exists())
                    // .andExpect(jsonPath("$[0].rejectedValue").exists())
        }
    }
}

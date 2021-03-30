package io.velog.dragontiger.demorestapi.events;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Spring REST API Practice")
                .description("REST API development with Spring")
                .build();

        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {

        // Given
        String name = "Event";
        String description = "Spring";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    private static Stream<Arguments> updateFreeParams() {
        return Stream.of(
                Arguments.of(0, true),
                Arguments.of(10000, false)
        );
    }

    @ParameterizedTest
    @MethodSource("updateFreeParams")
    void updateFree(int price, boolean result) {

        // Given
        Event event = Event.builder()
                .basePrice(price)
                .maxPrice(price)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(result);
    }

    private static Stream<Arguments> updateOfflineParams() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("  ", false),
                Arguments.of(null, false),
                Arguments.of("홍대입구역 2번출구", true)
        );
    }

    @ParameterizedTest
    @MethodSource("updateOfflineParams")
    void updateOffline(String location, boolean result) {

        // Given
        Event event = Event.builder()
                .location(location)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isEqualTo(result);
    }
}
package io.velog.dragontiger.demorestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors) {

        // 가격 유효성
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
            errors.rejectValue("basePrice", "wrongValue", "basePrice is wrong.");
            errors.rejectValue("maxPrice", "wrongValue", "maxPrice is wrong.");
            errors.reject("priceError", "prices are wrong.");
        }

        /*
         * 등록 시작 일시 < 등록 종료 일시 < 행사 시작 일시 < 행사 종료 일시
         */
        LocalDateTime beginEnrollmentDateTime = eventDto.getBeginEnrollmentDateTime();
        LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
        LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();

        // 등록 시작 일시 검사
        if (beginEnrollmentDateTime.isAfter(closeEnrollmentDateTime) ||
            beginEnrollmentDateTime.isAfter(beginEventDateTime) ||
            beginEnrollmentDateTime.isAfter(endEventDateTime)) {
            errors.rejectValue("beginEnrollmentDateTime", "wrongValue", "beginEnrollmentDateTime is wrong.");
        }

        // 등록 종료 일시 검사
        if (closeEnrollmentDateTime.isAfter(beginEventDateTime) ||
            closeEnrollmentDateTime.isAfter(endEventDateTime)) {
            errors.rejectValue("closeEnrollmentDateTime", "wrongValue", "closeEnrollmentDateTime is wrong.");
        }

        // 행사 시작 일시 검사
        if (beginEventDateTime.isAfter(endEventDateTime) ) {
            errors.rejectValue("beginEventDateTime", "wrongValue", "beginEventDateTime is wrong.");
        }
    }
}

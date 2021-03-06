package io.velog.dragontiger.demorestapi.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.velog.dragontiger.demorestapi.accounts.Account;
import io.velog.dragontiger.demorestapi.accounts.AccountSerializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    private LocalDateTime beginEnrollmentDateTime;

    private LocalDateTime closeEnrollmentDateTime;

    private LocalDateTime beginEventDateTime;

    private LocalDateTime endEventDateTime;

    private String location; // (optional) 이게 없으면 온라인 모임

    private int basePrice; // (optional)

    private int maxPrice; // (optional)

    private int limitOfEnrollment;

    @Setter(AccessLevel.NONE)
    private boolean offline;

    @Setter(AccessLevel.NONE)
    private boolean free;

    @Enumerated(EnumType.STRING) @Builder.Default
    private EventStatus eventStatus = EventStatus.DRAFT;

    @ManyToOne
    @JsonSerialize(using = AccountSerializer.class)
    private Account manager;

    public void update() {
        this.free = (this.basePrice == 0 && this.maxPrice == 0);
        this.offline = !(this.location == null || this.location.isBlank());
    }
}

package com.homework.travel.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "travels")
@DynamicUpdate
public class Travel {

    @Id
    // @GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;

    @Column(nullable = false)
    private String travelname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private City city;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate; // 여행시작 시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate; // 여행 종료 시간

    // 생성 메서드
    public static Travel planTravel(City city, Users user, String travelname, LocalDateTime startDate,
            LocalDateTime endDate) {

        Travel travel = new Travel();
        travel.setCity(city);
        travel.setUser(user);
        travel.setTravelname(travelname);
        travel.setStartDate(startDate);
        travel.setEndDate(endDate);

        return travel;
    }
}

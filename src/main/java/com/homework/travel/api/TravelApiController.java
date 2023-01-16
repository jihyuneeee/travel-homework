package com.homework.travel.api;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.homework.travel.domain.entity.Travel;
import com.homework.travel.service.TravelService;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TravelApiController {

    @Autowired
    TravelService travelService;

    /**
     * 여행 등록 API
     */
    @PostMapping("/api/travel")
    public CreateTravelResponse planTravel(@RequestBody @Valid CreateTravelRequest requset) {

        LocalDateTime now = LocalDateTime.now();

        // 여행 종료일은 미래시점
        if (!requset.getEndDate().isAfter(now)) {

            // 데이터

        }

        Long id = travelService.planTravel(requset.getCityId(), requset.getUserId(), requset.getTravelname(),
                requset.getStartDate(),
                requset.getEndDate());

        return new CreateTravelResponse(id);

    }

    /**
     * 수정 API
     */
    @PutMapping("/api/travel/{id}")
    public UpdateTravelResponse updateCity(@PathVariable("id") Long id,
            @RequestBody @Valid UpdateTravelRequest request) {

        travelService.update(id, request.getCityId(), request.getStartDate(), request.getEndDate());
        // Travel findCity = travelService.findOne(id);
        // return new UpdateCityResponse(findCity.getId(), findCity.getCityname());
        return null;
    }

    /**
     * 단일 조회 API
     */
    @GetMapping("/api/travel/{id}")
    public TravelDto selectTravel(@PathVariable("id") Long id) {
        System.out.println("########################");
        Travel travel = travelService.findOne(id);
        TravelDto dto = new TravelDto(id, travel.getStartDate(), travel.getEndDate());
        System.out.println("########################");

        return dto;
    }

    @DeleteMapping("/api/travel/{id}")
    public void deleteTravel(@PathVariable("id") Long id) {
        travelService.deleteTravel(id);

    }

    @Data
    @AllArgsConstructor
    static class TravelDto {

        private Long id;

        // private User user;

        // private City city;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startDate; // 여행시작 시간

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endDate; // 여행 종료 시간
    }

    @Data
    static class UpdateTravelRequest {

        private Long cityId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endDate;
    }

    @Data
    static class UpdateTravelResponse {

    }

    @Data
    static class CreateTravelRequest {

        private Long cityId;

        private String travelname;

        private Long userId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime endDate;
    }

    @Data
    static class CreateTravelResponse {

        private Long id;

        public CreateTravelResponse(Long id) {
            this.id = id;
        }
    }
}

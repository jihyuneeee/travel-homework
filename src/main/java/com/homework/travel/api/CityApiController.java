package com.homework.travel.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.homework.travel.domain.entity.City;
import com.homework.travel.domain.entity.Travel;
import com.homework.travel.service.CityService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.util.stream.Collectors.toList;

@RestController
public class CityApiController {

    @Autowired
    CityService cityService;

    /**
     * 도시 등록 API
     */
    @PostMapping("/api/city")
    public CreateCityResponse saveCity(@RequestBody @Valid CreateCityRequest requset) {

        City city = new City();
        city.setCityname(requset.getCityname());

        // LocalDateTime now = LocalDateTime.now();
        // // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
        // // HH:mm:ss");
        // // System.out.println("now : " + now.format(formatter));
        // city.setRegDate(now);

        Long id = cityService.register(city);
        return new CreateCityResponse(id);

    }

    /**
     * 도시 수정 API
     */
    @PutMapping("/api/city/{id}")
    public UpdateCityResponse updateCity(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {
        cityService.updateCity(id, request.getCityname());
        City findCity = cityService.findOne(id);
        return new UpdateCityResponse(findCity.getId(), findCity.getCityname());
    }

    /**
     * 단일 도시 조회 API
     */
    @GetMapping("/api/city/{id}")
    public CityDto selectCity(@PathVariable("id") Long id) {

        CityDto dto = new CityDto();
        City city = cityService.findCity(id);

        if (city != null) {
            cityService.updateInqryDate(city); // 조회 시간 업데이트
            dto.setCityname(city.getCityname());// Entity 직접 노출을 피하기 위해
        }

        return dto;

    }

    /**
     * 도시 삭제 API
     * 
     * @param id
     */
    @DeleteMapping("/api/city/{id}")
    public void deleteCity(@PathVariable("id") Long id) {
        cityService.deleteCity(id);

    }

    /**
     * 사용자별 도시 목록 조회 API
     */
    @GetMapping("/api/city/list/{id}")
    public List<CityListDto> selectCityList(@PathVariable("id") Long id) {

        // 1. 여행 중인 도시 조회
        List<Travel> city = cityService.findTravelingCityWithUserId(id);

        // 2. 나머지 도시 조회
        List<City> city2 = cityService.findOtherCityWithUserId(id);

        List<CityListDto> result = city.stream()
                .map(o -> new CityListDto(o))
                .collect(toList());

        // null 처리 하기
        result.addAll(city2.stream()
                .map(o -> new CityListDto(o))
                .collect(Collectors.toList()));

        return result;

    }

    @Data
    static class CityListDto {
        private Long city_id;
        private String cityname;

        public CityListDto(Travel o) {
            city_id = o.getCity().getId();
            cityname = o.getCity().getCityname();
        }

        public CityListDto(City o) {
            city_id = o.getId();
            cityname = o.getCityname();
        }
    }

    @Data
    @NoArgsConstructor
    static class CityDto {
        private String cityname;
    }

    @Data
    static class UpdateMemberRequest {
        private String cityname;
    }

    @Data
    @AllArgsConstructor
    static class UpdateCityResponse {

        private Long id;

        @NotBlank(message = "cityname is a required value.")
        private String cityname;
    }

    @Data
    static class CreateCityRequest {
        @NotBlank(message = "cityname is a required value.")
        private String cityname;
    }

    @Data
    static class CreateCityResponse {
        private Long id;

        public CreateCityResponse(Long id) {
            this.id = id;
        }
    }
}

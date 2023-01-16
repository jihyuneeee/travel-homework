package com.homework.travel.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homework.travel.domain.entity.City;
import com.homework.travel.domain.entity.CityDto;
import com.homework.travel.domain.entity.Travel;
import com.homework.travel.repository.CityRepository;
import com.homework.travel.repository.TravelRepository;
import com.querydsl.core.Tuple;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final TravelRepository travelRepository;

    /**
     * 도시 등록
     */
    @Transactional
    public Long register(City city) {

        validateDuplicateCity(city.getCityname()); // 중복 도시 검증
        cityRepository.save(city);
        return city.getId();
    }

    /**
     * 도시 수정
     */
    @Transactional
    public void updateCity(Long id, String cityname) {

        validateDuplicateCity(cityname); // 중복 도시 검증

        City city = cityRepository.findOne(id);
        city.setCityname(cityname);
    }

    @Transactional
    public City findOne(Long id) {

        City city = cityRepository.findOne(id);

        LocalDateTime now = LocalDateTime.now();
        city.setInqryDate(now);

        return city;
    }

    @Transactional
    public City findCity(Long id) {

        City city = cityRepository.findOne(id);
        return city;
    }

    @Transactional
    public void updateInqryDate(City city) {

        // 조회 시간 업데이트 (inqryDate)
        LocalDateTime now = LocalDateTime.now();
        city.setInqryDate(now);

    }

    @Transactional
    public void deleteCity(Long id) {

        List<City> travel = cityRepository.findById(id);

        if (travel.isEmpty()) {
            City city = cityRepository.findOne(id);

            // 삭제
            Long id2 = cityRepository.delete(city);

        }

    }

    /**
     * 여행 중인 도시
     * 
     * @param id
     * @return
     */
    public List<Travel> findTravelingCityWithUserId(Long id) {

        LocalDateTime now = LocalDateTime.now();

        List<Travel> travel = travelRepository.findByUserId(id, now);

        // 지우기
        for (int i = 0; i < travel.size(); i++) {
            System.out.println("!!!");
            System.out.println(travel.get(i).getCity().getId());
            System.out.println(travel.get(i).getCity().getCityname());

        }
        return travel;

    }

    /**
     * 여행 중 도시 제외하고, 조회
     * 
     * @param id
     * @return
     */
    public List<City> findOtherCityWithUserId(Long id) {

        LocalDateTime now = LocalDateTime.now();

        List<City> city = cityRepository.findByUserId(id, now);

        // 지우기

        // 지우기

        return city;
    }

    private void validateDuplicateCity(String cityname) {
        List<City> findCities = cityRepository.findByCityname(cityname);
        if (!findCities.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 도시입니다.");
        }
    }

}
package com.homework.travel.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.homework.travel.domain.entity.City;
import com.homework.travel.domain.entity.Travel;
import com.homework.travel.domain.entity.Users;
import com.homework.travel.repository.CityRepository;
import com.homework.travel.repository.TravelRepository;
import com.homework.travel.repository.UserRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    /**
     * 여행 등록
     */
    @Transactional
    public Long planTravel(Long cityId, Long userId, String travelname, LocalDateTime startDate,
            LocalDateTime endDate) {

        // 1. Entity 조회
        City city = cityRepository.findOne(cityId);
        Users user = userRepository.findOne(userId);

        // 2. Travel 등록
        Travel travel = Travel.planTravel(city, user, travelname, startDate, endDate);

        travelRepository.save(travel);

        return travel.getId();
    }

    @Transactional
    public void update(Long id, Long cityId, LocalDateTime startDate, LocalDateTime endDate) {

        Travel travel = travelRepository.findOne(id);

        City city = cityRepository.findOne(cityId);

        travel.setCity(city);
        travel.setStartDate(startDate);
        travel.setEndDate(endDate);

    }

    @Transactional
    public void deleteTravel(Long id) {

        Travel travel = travelRepository.findOne(id);

        if (travel != null) {
            Long id2 = travelRepository.delete(travel);
            System.out.println("id2 :" + id2);
        }

    }

    public Travel findOne(Long id) {
        Travel travel = travelRepository.findOne(id);
        return travel;
    }

}

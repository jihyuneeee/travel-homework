package com.homework.travel.domain.entity;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityDto {
    private Long id;
    private String cityname;

    @QueryProjection
    public CityDto(Long id, String cityname) {
        this.id = id;
        this.cityname = cityname;
    }
}

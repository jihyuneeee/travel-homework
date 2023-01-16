package com.homework.travel.domain.entity;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.homework.travel.domain.entity.QCityDto is a Querydsl Projection type for CityDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCityDto extends ConstructorExpression<CityDto> {

    private static final long serialVersionUID = -88712720L;

    public QCityDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> cityname) {
        super(CityDto.class, new Class<?>[]{long.class, String.class}, id, cityname);
    }

}


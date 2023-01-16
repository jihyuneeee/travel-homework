package com.homework.travel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.homework.travel.domain.entity.Travel;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TravelRepository {

    private final EntityManager em;

    public void save(Travel travel) {
        em.persist(travel);
    }

    public Long delete(Travel travel) {
        em.remove(travel);
        return travel.getId();
    }

    public Travel findOne(Long id) {
        return em.find(Travel.class, id);
    }

    public List<Travel> findByUserId(Long userId, LocalDateTime now) {

        return em.createQuery(
                "select t " +
                        " from Travel t" +
                        " join fetch t.city c" +
                        " where t.user.id = :id " +
                        " and date_format(:nowdate, '%Y-%m-%d') BETWEEN date_format(t.startDate, '%Y-%m-%d') AND date_format(t.endDate, '%Y-%m-%d') "
                        +
                        " order by t.startDate asc",
                Travel.class)
                .setParameter("id", userId)
                .setParameter("nowdate", now)
                .getResultList();
    }

}
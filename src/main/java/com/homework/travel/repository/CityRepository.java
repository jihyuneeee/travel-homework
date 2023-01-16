package com.homework.travel.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.homework.travel.domain.entity.City;
import com.homework.travel.domain.entity.CityDto;
import com.homework.travel.domain.entity.QCity;
import com.homework.travel.domain.entity.QTravel;
import com.mysql.cj.xdevapi.Expression;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CityRepository {

        private final EntityManager em;

        public void save(City city) {
                em.persist(city);
        }

        public List<City> findByCityname(String cityname) {
                return em.createQuery("select c from City c where c.cityname = :cityname", City.class)
                                .setParameter("cityname", cityname)
                                .getResultList();
        };

        public List<City> findById(Long id) {

                return em
                                .createQuery(
                                                "select c from City c join fetch c.travels t where c.id = :id ",
                                                City.class)
                                .setParameter("id", id)
                                .getResultList();
        };

        public List<City> findByUserId(Long userId, LocalDateTime now) {

                QCity city = QCity.city;
                QTravel travel = QTravel.travel;

                JPAQueryFactory query = new JPAQueryFactory(em);

                StringTemplate nowDate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", now,
                                ConstantImpl.create("%Y-%m-%d HH:mm:ss"));

                StringTemplate startDate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", travel.startDate,
                                ConstantImpl.create("%Y-%m-%d HH:mm:ss"));

                StringTemplate endDate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", travel.endDate,
                                ConstantImpl.create("%Y-%m-%d HH:mm:ss"));

                StringTemplate regDate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", city.regDate,
                                ConstantImpl.create("%Y-%m-%d HH:mm:ss"));

                StringTemplate inqryDate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", city.inqryDate,
                                ConstantImpl.create("%Y-%m-%d HH:mm:ss"));

                StringTemplate afterWeek = Expressions.stringTemplate("DATE_ADD({0} , INTERVAL {ls} day)", now,
                                Expressions.constant(7));

                Path<Integer> rank = Expressions.numberPath(Integer.class, "rank");

                StringPath alias = Expressions.stringPath("rank");

                DateTimeExpression<LocalDateTime> cases = new CaseBuilder()
                                .when(startDate.gt(nowDate)).then(travel.startDate)
                                .otherwise(Expressions.nullExpression());

                System.out.println("alias :" + alias.getAnnotatedElement());
                System.out.println("alias :" + alias.getClass());

                List<Tuple> result = query.select(city,
                                new CaseBuilder()
                                                .when(startDate.gt(nowDate)).then(1)
                                                .when(regDate.eq(nowDate)).then(2)
                                                .when(regDtsAfter(now)).then(3)
                                                .otherwise(4).as("rank"))
                                .from(city).join(city.travels, travel).fetchJoin()
                                .where(travel.user.id.eq(userId), nowDate.between(startDate, endDate).not())
                                .orderBy(alias.asc())
                                .offset(0)
                                .limit(10)
                                .fetch();

                List<City> list = new ArrayList<>();
                for (Tuple tuple : result) {

                        City tt = new City();
                        Long id = tuple.get(city).getId();
                        String cityname = tuple.get(city).getCityname();

                        tt.setId(id);
                        tt.setCityname(cityname);

                        list.add(tt);
                }

                return list;
        }

        private BooleanExpression regDtsAfter(LocalDateTime now) {

                now = now.minusWeeks(1);
                System.out.println("now :" + now);
                return QCity.city.inqryDate.after(now);
        }

        public List<City> findByUserId2(Long userId, LocalDateTime now) {

                return em.createQuery(
                                "select  " +
                                                " c " +
                                                " from City c" +
                                                " join fetch c.travels t" +
                                                " where t.user.id = :id " +
                                                " and not date_format(:nowdate, '%Y-%m-%d') BETWEEN date_format(t.startDate, '%Y-%m-%d') AND date_format(t.endDate, '%Y-%m-%d') "

                                , City.class)
                                .setParameter("id", userId)
                                .setParameter("nowdate", now)
                                // .setParameter("afterweek", now.plusDays(7))
                                .setFirstResult(0)
                                .setMaxResults(10)
                                .getResultList();
        }

        public City findOne(Long id) {
                return em.find(City.class, id);
        }

        public Long delete(City city) {
                em.remove(city);
                return city.getId();
        }

}
package com.homework.travel.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.homework.travel.domain.entity.Users;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(Users user) {
        em.persist(user);
    }

    public List<Users> findByUsername(String username) {
        return em.createQuery("select u from Users u where u.username = :username", Users.class)
                .setParameter("username", username)
                .getResultList();
    };

    public Users findOne(Long id) {
        return em.find(Users.class, id);
    }

}

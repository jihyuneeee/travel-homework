package com.homework.travel.service;

import org.omg.CORBA.UserException;
import org.springframework.stereotype.Service;

import com.homework.travel.domain.entity.Users;
import com.homework.travel.repository.UserRepository;

import java.util.List;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 등록
     */
    @Transactional
    public Long saveUser(Users user) {

        validateDuplicateUser(user); // 중복 사용지 검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(Users user) {
        List<Users> findUsers = userRepository.findByUsername(user.getUsername());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        }
    }

}

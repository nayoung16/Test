package com.example.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    User save(User user);

    //이메일로 회원가입된 회원인지 중복체크
    boolean existsByEmail(String email);

    // ?1 : 첫 번째 위치 기반 파라미터에 해당하는 값 지정
    @Query("select u from User u where u.role = 'tutor' and u.email = ?1")
    User findTutorByEmail(String email);

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);

    List<User> findAll();

    @Query("select u from User u where u.role = 'tutor'")
    List<User> findTutors(String role);

/*
    //Param 어노테이션을 사용하지 않는 경우
    // 튜터 필터링
    @Query("select u from User u where " +
            "(:gender IS NULL OR u.gender = :gender) AND " +
            "(:locationsido IS NULL OR u.locationsido = :locationsido) AND " +
            "(:locationgu IS NULL OR u.locationgu = :locationgu) AND " +
            "(:tutoringmethod IS NULL OR u.tutoringmethod = :tutoringmethod)")
    List<User> findUsersByFilter(
            String gender,
            String locationsido,
            String locationgu,
            String tutoringmethod);

 */

    //튜터 필터링 - 시간까지
    @Query("select u from User u join Time t on u.email = t.user_email where" +
            "(:gender IS NULL OR u.gender = :gender) AND " +
            "(:locationsido IS NULL OR u.locationsido = :locationsido) AND " +
            "(:locationgu IS NULL OR u.locationgu = :locationgu) AND " +
            "(:tutoringmethod IS NULL OR u.tutoringmethod = :tutoringmethod) AND " +
            "(:times IS NULL OR t.time in :times) AND " +
            "u.role = 'tutor'")
    List<Object[]> findUsersByFilterWithTime(String gender,
                                         String locationsido,
                                         String locationgu,
                                         String tutoringmethod,
                                         List<String> times);

    @Query("select u from User u join Time t on u.email = t.user_email where u.role = 'tutor' and t.time = ?1")
    List<User> findTutorsByTime(String time);

}

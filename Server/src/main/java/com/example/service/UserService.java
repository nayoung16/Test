package com.example.service;

import com.example.domain.message.MessageRepository;
import com.example.domain.time.Time;
import com.example.domain.time.TimeRepository;
import com.example.domain.user.User;
import com.example.domain.user.UserRepository;
import com.example.dto.UserAndTimeReturnDto;
import com.example.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TimeRepository timeRepository;
    private final MessageRepository messageRepository;

    //user데이터 DB에 저장
    public void join(UserDto userDto){
        userRepository.save(userDto.toEntity());
    }
    /*
        //중복데이터 저장 방지
        private void validateDuplicateMember(User user){
            userRepository.findByEmail(user.getEmail())
                    .ifPresent(e -> {
                        throw new IllegalStateException("이미 존재하는 회원입니다.");
                    });
        }
    */


    //이메일로 이미 가입된 회원인지 판별
    public boolean checkUserDuplicate(String email){
        return userRepository.existsByEmail(email);
    }

    //email주소로 db에서 튜터데이터 검색
    public UserAndTimeReturnDto findTutorByEmail(String email){
        User user =  userRepository.findTutorByEmail(email);
        List<Time> times = timeRepository.findTimesByUserEmail(email);
        // 결과 조합
        List<String> return_times = new ArrayList<>();
        for (Time time : times) {
            return_times.add(time.getTime());
        }
        return new UserAndTimeReturnDto(user.getEmail(), user.getName(),
                user.getRole(), user.getGender(), user.getLocationsido(), user.getLocationgu(), user.getTutoringmethod(),
                user.getIntroduction(), user.getPhotourl(), return_times);
    }

    //email주소로 db에서 유저데이터 검색
    public UserAndTimeReturnDto findUserByEmail(String email){
        User user =  userRepository.findByEmail(email);
        List<Time> times = timeRepository.findTimesByUserEmail(email);
        // 결과 조합
        List<String> return_times = new ArrayList<>();
        for (Time time : times) {
            return_times.add(time.getTime());
        }
        return new UserAndTimeReturnDto(user.getEmail(), user.getName(),
                user.getRole(), user.getGender(), user.getLocationsido(), user.getLocationgu(), user.getTutoringmethod(),
                user.getIntroduction(), user.getPhotourl(), return_times);
    }

    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public List<User> findTutors(String role){
        return userRepository.findTutors(role);
    }

    public List<UserAndTimeReturnDto> getUsersByFilterWithTime(String gender, String locationsido, String locationgu, String tutoringmethod,
                                               List<String> times) {
        // 사용자 필터링된 목록 가져오기
        List<Object[]> usersAndTimes = userRepository.findUsersByFilterWithTime(gender, locationsido, locationgu, tutoringmethod, times);

        // 사용자 이메일 목록 추출
        List<String> userEmails = usersAndTimes.stream()
                .map(userAndTime -> ((User) userAndTime[0]).getEmail())
                .toList();

        // 각 사용자에 대한 시간 정보 가져오기
        Map<String, List<Time>> userEmailToTimesMap = new HashMap<>();
        for (String userEmail : userEmails) {
            List<Time> times1 = timeRepository.findTimesByUserEmail(userEmail);
            userEmailToTimesMap.put(userEmail, times1);
        }

        // 결과 조합
        List<UserAndTimeReturnDto> result = new ArrayList<>();
        for (Object[] userAndTime : usersAndTimes) {
            User user = (User) userAndTime[0];
            List<Time> times2 = userEmailToTimesMap.get(user.getEmail());
            List<String> return_times = new ArrayList<>();
            for (Time time : times2) {
                return_times.add(time.getTime());
            }
            UserAndTimeReturnDto userAndTimeReturnDto = new UserAndTimeReturnDto(user.getEmail(), user.getName(),
                    user.getRole(), user.getGender(), user.getLocationsido(), user.getLocationgu(), user.getTutoringmethod(),
                    user.getIntroduction(), user.getPhotourl(), return_times);
            result.add(userAndTimeReturnDto);
        }
        return result;
    }

    public List<User> findTutorsByTime(String time){
        return userRepository.findTutorsByTime(time);
    }

    public List<Object[]> getMessageUserLists(String email) {
        User user = userRepository.findByEmail(email);
        List<Object[]> users = new ArrayList<>();
        switch (user.getRole()) {
            case "tutor" -> {
                users = messageRepository.findSenders(user);
            }
            case "student" -> {
                users = messageRepository.findReceivers(user);
            }
        }
        return users;
    }

    public void updateUserIntro(String email, String introduction) {
        User user = userRepository.findByEmail(email);
        user.setIntroduction(introduction);

        // 유저 정보를 저장합니다.
        userRepository.save(user);
    }

    //마이페이지에서 정보를 수정
    public void editMypage(String email, UserDto userDto){
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.updateMypage(userDto.getGender(), userDto.getLocationsido(),
                    userDto.getLocationgu(), userDto.getTutoringmethod(),
                    userDto.getIntroduction());

            userRepository.save(user);
        } else {
            System.out.println("이메일에 해당하는 사용자를 찾을 수 없습니다: " + email);
        }
    }

    /*
    //마이페이지에서 time 제외하고 기본 정보만
    public UserDto getUserByEmail(String email){
        User user =  userRepository.findByEmail(email);

        UserDto userDto = new UserDto();
        userDto.setGender(user.getGender());
        userDto.setLocationsido(user.getLocationsido());
        userDto.setLocationgu(user.getLocationgu());
        userDto.setTutoringmethod(user.getTutoringmethod());
        userDto.setIntroduction(user.getIntroduction());
        return userDto;
    }
    */
}
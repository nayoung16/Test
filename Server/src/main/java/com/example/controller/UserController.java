package com.example.controller;

import com.example.domain.request.TimeRequest;
import com.example.dto.TimeDto;
import com.example.dto.UserDto;
import com.example.response.Response;
import com.example.service.TimeService;
import com.example.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final TimeService timeService;

    public UserController(UserService userService, TimeService timeService) {
        this.userService = userService;
        this.timeService = timeService;
    }

    //회원 등록
    @PostMapping("register")
    public boolean register(@RequestBody UserDto user){
        try{
            if (userService.checkUserDuplicate(user.getEmail())){
                return true;
            }else{
                System.out.println(user.toString());
                userService.join(user);
                return false;
            }
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미 등록된 회원입니다.");
        }
    }


    //회원가입시 이미 가입한 회원인지 중복체크
    @GetMapping("register/{email}/exists")
    public ResponseEntity<Boolean> checkMemberDuplicate(@PathVariable String email){
        return ResponseEntity.ok(userService.checkUserDuplicate(email));
    }


    //time 저장
    @PostMapping("register/time")
    public Response register_time(@RequestBody TimeRequest request) {
        String email = request.getEmail();
        List<String> times = request.getTimes();
        try {
        for (String time : times) {
            TimeDto timeDto = new TimeDto(time, email);
            timeService.save(timeDto);
        }
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미 등록된 시간입니다.");
        }
        return new Response("성공", "요일 저장 성공", null);
    }

//    @GetMapping("all")
//    public List<User> findAll(){
//        return userService.findUsers();
//    }

    //모든 회원 목록 조회 (요일 제외)
    @GetMapping("all")
    public Response findAll(){
        return new Response("성공", "모든 회원목록 조회 성공",userService.findUsers());
    }

    //모든 튜터 목록 조회 (요일 제외)
    @GetMapping("tutors")
    public Response findTutors(String role){
        return new Response("성공", "모든 튜터목록 조회 성공",userService.findTutors(role));
    }

    //이메일로 튜터 정보 조회
    //@PathVariable : 경로 변수
    @GetMapping("tutors/{email}")
    public Response findTutorByEmail(@PathVariable String email){

        return new Response("성공", "튜터정보 조회 성공", userService.findTutorByEmail(email));
    }
  /*
    //tutor 필터링
    @GetMapping("/user")
    public List<User> getUsersByFilter(
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "locationsido", required = false) String locationsido,
            @RequestParam(value = "locationgu", required = false) String locationgu,
            @RequestParam(value = "tutoringmethod", required = false) String tutoringmethod) {

        return userService.getUsersByFilter(gender, locationsido, locationgu, tutoringmethod);
    }

   */

    //필터링 결과에 따른 튜터 정보 조회
    @GetMapping("/filter")
    public Response getUsersByFilterWithTime(
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "locationsido", required = false) String locationsido,
            @RequestParam(value = "locationgu", required = false) String locationgu,
            @RequestParam(value = "tutoringmethod", required = false) String tutoringmethod,
            @RequestParam(value = "times", required = false) List<String> times) {

        return new Response("성공", "튜터 필터링 성공",
                userService.getUsersByFilterWithTime(gender, locationsido, locationgu, tutoringmethod, times));
    }

    //요일에 따른 튜터 조회
    @GetMapping("tutors/time/{time}")
    public Response findTutorsByTime(@PathVariable String time){
        return new Response("성공", "요일별 튜터 조회 성공", userService.findTutorsByTime(time));
    }

    //유저 정보 검색 (요일 포함)
    @GetMapping("/{email}")
    public Response findUserByEmail(@PathVariable String email){

        return new Response("성공", "요일별 회원 조회 성공", userService.findUserByEmail(email));
    }

    @PatchMapping("/updateIntro")
    public void updateUserIntro(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "intro") String introduction) {
        userService.updateUserIntro(email, introduction);
    }

    //마이페이지에서 정보 조회
    @GetMapping("/mypage/{email}")
    public Response getMypage3(@PathVariable String email){
        return new Response("성공", "마이페이지 조회 성공", userService.findUserByEmail(email));
    }

    //마이페이지에서 정보 업데이트 (요일 제외)
    @PatchMapping("/mypage/{email}/edit")
    public Response editMypage(
            @PathVariable String email,
            @RequestBody UserDto userDto) {
        userService.editMypage(email, userDto);
        return new Response("성공", "마이페이지 업데이트 성공",null);
    }

    //마이페이지에서 요일 추가
    @PostMapping("/mypage/edit-time")
    public Response addMypageTime(@RequestBody TimeRequest request) {
        String email = request.getEmail();
        List<String> times = request.getTimes();
        try {
            for (String time : times) {
                TimeDto timeDto = new TimeDto(time, email);
                timeService.save(timeDto);
            }
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미 저장된 요일입니다.");
        }
        return new Response("성공", "요일 저장 성공", null);
    }

    //마이페이지에서 요일 삭제
    @DeleteMapping("/mypage/delete-time")
    public Response deleteMypageTime(@RequestBody TimeRequest request) {
        String email = request.getEmail();
        List<String> times = request.getTimes();
        try {
            for (String time : times) {
                timeService.deleteByEmailAndTime(email, time);
            }
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미 저장된 요일입니다.");
        }
        return new Response("성공", "요일 삭제 성공", null);
    }
}

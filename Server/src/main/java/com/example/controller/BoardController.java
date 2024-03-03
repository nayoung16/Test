package com.example.controller;

import com.example.domain.user.User;
import com.example.domain.user.UserRepository;
import com.example.dto.BoardDto;
import com.example.response.Response;
import com.example.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final UserRepository userRepository;


    // 전체 질문 게시글 목록 조회 (최신순)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts")
    public Response getAllBoards() {
        return new Response("성공", "전체 게시물 리턴", boardService.getAllBoards());
    }


    // 개별 질문 게시글 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/{id}")
    public Response getBoard(@PathVariable("id") Integer id) {
        return new Response("성공", "개별 게시물 리턴", boardService.getBoard(id));
    }


    // 질문 게시글 작성
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/write")
    public Response writeBoard(@RequestBody BoardDto boardDto) {
        String user_email = boardDto.getWriter();
        User user = userRepository.findByEmail(user_email);
        System.out.println(boardDto.toString());
        return new Response("성공", "게시글 작성 성공", boardService.writeBoard(boardDto, user));
    }


    // 게시글 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/posts/delete/{id}")
    public Response delete(@PathVariable("id") Integer id) {
        boardService.deleteBoard(id);
        return new Response("성공", "게시글 삭제 성공", null);
    }

}

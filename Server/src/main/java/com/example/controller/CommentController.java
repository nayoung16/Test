package com.example.controller;

import com.example.domain.user.User;
import com.example.domain.user.UserRepository;
import com.example.dto.CommentDto;
import com.example.response.Response;
import com.example.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;

    // 댓글 작성
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/comments/{boardId}")
    public Response writeComment(@PathVariable("boardId") Integer boardId, @RequestBody CommentDto commentDto) {
        String user_email = commentDto.getWriter();
        User user = userRepository.findByEmail(user_email);
        System.out.println(commentDto.toString());
        return new Response("성공", "댓글 작성 완료", commentService.writeComment(boardId, commentDto, user));
    }


    // 게시글에 달린 전체 댓글 조회하기
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comments/{boardId}")
    public Response getComments(@PathVariable("boardId") Integer boardId) {
        return new Response("성공", "전체 댓글 조회 완료", commentService.getAllComments(boardId));
    }


    // 댓글 개별 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/comments/{boardId}/{commentId}")
    public Response deleteComment(@PathVariable("boardId") Integer boardId, @PathVariable("commentId") Integer commentId) {
        return new Response("성공", "댓글 삭제 완료", commentService.deleteComment(commentId));
    }

}

package com.example.service;

import com.example.domain.comment.Comment;
import com.example.domain.comment.CommentRepository;
import com.example.domain.post.Board;
import com.example.domain.post.BoardRepository;
import com.example.domain.user.User;
import com.example.dto.BoardDto;
import com.example.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 작성하기
    @Transactional
    public CommentDto writeComment(int boardId, CommentDto commentDto, User user) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());

        // 게시판 번호로 게시글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없습니다.");
        });

        comment.setUser(user);
        comment.setBoard(board);
        commentRepository.save(comment);

        return CommentDto.toDto(comment);
    }



    // 글에 해당하는 전체 댓글 불러오기
    @Transactional(readOnly = true)
    public List<CommentDto> getAllComments(int boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);

        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments){
            CommentDto commentDto = CommentDto.toDto(comment);
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }


    // 댓글 삭제하기
    @Transactional
    public String deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> {
            return new IllegalArgumentException("삭제하려는 댓글 Id를 찾을 수 없습니다.");
        });
        commentRepository.deleteById(commentId);
        return "댓글 삭제 완료";
    }
}

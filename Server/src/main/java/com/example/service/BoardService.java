package com.example.service;

import com.example.domain.post.Board;
import com.example.domain.post.BoardRepository;
import com.example.domain.user.User;
import com.example.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    //최신순으로 정렬하여 전체 질문 게시글 조회
    @Transactional(readOnly = true)
    public List<BoardDto> getAllBoards() {
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();

        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boards) {
            BoardDto boardDto = BoardDto.toDto(board);
            boardDtos.add(boardDto);
        }
        return boardDtos;
    }

    // 개별 질문 게시글 조회
    @Transactional(readOnly = true)
    public BoardDto getBoard(int id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("없는 게시글 ID입니다.");
        });
        BoardDto boardDto = BoardDto.toDto(board);
        return boardDto;
    }

    // 질문 게시글 작성
    @Transactional
    public BoardDto writeBoard(BoardDto boardDto, User user) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setUser(user);
        boardRepository.save(board);
        return BoardDto.toDto(board);
    }

    // 질문 게시글 삭제
    @Transactional
    public void deleteBoard(int id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("없는 게시글 ID입니다.");
        });
        boardRepository.deleteById(id);
    }
}

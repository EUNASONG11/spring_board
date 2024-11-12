package com.green.board;

import com.green.board.model.BoardInsReq;
import com.green.board.model.BoardSelOneRes;
import com.green.board.model.BoardSelRes;
import com.green.board.model.BoardUpdReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    @Service - 빈 등록, 서비스가 로직 처리 담당. 로직 처리가 있다면 여기서 처리한다.
               없으면 연결 작업만 한다. 연결 작업이 Controller와 Persistence(DB) 연결

    빈 등록 : 스프링 컨테이너에게 객체 생성을 대리로 맡기는 것,
             기본적으로 싱글톤(Singleton - 특정 클래스의 인스턴스가 오직 하나만 생성되도록 보장하는 패턴)으로 객체화
 */
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardMapper mapper;

    public int insBoard(BoardInsReq p) {
        return mapper.insBoard(p);
    }

    public List<BoardSelRes> selBoardList() {
        return mapper.selBoardList();
    }

    public BoardSelOneRes selBoardOne(int p) {
        return mapper.selBoardOne(p);
    }

    public int updBoard(BoardUpdReq p) {
        return mapper.updBoard(p);
    }
}

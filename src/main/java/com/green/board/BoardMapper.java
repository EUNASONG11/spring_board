package com.green.board;

import com.green.board.model.BoardInsReq;
import com.green.board.model.BoardSelOneRes;
import com.green.board.model.BoardSelRes;
import com.green.board.model.BoardUpdReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/*
    src > main > resource > mappers 폴더 아래에 이름이 같은 xml 파일을 만든다.
    (같은 이름을 사용할 필요는 없으나 관리상 용이하게 하기 위해 같은 이름을 쓴다.)

    xml + interface 파일을 이용해서 implements한 class 파일을 만들고 빈 등록까지 해준다.
    스프링 컨테이너가 빈 등록한 class 파일을 객체화할 것이다.
    여기서 만든 주소값을 BoardService 객체화할 때 DI 해준다.
 */

/*
    insert, update, delete의 리턴 타입은 int
 */
@Mapper
public interface BoardMapper {
    int insBoard(BoardInsReq p);
    List<BoardSelRes> selBoardList();
    BoardSelOneRes selBoardOne(int p);
    int updBoard(BoardUpdReq p);
}

// insBoard, selBoardList, selBoardOne, updBoard 는 BoardMapper.xml 에 만든 id와 동일한 이름
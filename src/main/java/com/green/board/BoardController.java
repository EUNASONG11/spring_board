package com.green.board;
/*
    Controller의 역할 : 요청(request)을 받고 응답(response)을 처리하는 객체
                       로직 처리는 하지 않는다.
    Annotation(애노테이션)
    @Controller - 응답을 html (데이터로 만든 화면을 응답)
    @RestController - 응답을 json (데이터만 응답)
    @RequestMapping - URL과 클래스 아래에 있는 Method 맵핑(연결)
                      class에 RequestMapping 전체 메소드 주소가 맵핑
    @PostMapping - URL + Post 방식으로 요청이 왔을 시 담당자


    요청과 응답은 header, body로 이루어져 있다.
    header에는 목적지(URL), 방식, 인코딩 등등이 담겨져 있다.
    body에는 값, 데이터가 담겨져 있다.


    브라우저를 통해 요청을 보낼 때 URL과 method를 함께 요청을 보낸다.
    브라우저의 주소창에 주소값을 적고 엔터는 URL + GET 방식 + 데이터 보내는 방식(Key/Value)으로 요청을 보낸다.
    데이터를 보낼 때 보여지나 안보여지나 의 차이가 있다.
    1. 쿼리스트링 방식(파라미터라고 부르기도 함), URL에 데이터를 포함하는 방식
    2. body에 담아서 보내는 방식(Fromdata, JSON)

    쿼리스트링 모양 : ULR + 쿼리스트링(?로 시작, key=value, 여러 개라면 & 구분)
                   www.naver.com?name=홍길동&age=12&height=172.1

    대용량의 데이터를 보내야 할 때도 body에 데이터를 담아서 보낸다. URL은 길이 제한이 있기 때문에 URL에 데이터를 포함하는 쿼리스트링은 대용량을 보낼 수 없다.

    JSON(JavaScript Object Notation) : 자바스트립트에서 객체를 만들 때 사용하는 문법을 이용하여
                                       데이터를 표현하는 포맷(형식), Key와 Value로 이루어져 있다.
    ex) name은 홍길동, age는 22살, height는 178.2 데이터를 JSON으로 표현을 하면
        {
            "name": "홍길동",
            "age": 22,
            "height": 178.2
        }
        이렇게 표현하는 문자열이다.
        {}는 객체를 의미하고 []는 배열을 의미한다.
        ""은 문자열, 숫자형은 ""없이 표현한다.
        key("name" 등)는 무조건 ""로 감싸줘야 한다.

    Restful 이전에는 GET, POST 방식밖에 없었다.
    GET 방식은 주로 쿼리스트링 방식을 사용하고 (데이터를 읽어올 때, 간혹 삭제할 때도 사용함)
    POST 방식은 body에 데이터를 담아서 보내는 방식을 사용했었다. (데이터를 저장/수정/삭제할 때)
    데이터가 있었을 때는 GET 방식이 처리 속도가 빠름, 데이터 처리가 아닌 단순 화면을 띄울 때도 GET 방식을 사용함

    ex) 로그인을 하는 상황에서 로그인을 하는 화면이 띄워져야 한다.
        작업(1) : 로그인 하는 화면은 GET 방식으로 URL은 /login을 요청하면 로그인하는 화면이 나타난다.
                 이하 (GET) /login 이렇게 표현하겠다.
        작업(2) : 그 다음, 아이디/비번을 작성하고 로그인 버튼을 누르면 (POST) /login, 아이디/비번은 body에 담아서 요청을 보냈다.

        URL은 같으나 method(GET/POST)로 작업을 구분 했다.(마치 if문처럼)

    위 작업은 2가지밖에 없었기 때문에 같은 주소값으로 method를 구분할 수 있었다.
    그런데 CRUD(작업이 4가지)를 해야되는 상황에서는 작업 구분을 주소값으로 해야 했었다.

    (GET) /board - 게시판 리스트 보기 화면
    (GET) /board_detail - 게시판 글 하나 보기 화면
    (GET) /board_create - 게시판 글 등록하는 화면
    (POST) /board_create - 게시판 글 등록하는 작업 처리
    (GET) /board_modify - 게시판 글 수정하는 화면
    (POST) /board_modify - 게시판 글 수정하는 작업 처리
    (GET/POST) /board_delete - 게시판 글 삭제하는 작업 처리

    첫 페이지(index 화면)를 띄울 때 소프트웨어(FE 작업 코드)가 모두 다운로드 된다. // FE는 프론트 엔드
    화면 이동은 모두 FE 코드가 작동하는 것, 화면 만들기는 Client 리소스를 사용하여 그린다.
    (Rendering) 화면마다 데이터가 필요하면 BE에게 요청을 한다.
    누가? FE 작업코드가 요청
    그래서 BE는 이제 화면은 신경쓰지 않아도 된다.
    FE 코드가 요청한 작업에 응답만 잘해주면 된다.

    Client 리소스 : Client, 즉 요청을 보낸  컴퓨터의 자원을 사용한다(cpu, ram 등등)

    Restful 방식은 화면은 없고 작업만 신경 쓰면 된다.
    (요청의 method는 크게 4가지로 나누어진다)
    - POST 방식 : Create - Insert 작업
    - GET 방식 :
    - PUT / PATCH 방식
    - DELETE 방식

    POST, PUT/PATCH 방식은 주로 데이터를 body에 담아서 보내고
    GET, DELETE 방식은 Path Variable or Query String을 사용행서 데이터를 보낸다.

    FE가 BE한테 ( URL + method + 데이터 ) 요청(Request)을 보내고 BE는 JSON으로 응답(Response)

    (POST) /board - 글 등록

    (GET) /board?page=1 - 리스트 데이터 (row가 여러 개)
    (GET) /board/ - 마지막에 '/'만 붙어있어도 위의 URL( (GET) /board )과 다른 요청이 된다.(Tip)
    (GET) /board?aaa=2 - /board?page=1과 같은 요청이다. URL( (GET) /board )이 같으면 같은 요청
    (GET) /board/1 - 튜플 1개 데이터(row가 1줄), 1은 PK, Path Variable

    (PUT / Patch) /board - 글 수정

    (DELETE) /board - 글 삭제(Path Variable or Query String으로 PK 값 전달)


    (POST) /board
    (GET) /board
    같은 URL 다른 method
 */

import com.green.board.model.BoardInsReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor // final 붙은 멤버필드 DI 받을 수 있게 생성자를 만든다. 애노테이션을 생략하면 오버로딩된 생성자를 직접 작성해주면 된다.
@RestController //빈 등록 + 컨트롤러 임명, 빈 등록은 스프링 컨테이너가 직접 객체화를 한다.
@RequestMapping("/board")
public class BoardController {
    private final BoardService service;

    // @RequiredArgsConstructor 애노테이션을 붙이면 아래 생성자가 자동으로 만들어진다.
//    public BoardController(BoardService boardService) {
//        this.boardService = boardService;
//    }

    // insert(Create)
    @PostMapping // (Post) /board 요청이 오면 이 메소드가 응답 담당자
    // @PostMapping("/board") : @RequestMapping("/board") 이 코드가 없었다면 URL을 작성해줘야 한다.
    // @RequestBody는 요청이 올 때 데이터가 JSON 형태로 오니까 JSON에 맞춰서 데이터를 받자는 의미.
    public int insBoard (@RequestBody BoardInsReq p) {
        System.out.println(p);
        return service.insBoard(p);
    }
}

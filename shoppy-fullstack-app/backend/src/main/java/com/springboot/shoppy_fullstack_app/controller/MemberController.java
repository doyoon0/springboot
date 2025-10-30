package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.Member;
import com.springboot.shoppy_fullstack_app.service.MemberService;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// RestController Annotation에 따라 Controller가 생성되면서 생성자도 함께 생성
// 이때 MemberServiceImpl 은 MemberService의 형태로 생성이 된다. => 컨테이너가 만들어준다.
// MemberServiceImpl은 구현체, MemberService는 인터페이스. Loosely Coupled 구조로 인해
// 인터페이스를 주입받고 구현체는 컨테이너에 의해 자동 주입된다. Spring의 DI 기능이다.
// Controller에 Service 주입하는 이유 : 비즈니스 로직을 서비스에서 처리하고, 컨트롤러는 요청/응답만 처리
@RestController
@RequestMapping("/member")
//@CrossOrigin(origins = {"http://localhost:3000"})
public class MemberController {

    //서비스 객체 호출
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService =  memberService;
    }

    @PostMapping("/signup")
    public boolean Signup(@RequestBody Member member) {
        boolean result = false;
        int rows = memberService.signup(member);
        if(rows == 1) result=true;
        return result;
    }

    //HttpServletResponse 는 lagacy, 요즘은 spring이 response하는 형태가 정해져있음
    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody Member member,
                                HttpServletRequest request) {

        ResponseEntity<?> response = null;
        boolean result =  memberService.login(member);
        if(result) {
            //세션 생성 - true, 빈 값은 생성 파라미터
            //기존 세션 가져오기 - false
            HttpSession session = request.getSession(true);
            session.setAttribute("sid", "hong");
            response = ResponseEntity.ok(Map.of("login", true)); //authAPI에 넘겨준다.
        } else {
            response = ResponseEntity.ok(Map.of("login", false));
        }

        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> Logout(HttpServletRequest request,
                                    HttpServletResponse response) { //쿠키도 추가하려면 얘를 자동으로 받아서 주면됨
        HttpSession session = request.getSession();
        String ssid = session.getId();
        String sid = (String)session.getAttribute("sid"); //Object -> String으로 형변환

//        ResponseEntity<?> response = null;
        if(ssid != null && sid != null) {
            session.invalidate(); //세션 삭제 - 스프링의 세션 테이블에서 삭제됨

            var cookie = new Cookie("JSESSIONID", null); //호불호 갈리는 var 의 사용
            cookie.setPath("/");                // <- 기존과 동일
            cookie.setMaxAge(0);                // <- 즉시 만료 (유효기간)
            cookie.setHttpOnly(true);           // 개발 중에도 HttpOnly 유지 권장
            // cookie.setSecure(true);          // HTTPS에서만. 로컬 http면 주석
            // cookie.setDomain("localhost");   // 기존 쿠키가 domain=localhost였다면 지정
            response.addCookie(cookie); //브라우저에 붙어서 넘어가는 값
        }

        return ResponseEntity.ok(true); //실제 React에게 넘어가는 값
    }

    @PostMapping("/idcheck")
    public String IdCheck(@RequestBody Member member) {
        boolean result = memberService.idCheck(member.getId());
        String msg = "";
        if(result) msg = "이미 사용중인 아이디 입니다.";
        else msg = "사용이 가능한 아이디 입니다.";

        return msg;
    }
}

package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import com.springboot.shoppy_fullstack_app.service.MemberService;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final HttpSessionSecurityContextRepository contextRepository;

    @Autowired
    public MemberController(MemberService memberService,
                            AuthenticationManager authenticationManager,
                            HttpSessionSecurityContextRepository contextRepository) {
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
        this.contextRepository = contextRepository;
    }

    @PostMapping("/signup")
    public boolean Signup(@RequestBody MemberDto member) {
        boolean result = false;
        int rows = memberService.signup(member);
        if(rows == 1) result=true;
        return result;
    }
    
    /**
     * Spring-Security 라이브러리를 이용한 로그인 진행 :
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto member,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            //1. 인증요청
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(member.getId(), member.getPwd());
            
            //2. 인증처리
            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);

            System.out.println("인증 성공: " + authenticationResponse.getPrincipal());

            // 3. 컨텍스트에 보관
            var context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationResponse);
            SecurityContextHolder.setContext(context);

            // SecurityContext 세션에 "명시 저장" (requireExplicitSave(true)일 때 필수)
            contextRepository.saveContext(context, request, response);

            //4. 로그인 성공 시 CSRF 토큰을 재발행을 위해 브라우저 토큰 null 처리
            var xsrf = new Cookie("XSRF-TOKEN", null); //호불호 갈리는 var 의 사용
            xsrf.setPath("/");                // <- 기존과 동일
            xsrf.setMaxAge(0);                // <- 즉시 만료 (유효기간)
            xsrf.setHttpOnly(false);           // 재발행
            // xsrf.setSecure(true);          // HTTPS에서만. 로컬 http면 주석
            // xsrf.setDomain("localhost");   // 기존 쿠키가 domain=localhost였다면 지정
            response.addCookie(xsrf); //브라우저에 붙어서 넘어가는 값

            return ResponseEntity.ok(Map.of("login", true,
                    "userId", member.getId()));

        }catch(Exception e) {
            //로그인 실패
            return ResponseEntity.ok(Map.of("login", false));
        }
    }

//    //HttpServletResponse 는 lagacy, 요즘은 spring이 response하는 형태가 정해져있음
//    @PostMapping("/login")
//    public ResponseEntity<?> Login(@RequestBody Member member,
//                                HttpServletRequest request) {
//
//        ResponseEntity<?> response = null;
//        boolean result =  memberService.login(member);
//        if(result) {
//            //세션 생성 - true, 빈 값은 생성 파라미터
//            //기존 세션 가져오기 - false
//            HttpSession session = request.getSession(true);
//            session.setAttribute("sid", "hong");
//            response = ResponseEntity.ok(Map.of("login", true)); //authAPI에 넘겨준다.
//        } else {
//            response = ResponseEntity.ok(Map.of("login", false));
//        }
//
//        return response;
//    }

    @PostMapping("/logout")
    public ResponseEntity<?> Logout(HttpServletRequest request,
                                    HttpServletResponse response) { //쿠키도 추가하려면 얘를 자동으로 받아서 주면됨
        // 1. 세션이 없으면 생성하지 않고 null 반환 (로그아웃 시 표준 방식)
        HttpSession session = request.getSession(false);

        // 2. 세션이 존재하면 무효화
        if(session != null) {
            session.invalidate(); // 서버 세션 무효화 (JSESSIONID 삭제 명령 포함)
        }

        // 3. JSESSIONID 만료 쿠키 전송 (Path/Domain 꼭 기존과 동일)
        var cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");               // ← 기존과 동일
        cookie.setMaxAge(0);               // ← 즉시 만료
        cookie.setHttpOnly(true);          // 개발 중에도 HttpOnly 유지 권장
        // cookie.setSecure(true);         // HTTPS에서만. 로컬 http면 주석
        // cookie.setDomain("localhost");  // 기존 쿠키가 domain=localhost였다면 지정
        response.addCookie(cookie);

        // 4. CSRF 토큰을 재발행하여 출력
        var xsrf = new Cookie("XSRF-TOKEN", null);
        xsrf.setPath("/");               // ← 기존과 동일
        xsrf.setMaxAge(0);               // ← 즉시 만료
        xsrf.setHttpOnly(false);          // 개발 중에도 HttpOnly 유지 권장
        // xsrf.setSecure(true);         // HTTPS에서만. 로컬 http면 주석
        // xsrf.setDomain("localhost");  // 기존 쿠키가 domain=localhost였다면 지정
        response.addCookie(xsrf);


        // 3. 응답: 세션이 있었든 없었든, 클라이언트에게 로그아웃 요청이 성공했음을 알림 (200 OK)
        //    JSESSIONID 쿠키 삭제는 session.invalidate() 시 서블릿 컨테이너가 처리합니다.
        return ResponseEntity.ok(Map.of("logout", true));
    }

    @PostMapping("/idcheck")
    public String IdCheck(@RequestBody MemberDto member) {
        boolean result = memberService.idCheck(member.getId());
        String msg = "";
        if(result) msg = "이미 사용중인 아이디 입니다.";
        else msg = "사용이 가능한 아이디 입니다.";

        return msg;
    }
}

package me.jaeheon.springbootdeveloper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    //HTTP 응답을 표현하는 클래스입니다. 응답 상태 코드와 메시지를 설정할 수 있음
    @GetMapping("/quiz")
    public ResponseEntity<String> quiz(@RequestParam("code") int code) {
        //클라이언트에서 전달하는 쿼리 파라미터(?code=1)를 메서드의 파라미터로 매핑.
        switch (code) {
            case 1:
                return ResponseEntity.created(null).body("Created");
            case 2:
                return ResponseEntity.badRequest().body("Bad Request");
            default:
                return ResponseEntity.ok().body("OK");
        }
    }

    @PostMapping("/quiz")
    public ResponseEntity<String> quiz2(@RequestBody Code code) {
        // 요청의 JSON 데이터를 Java 객체(Code)로 변환합니다.
        // 요청 본문(body)을 매핑할 때 사용됩니다.

        switch (code.value()) {
            case 1:
                return ResponseEntity.status(403).body("Forbidden");
            default:
                return ResponseEntity.ok().body("OK");
        }
    }

    // record: Java 16에서 도입된 새로운 클래스 형태로, 불변 데이터를 표현합니다.
    // 클래스의 필드, 생성자, toString, equals, hashCode 메서드를 자동 생성합니다.
    record Code(int value) {

    }
}

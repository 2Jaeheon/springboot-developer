package org.jaeheon.springbootdeveloper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    @GetMapping("/quiz")
    public ResponseEntity<String> quiz(@RequestParam("code") int code) {

        switch (code) {
            case 1:
                // Return a 201 Created status code with the response body "Created"
                return ResponseEntity.created(null).body("Created");
            case 2:
                // Return a 400 Bad Request status code with the response body "Bad Request"
                return ResponseEntity.badRequest().body("Bad Request");
            default:
                // Return a 200 OK status code with the response body "OK"
                return ResponseEntity.ok().body("OK");
        }
    }

    @PostMapping("/quiz")
    public ResponseEntity<String> quiz2(@RequestBody Code code) {

        switch (code.value()) {
            case 1:
                // Return a 403 Forbidden status code with the response body "Forbidden"
                return ResponseEntity.status(403).body("Forbidden");
            default:
                // Return a 200 OK status code with the response body "OK"
                return ResponseEntity.status(200).body("OK");
        }
    }
}

record Code(int value) {

}
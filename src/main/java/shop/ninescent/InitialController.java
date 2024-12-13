package shop.ninescent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitialController {

    @GetMapping("/")
    public String home() {
        return "Hello, Spring Boot!";
    }
}

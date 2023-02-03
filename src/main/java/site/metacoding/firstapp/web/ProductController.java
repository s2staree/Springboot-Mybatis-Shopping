package site.metacoding.firstapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    // 메인 페이지
    @GetMapping("/")
    public String home() {
        return "home";
    }
}

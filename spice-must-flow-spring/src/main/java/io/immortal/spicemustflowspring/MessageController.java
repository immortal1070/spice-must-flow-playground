package io.immortal.spicemustflowspring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MessageController {
    @GetMapping("/test")
    public String index(@RequestParam("name") String name) {
        return "Hello, " + name + "!";
    }
}
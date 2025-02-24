package me.adityabanerjee.kafkart_backend.health;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@CrossOrigin(origins = "*")
public class HealthController {
    @GetMapping("")
    public String getHealth() {
        return "KafKaRT backend is healthy";
    }
}

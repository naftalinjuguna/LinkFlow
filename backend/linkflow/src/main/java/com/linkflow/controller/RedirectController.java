package com.linkflow.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import com.linkflow.service.RedirectService;

import java.net.URI;

@RestController
public class RedirectController {

    private final RedirectService redirectService;

    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode, HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");

        String originalUrl = redirectService.getOriginalUrl(shortCode, ipAddress, userAgent, referer);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

}

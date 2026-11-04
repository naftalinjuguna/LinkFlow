package com.linkflow.service;

import org.springframework.stereotype.Service;
import com.linkflow.repository.ShortUrlRepository;
import org.springframework.transaction.annotation.Transactional;
import com.linkflow.entity.User;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import com.linkflow.dto.CreateShortUrlRequest;
import com.linkflow.dto.ShortUrlResponse;
import com.linkflow.entity.ShortUrl;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ShortUrlService {

    @Value("${app.base-url}")
    private String baseUrl;

    private final ShortCodeGeneratorService shortCodeGeneratorService;
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlService(ShortCodeGeneratorService shortCodeGeneratorService, ShortUrlRepository shortUrlRepository) {
        this.shortCodeGeneratorService = shortCodeGeneratorService;
        this.shortUrlRepository = shortUrlRepository;
    }

    @Transactional
    public ShortUrlResponse createShortUrl(CreateShortUrlRequest request, User currentUser) {
        String shortCode = request.getCustomShortCode();

        if (shortCode == null || shortCode.isBlank()) {
            shortCode = shortCodeGeneratorService.generateShortCode();
        } else if (shortUrlRepository.existsByShortCode(shortCode)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Custom short code already exists");
        }

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(request.getOriginalUrl());
        shortUrl.setShortCode(shortCode);
        shortUrl.setUser(currentUser);
        shortUrl.setExpiresAt(request.getExpiresAt());

        ShortUrl savedShortUrl = shortUrlRepository.save(shortUrl);
        return mapToResponse(savedShortUrl);
    }



    private ShortUrlResponse mapToResponse(ShortUrl shortUrl) {
        ShortUrlResponse response = new ShortUrlResponse(
                shortUrl.getId(),
                shortUrl.getOriginalUrl(),
                shortUrl.getShortCode(),
                baseUrl + "/" + shortUrl.getShortCode(),
                shortUrl.getCreatedAt(),
                shortUrl.getExpiresAt()
        );
        return response;
    }

}

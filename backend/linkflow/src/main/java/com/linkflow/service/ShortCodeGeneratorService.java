package com.linkflow.service;

import org.springframework.stereotype.Service;

import com.linkflow.repository.ShortUrlRepository;

import java.security.SecureRandom;

@Service
public class ShortCodeGeneratorService {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 5;
    private final SecureRandom random = new SecureRandom();

    private final ShortUrlRepository shortUrlRepository;

    public ShortCodeGeneratorService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public String generateShortCode() {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            String shortCode = generateRandomCode();
            if (!shortUrlRepository.existsByShortCode(shortCode)) {
                return shortCode;
            }
        }
        throw new IllegalStateException("Unable to generate a unique short code after " + MAX_ATTEMPTS + " attempts");
    }

    private String generateRandomCode() {
        StringBuilder shortCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(ALPHABET.length());
            shortCode.append(ALPHABET.charAt(index));
        }
        return shortCode.toString();
    }

}

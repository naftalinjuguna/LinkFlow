package com.linkflow.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class CreateShortUrlRequest {

    @NotBlank(message = "Original URL cannot be blank")
    @URL(message = "Invalid URL format")
    private String originalUrl;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,30}$", message = "Custom short code must be 3-30 characters long and can only contain letters, numbers, hyphens, and underscores")
    private String customShortCode;

    @Future(message = "Expiration date must be in the future")
    private LocalDateTime expiresAt;


    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getCustomShortCode() {
        return customShortCode;
    }

    public void setCustomShortCode(String customShortCode) {
        this.customShortCode = customShortCode;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

}

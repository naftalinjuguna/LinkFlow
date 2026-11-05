package com.linkflow.service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import com.linkflow.repository.ShortUrlRepository;
import com.linkflow.repository.ClickEventRepository;
import com.linkflow.entity.ClickEvent;
import com.linkflow.entity.ShortUrl;

import java.time.LocalDateTime;


@Service
public class RedirectService {

    private final ShortUrlRepository shortUrlRepository;
    private final ClickEventRepository clickEventRepository;

    public RedirectService(ShortUrlRepository shortUrlRepository, ClickEventRepository clickEventRepository) {
        this.shortUrlRepository = shortUrlRepository;
        this.clickEventRepository = clickEventRepository;
    }

    @Transactional
    public String getOriginalUrl(String shortCode, String ipAddress, String userAgent, String referer) {

        ShortUrl shortUrl = shortUrlRepository.findByShortCode(shortCode).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ShortUrl not found"));

        if(shortUrl.getExpirationDate() != null && !shortUrl.getExpirationDate().isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "ShortUrl has expired");
        }

        ClickEvent clickEvent = new ClickEvent();

        clickEvent.setShortUrl(shortUrl);
        clickEvent.setIpAddress(ipAddress);
        clickEvent.setUserAgent(userAgent);
        clickEvent.setReferer(referer);

        clickEventRepository.save(clickEvent);


        return shortUrl.getOriginalUrl();
    }

}

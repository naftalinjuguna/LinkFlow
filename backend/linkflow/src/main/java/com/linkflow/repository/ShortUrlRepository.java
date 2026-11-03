package com.linkflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkflow.entity.ShortUrl;
import java.util.Optional;
import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
    List<ShortUrl> findAllByUser_Id(Long userId);

}

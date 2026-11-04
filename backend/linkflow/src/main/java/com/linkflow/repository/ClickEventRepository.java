package com.linkflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkflow.entity.ClickEvent;

public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {

}

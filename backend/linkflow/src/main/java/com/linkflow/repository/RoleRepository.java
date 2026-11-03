package com.linkflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkflow.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}

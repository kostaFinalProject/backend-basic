package com.sportuniform.repository;

import com.sportuniform.domain.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findBySize(String size);
}

package com.sportuniform.repository;

import com.sportuniform.domain.Size;

import java.util.Optional;

public interface SizeRepository extends ReadOnlyRepository<Size, Long> {
    Optional<Size> findBySize(String size);
}

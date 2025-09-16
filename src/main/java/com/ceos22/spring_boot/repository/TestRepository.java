package com.ceos22.spring_boot.repository;

import com.ceos22.spring_boot.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {}
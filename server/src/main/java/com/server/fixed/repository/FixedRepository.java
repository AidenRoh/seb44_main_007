package com.server.fixed.repository;


import com.server.fixed.entity.Fixed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FixedRepository extends JpaRepository<Fixed, Long> {

    List<Fixed> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
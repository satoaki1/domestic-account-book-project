package com.example.domesticaccountbookproject.repository;

import com.example.domesticaccountbookproject.entity.SpentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpentDetailRepository extends JpaRepository<SpentDetail, Long> {
}

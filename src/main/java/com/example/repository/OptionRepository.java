package com.example.repository;

import java.util.List;

import com.example.entity.Option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    Option findByOptionno(long no);

    List<Option> findAllByProduct_productno(Long productno);
}

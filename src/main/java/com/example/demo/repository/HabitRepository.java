package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Habit;


@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUser_Id(Long id);
}

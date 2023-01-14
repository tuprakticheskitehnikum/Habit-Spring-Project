package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Habit;
import com.example.demo.service.HabitService;

@RestController
@RequestMapping("/api/v1/habit")
public class HabitController {
    
    @Autowired
    private HabitService habitService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ResponseEntity<Habit> addHabit(@RequestBody Habit habit){
        return ResponseEntity.ok(habitService.addHabit(habit));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ResponseEntity<Habit> updateHabit(@PathVariable Long id, @RequestBody Habit habit) {
        return ResponseEntity.ok(habitService.updateHabit(id, habit));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public void deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ResponseEntity<Habit> getHabit(@PathVariable Long id) {
        return ResponseEntity.ok(habitService.getHabit(id));
    }

    @GetMapping("/all/{userId}")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ResponseEntity<List<Habit>> getHabits(@PathVariable Long userId) {
        return ResponseEntity.ok(habitService.getHabitsByUser(userId));
    }
    
}

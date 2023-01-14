package com.example.demo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Habit;
import com.example.demo.exception.HabitNotFoundException;
import com.example.demo.repository.HabitRepository;


@Service
public class HabitService {
    
    @Autowired
    private HabitRepository habitRepository;

    public Habit addHabit(Habit habit){
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Long id, Habit habit) {
        Habit existingHabit = getHabit(id);
        existingHabit.setName(habit.getName());
        existingHabit.setFrequency(habit.getFrequency());
        existingHabit.setUnit(habit.getUnit());
        existingHabit.setRecurring(habit.getRecurring());
        return habitRepository.save(existingHabit);
    }

    public void deleteHabit(Long id) {
        habitRepository.deleteById(id);
    }

    public List<Habit> getHabitsByUser(Long id) {
        
        return habitRepository.findByUser_Id(id);
    }

    public Habit getHabit(Long id) {
        Optional<Habit> optionalHabit = habitRepository.findById(id);
        if(optionalHabit.isPresent()){
            return optionalHabit.get();
        }
        throw new HabitNotFoundException("Habit not found");
    }

}

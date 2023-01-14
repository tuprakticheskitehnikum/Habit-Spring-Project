package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "habits")
public class Habit {

    Habit(){

    }

    Habit(String name, int frequency, String unit, boolean isRecurring, User user){
        this.name = name;
        this.frequency = frequency;
        this.unit = unit;
        this.isRecurring = isRecurring;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int frequency;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private boolean isRecurring;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    public String getName(){
        return name;
    }

    public int getFrequency(){
        return frequency;
    }

    public String getUnit(){
        return unit;
    }

    public boolean getRecurring(){
        return isRecurring;
    }

    public User getUser(){
        return user;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }
    
    public void setUserId(User user){
        this.user = user;
    }

}

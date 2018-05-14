package com.surveyapp.backend.persistence.domain.backend;

public class Counter {
    private int count ;

    public Counter(int value) {
        this.count = value;
    }


    public Counter() {
        this.count =0;
    }

    public void resetCount(){
        this.count =0;
    }

    public void incrementCount(){
        this.count++;
    }
    public int getValue() {
        return count;
    }

    public void setValue(int value) {
        this.count = value;
    }
}

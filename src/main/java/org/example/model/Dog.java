package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dog extends Animal {
    public Dog(int id, String name, String medicalCondition) {
        super(id, name, "Dog", medicalCondition);
    }

    public Dog() {
        super();
    }

    @Override
    public String makeSound() {
        return "Woof!";
    }
}
package org.example.model;

public class Cat extends Animal {

    public Cat(int id, String name, String medicalCondition) {
        super(id, name, "Cat", medicalCondition);
    }

    public Cat() {
        super();
    }

    @Override
    public String makeSound() {
        return "Meow!";
    }
}
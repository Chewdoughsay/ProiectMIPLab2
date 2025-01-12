package org.example.model;

public abstract class Animal {
    private int id;
    private String name;
    private String species;
    private String medicalCondition;

    public Animal() {}

    public Animal(int id, String name, String species, String medicalCondition) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.medicalCondition = medicalCondition;
    }

    public abstract String makeSound();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", medicalCondition='" + medicalCondition + '\'' +
                '}';
    }
}
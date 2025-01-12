package org.example.interfaces;

import org.example.model.Animal;
import org.example.model.Owner;

import java.util.List;

public interface IServices {
    void addOwnerWithPet(Owner owner, Animal pet);
    void addPetToOwner(int ownerId, Animal pet);
    void healAnimal(int ownerId, int animalId);
    List<Owner> getRemainingOwners();
    List<Animal> getRemainingAnimals();
}
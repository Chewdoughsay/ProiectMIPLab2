package org.example;

import org.example.interfaces.IServices;
import org.example.model.Animal;
import org.example.model.Cat;
import org.example.model.Dog;
import org.example.model.Owner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Services implements IServices {
    private final String FILE_PATH;
    private List<Owner> owners;

    public Services() {
        this.FILE_PATH = "src/main/resources/clients.txt";
        this.owners = new ArrayList<>();
        loadFromFile();
    }

    public Services(String filePath) {
        this.FILE_PATH = filePath;
        this.owners = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile() {
        owners.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            Owner currentOwner = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Owner:")) {
                    String[] ownerData = line.substring(6).split(",");
                    currentOwner = new Owner(
                            Integer.parseInt(ownerData[0].trim()),
                            ownerData[1].trim(),
                            ownerData[2].trim()
                    );
                    owners.add(currentOwner);
                } else if (line.startsWith("Pet:") && currentOwner != null) {
                    String[] petData = line.substring(4).split(",");
                    Animal animal;
                    if (petData[2].trim().equals("Cat")) {
                        animal = new Cat(
                                Integer.parseInt(petData[0].trim()),
                                petData[1].trim(),
                                petData[3].trim()
                        );
                    } else {
                        animal = new Dog(
                                Integer.parseInt(petData[0].trim()),
                                petData[1].trim(),
                                petData[3].trim()
                        );
                    }
                    currentOwner.addPet(animal);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Owner owner : owners) {
                writer.write("Owner:" + owner.getId() + "," + owner.getName() + "," + owner.getPhoneNumber());
                writer.newLine();
                for (Animal pet : owner.getPets()) {
                    writer.write("Pet:" + pet.getId() + "," + pet.getName() + "," +
                            pet.getSpecies() + "," + pet.getMedicalCondition());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public void addOwnerWithPet(Owner owner, Animal pet) {
        owner.addPet(pet);
        owners.add(owner);
        saveToFile();
    }

    @Override
    public void addPetToOwner(int ownerId, Animal pet) {
        Owner owner = owners.stream()
                .filter(o -> o.getId() == ownerId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        owner.addPet(pet);
        saveToFile();
    }

    @Override
    public void healAnimal(int ownerId, int animalId) {
        Owner owner = owners.stream()
                .filter(o -> o.getId() == ownerId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        owner.getPets().removeIf(pet -> pet.getId() == animalId);

        if (owner.getPets().isEmpty()) {
            owners.remove(owner);
        }

        saveToFile();
    }

    @Override
    public List<Owner> getRemainingOwners() {
        return new ArrayList<>(owners);
    }

    @Override
    public List<Animal> getRemainingAnimals() {
        return owners.stream()
                .flatMap(owner -> owner.getPets().stream())
                .collect(Collectors.toList());
    }
}
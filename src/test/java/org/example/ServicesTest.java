package org.example;

import org.example.interfaces.IServices;
import org.example.model.Animal;
import org.example.model.Cat;
import org.example.model.Dog;
import org.example.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicesTest {
    private IServices services;
    private static final String TEST_FILE_PATH = "src/test/resources/test_clients.txt";

    @BeforeEach
    void setUp() {
        // Creăm un fișier de test gol înainte de fiecare test
        try {
            File file = new File(TEST_FILE_PATH);
            file.createNewFile();
            Files.write(Paths.get(TEST_FILE_PATH), new byte[0]);
        } catch (IOException e) {
            fail("Nu s-a putut crea fișierul de test");
        }

        services = new Services(TEST_FILE_PATH);
    }

    @Test
    void testAddOwnerWithPet() {
        // Arrange
        Owner owner = new Owner(1, "John", "0722123456");
        Animal pet = new Dog(1, "Rex", "Vaccin necesar");

        // Act
        services.addOwnerWithPet(owner, pet);
        List<Owner> owners = services.getRemainingOwners();

        // Assert
        assertEquals(1, owners.size());
        assertEquals("John", owners.get(0).getName());
        assertEquals(1, owners.get(0).getPets().size());
        assertEquals("Rex", owners.get(0).getPets().get(0).getName());
    }

    @Test
    void testAddPetToOwner() {
        // Arrange
        Owner owner = new Owner(1, "John", "0722123456");
        Animal firstPet = new Dog(1, "Rex", "Vaccin necesar");
        services.addOwnerWithPet(owner, firstPet);

        // Act
        Animal secondPet = new Cat(2, "Whiskers", "Control anual");
        services.addPetToOwner(1, secondPet);

        // Assert
        List<Owner> owners = services.getRemainingOwners();
        assertEquals(1, owners.size());
        assertEquals(2, owners.get(0).getPets().size());
    }

    @Test
    void testAddPetToNonExistentOwner() {
        // Arrange
        Animal pet = new Dog(1, "Rex", "Vaccin necesar");

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                services.addPetToOwner(999, pet)
        );
    }

    @Test
    void testHealAnimal() {
        // Arrange
        Owner owner = new Owner(1, "John", "0722123456");
        Animal pet1 = new Dog(1, "Rex", "Vaccin necesar");
        Animal pet2 = new Cat(2, "Whiskers", "Control anual");
        services.addOwnerWithPet(owner, pet1);
        services.addPetToOwner(1, pet2);

        // Act
        services.healAnimal(1, 1);

        // Assert
        List<Owner> owners = services.getRemainingOwners();
        assertEquals(1, owners.size());
        assertEquals(1, owners.get(0).getPets().size());
        assertEquals("Whiskers", owners.get(0).getPets().get(0).getName());
    }

    @Test
    void testHealLastAnimalRemovesOwner() {
        // Arrange
        Owner owner = new Owner(1, "John", "0722123456");
        Animal pet = new Dog(1, "Rex", "Vaccin necesar");
        services.addOwnerWithPet(owner, pet);

        // Act
        services.healAnimal(1, 1);

        // Assert
        List<Owner> owners = services.getRemainingOwners();
        assertTrue(owners.isEmpty());
    }

    @Test
    void testGetRemainingOwners() {
        // Arrange
        Owner owner1 = new Owner(1, "John", "0722123456");
        Owner owner2 = new Owner(2, "Jane", "0733123456");
        Animal pet1 = new Dog(1, "Rex", "Vaccin necesar");
        Animal pet2 = new Cat(2, "Whiskers", "Control anual");

        services.addOwnerWithPet(owner1, pet1);
        services.addOwnerWithPet(owner2, pet2);

        // Act
        List<Owner> owners = services.getRemainingOwners();

        // Assert
        assertEquals(2, owners.size());
    }

    @Test
    void testGetRemainingAnimals() {
        // Arrange
        Owner owner = new Owner(1, "John", "0722123456");
        Animal pet1 = new Dog(1, "Rex", "Vaccin necesar");
        Animal pet2 = new Cat(2, "Whiskers", "Control anual");

        services.addOwnerWithPet(owner, pet1);
        services.addPetToOwner(1, pet2);

        // Act
        List<Animal> animals = services.getRemainingAnimals();

        // Assert
        assertEquals(2, animals.size());
        assertTrue(animals.stream().anyMatch(a -> a.getName().equals("Rex")));
        assertTrue(animals.stream().anyMatch(a -> a.getName().equals("Whiskers")));
    }

    @Test
    void testPersistenceAcrossServiceInstances() {
        // Arrange
        Owner owner = new Owner(1, "John", "0722123456");
        Animal pet = new Dog(1, "Rex", "Vaccin necesar");
        services.addOwnerWithPet(owner, pet);

        // Act
        IServices newServices = new Services(TEST_FILE_PATH);
        List<Owner> owners = newServices.getRemainingOwners();

        // Assert
        assertEquals(1, owners.size());
        assertEquals("John", owners.get(0).getName());
        assertEquals(1, owners.get(0).getPets().size());
        assertEquals("Rex", owners.get(0).getPets().get(0).getName());
    }
}
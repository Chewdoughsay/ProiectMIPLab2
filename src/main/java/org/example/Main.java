package org.example;

import org.example.interfaces.IServices;
import org.example.model.Animal;
import org.example.model.Cat;
import org.example.model.Dog;
import org.example.model.Owner;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IServices services = new Services();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addOwnerWithPet();
                    break;
                case 2:
                    addPetToOwner();
                    break;
                case 3:
                    healAnimal();
                    break;
                case 4:
                    showRemainingOwners();
                    break;
                case 5:
                    showRemainingAnimals();
                    break;
                case 0:
                    System.out.println("La revedere!");
                    return;
                default:
                    System.out.println("Opțiune invalidă!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== MENIU CLINICĂ VETERINARĂ ===");
        System.out.println("1. Adaugă owner nou cu animal");
        System.out.println("2. Adaugă animal nou la owner existent");
        System.out.println("3. Vindecă animal");
        System.out.println("4. Afișează ownerii rămași");
        System.out.println("5. Afișează animalele rămase");
        System.out.println("0. Ieșire");
        System.out.print("Alegeti o opțiune: ");
    }

    private static void addOwnerWithPet() {
        System.out.println("\n=== ADAUGĂ OWNER NOU CU ANIMAL ===");

        System.out.print("ID Owner: ");
        int ownerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nume Owner: ");
        String ownerName = scanner.nextLine();

        System.out.print("Telefon Owner: ");
        String phoneNumber = scanner.nextLine();

        Owner owner = new Owner(ownerId, ownerName, phoneNumber);

        System.out.println("\nTip animal (1 - Câine, 2 - Pisică): ");
        int animalType = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID Animal: ");
        int animalId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nume Animal: ");
        String animalName = scanner.nextLine();

        System.out.print("Condiție Medicală: ");
        String medicalCondition = scanner.nextLine();

        Animal animal;
        if (animalType == 1) {
            animal = new Dog(animalId, animalName, medicalCondition);
        } else {
            animal = new Cat(animalId, animalName, medicalCondition);
        }

        services.addOwnerWithPet(owner, animal);
        System.out.println("Owner și animal adăugați cu succes!");
    }

    private static void addPetToOwner() {
        System.out.println("\n=== ADAUGĂ ANIMAL LA OWNER EXISTENT ===");

        System.out.print("ID Owner: ");
        int ownerId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Tip animal (1 - Câine, 2 - Pisică): ");
        int animalType = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID Animal: ");
        int animalId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nume Animal: ");
        String animalName = scanner.nextLine();

        System.out.print("Condiție Medicală: ");
        String medicalCondition = scanner.nextLine();

        Animal animal;
        if (animalType == 1) {
            animal = new Dog(animalId, animalName, medicalCondition);
        } else {
            animal = new Cat(animalId, animalName, medicalCondition);
        }

        try {
            services.addPetToOwner(ownerId, animal);
            System.out.println("Animal adăugat cu succes!");
        } catch (RuntimeException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private static void healAnimal() {
        System.out.println("\n=== VINDECĂ ANIMAL ===");

        System.out.print("ID Owner: ");
        int ownerId = scanner.nextInt();

        System.out.print("ID Animal: ");
        int animalId = scanner.nextInt();

        try {
            services.healAnimal(ownerId, animalId);
            System.out.println("Animal vindecat cu succes!");
        } catch (RuntimeException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    private static void showRemainingOwners() {
        System.out.println("\n=== OWNERI RĂMAȘI ===");
        List<Owner> owners = services.getRemainingOwners();
        if (owners.isEmpty()) {
            System.out.println("Nu mai există owneri!");
        } else {
            for (Owner owner : owners) {
                System.out.println(owner);
            }
        }
    }

    private static void showRemainingAnimals() {
        System.out.println("\n=== ANIMALE RĂMASE ===");
        List<Animal> animals = services.getRemainingAnimals();
        if (animals.isEmpty()) {
            System.out.println("Nu mai există animale de vindecat!");
        } else {
            for (Animal animal : animals) {
                System.out.println(animal);
            }
        }
    }
}
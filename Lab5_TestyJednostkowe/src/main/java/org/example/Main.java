package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.mage.Mage;
import org.example.mage.MageController;
import org.example.mage.MageRepository;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        MageRepository mageRepository = new MageRepository(entityManager);
        MageController mageController = new MageController(mageRepository);

        System.out.println(mageController.save("Hubert", 11));
        System.out.println(mageController.find("Hubert"));

        entityManager.close();
        entityManagerFactory.close();
    }
}
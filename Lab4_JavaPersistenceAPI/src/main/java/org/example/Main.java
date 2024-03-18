package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        em.persist(new Mage("Hubert", 10, null));
        entityTransaction.commit();


        List<Mage> mages=  em.createNativeQuery("SELECT  * FROM Mage", Mage.class).getResultList();

        em.close();
        emf.close();
    }
}
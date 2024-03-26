package org.example.mage;


import jakarta.persistence.EntityManager;

import java.util.Optional;

public class MageRepository {
    private EntityManager entityManager;

    public MageRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    public Optional<Mage> find(String name){
        return Optional.ofNullable(entityManager.find(Mage.class, name));
    }

    public void delete (String name){
        Optional<Mage> mage = find(name);
        if(mage.isPresent()){
            entityManager.getTransaction().begin();
            entityManager.remove(mage.get());
            entityManager.getTransaction().commit();
            return ;
        }
        throw new IllegalArgumentException("Mage doesn't exist!");
    }

    public void save(Mage mage){
        Optional<Mage> optionalMage = find(mage.getName());
        if(optionalMage.isPresent()){
            throw new IllegalArgumentException("Mage already exists!");
        }
        else{
            entityManager.getTransaction().begin();
            entityManager.persist(mage);
            entityManager.getTransaction().commit();
        }

    }


}

package org.example.mage;

import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface MageRepositoryInterface {

    public Optional<Mage> find(String name);
    public void delete (String name);
    public void save(Mage mage);


}

package mage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.mage.Mage;
import org.example.mage.MageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MageRepositoryTest {

    EntityTransaction entityTransaction = mock(EntityTransaction.class);
    EntityManager entityManager = mock(EntityManager.class);
    MageRepository mageRepository = null;
    @BeforeEach
    void setUp() {
        mageRepository = new MageRepository(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
    }

    @Test
    public void findTestReturnObject(){
        //Arrange
        Mage mage = new Mage("test", 1);
        //Act
        when(entityManager.find(Mage.class, "test")).thenReturn(mage);
        //Assert
        assertTrue(mageRepository.find("test").isPresent());
    }

    @Test
    public void findTestReturnMage(){
        //Arrange
        Mage mage = new Mage("mage", 1);
        //Act
        when(entityManager.find(Mage.class, "mage")).thenReturn(mage);
        //Assert
        assertEquals(mage.getName(), "mage");
    }

    @Test
    public void saveTestExitingMage(){
        //Arrange
        Mage mage = new Mage("mage", 1);
        //Act
        when(entityManager.find(Mage.class, "mage")).thenReturn(mage);
        //Assert
        try{
            mageRepository.save(mage);
        }catch (IllegalArgumentException e){
            assertEquals(e.getMessage(), "Mage already exists!");
        }
    }

    @Test
    public void saveTestNewMage(){
        //Arrange
        Mage mage = new Mage("NewMage", 1);
        //Act
        when(entityManager.find(Mage.class, "NewMage")).thenReturn(null);
        //Assert
        mageRepository.save(mage);
    }

}

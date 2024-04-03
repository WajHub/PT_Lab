package mage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.mage.Mage;
import org.example.mage.MageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
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
        when(entityManager.find(Mage.class, "test")).thenReturn(mage);
        //Act
        Optional<Mage> mage2 = mageRepository.find("test");
        //Assert
        assertTrue(mage2.isPresent());
    }

    @Test
    public void findTestReturnMage(){
        //Arrange
        Mage mage = new Mage("mage", 1);
        when(entityManager.find(Mage.class, "mage")).thenReturn(mage);
        //Act
        Optional<Mage> mage2 = mageRepository.find("mage");
        //Assert
        assertEquals(mage2.get().getName(), "mage");
    }

    @Test
    public void findNotExistingMage(){
        //Arrange
        when(entityManager.find(Mage.class, "mage")).thenReturn(null);
        //Act
        Optional<Mage> mage = mageRepository.find(("mage"));
        //Assert
        assertTrue(mage.isEmpty());
    }

    @Test
    public void saveTestExitingMage(){
        Mage mage = new Mage("mage", 1);
        when(entityManager.find(Mage.class, "mage")).thenReturn(mage);
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
        when(entityManager.find(Mage.class, "NewMage")).thenReturn(null);
        //Act
        mageRepository.save(mage);
        //Assert
        verify(entityManager).persist(mage);
    }

    @Test
    public void deleteNotExisting(){
        //Arrange
        when(entityManager.find(Mage.class, "Mage")).thenReturn(null);
        //Assert
        assertThrows(IllegalArgumentException.class, () -> mageRepository.delete("Mage"));
    }

}

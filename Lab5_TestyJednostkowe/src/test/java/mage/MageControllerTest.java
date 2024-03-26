package mage;

import org.example.mage.Mage;
import org.example.mage.MageController;
import org.example.mage.MageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MageControllerTest {
    MageRepository mageRepository;
    MageController mageController;

    @BeforeEach
    void setUp(){
        mageRepository = mock(MageRepository.class);
        mageController = new MageController(mageRepository);
    }

    @Test
    public void findTest(){
        //Arrange
        Mage mage = new Mage("test", 1);
        //Act
        when(mageRepository.find("test")).thenReturn(Optional.of(mage));
        String result = mageController.find("test");
        //Assert
        assertEquals(mage.toString(), result);
    }

    @Test
    public void notFoundTest(){
        //Arrange
        //Act
        when(mageRepository.find("test")).thenReturn(Optional.empty());
        String result = mageController.find("test");
        //Assert
        assertEquals("not found", result);
    }
}

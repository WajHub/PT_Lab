package mage;

import org.example.mage.Mage;
import org.example.mage.MageController;
import org.example.mage.MageRepository;
import org.example.mage.MageRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MageControllerTest {
    MageRepositoryInterface mageRepository;
    MageController mageController;

    @BeforeEach
    void setUp(){
        mageRepository = mock(MageRepositoryInterface.class);
        mageController = new MageController(mageRepository);
    }

    @Test
    public void findTest(){
        //Arrange
        Mage mage = new Mage("test", 1);
        when(mageRepository.find("test")).thenReturn(Optional.of(mage));
        //Act
        String result = mageController.find("test");
        //Assert
        assertEquals(mage.toString(), result);
    }

    @Test
    public void notFoundTest(){
        //Arrange
        when(mageRepository.find("test")).thenReturn(Optional.empty());
        //Act
        String result = mageController.find("test");
        //Assert
        assertEquals("not found", result);
    }

    @Test
    public void deleteExistingMage(){
        //Arrange
        Mage mage = new Mage("test",1 );
        when(mageRepository.find("test")).thenReturn(Optional.of(mage));
        //Act
        String result = mageController.delete("test");
        //Assert
        assertEquals("done", result);
    }

    @Test
    public void deleteNotExistingMage(){
        //Arrange
        doThrow(new IllegalArgumentException()).when(mageRepository).delete("test");
        //Act
        String result = mageController.delete("test");
        //Assert
        assertEquals("not found", result);
    }

    @Test
    public void saveNotExistingMage(){
        //Arrange
        when(mageRepository.find("test")).thenReturn(null);
        //Act
        String result = mageController.save("test", 1);
        //Assert
        assertEquals("done", result);
    }

    @Test
    public void saveExistingMage(){
        //Arrange
        Mage mage = new Mage("test", 1);
        doThrow(new IllegalArgumentException()).when(mageRepository).save(mage);
        //Act
        String result = mageController.save("test", 1);
        //Assert
        assertEquals("bad request", result);
    }
}

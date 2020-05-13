package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryStore;
  private TorpedoStore secondaryStore;

  @BeforeEach
  public void init(){
    primaryStore = mock(TorpedoStore.class);
    secondaryStore = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryStore,secondaryStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_primaryEmpty_SecondaryNotEmpty(){
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_primaryNotEmpty_itFails(){
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.fire(1)).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_bothStores_are_empty(){
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_secondaryEmpty(){
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.isEmpty()).thenReturn(true);
    when(primaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_bothStores_empty(){
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(true);


    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_primaryEmpty_secondaryNotEmpty_itFails(){
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_primaryFires_thenSecondaryFires(){
    // Arrange
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_primaryFires_SecondaryEmpty(){
    // Arrange
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStore, times(2)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_primaryFiresBecomesEmpty_SecondaryEmpty(){
    // Arrange
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    when(primaryStore.isEmpty()).thenReturn(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(0)).fire(1);
    assertEquals(false, result);
  }

}

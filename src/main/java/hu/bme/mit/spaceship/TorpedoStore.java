package hu.bme.mit.spaceship;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Class storing and managing the torpedoes of a ship
 *
 * (Deliberately contains bugs.)
 */
public class TorpedoStore {

  private final Random rand;

  // rate of failing to fire torpedos [0.0, 1.0]
  private double FAILURE_RATE = 0.0; // NOSONAR

  private int torpedoCount = 0;

  public TorpedoStore(final int numberOfTorpedos) throws NoSuchAlgorithmException {
    this.torpedoCount = numberOfTorpedos;
    rand = SecureRandom.getInstanceStrong();
    // update failure rate if it was specified in an environment variable
    final String failureEnv = System.getenv("IVT_RATE");
    if (failureEnv != null) {
      try {
        FAILURE_RATE = Double.parseDouble(failureEnv);
      } catch (final NumberFormatException nfe) {
        FAILURE_RATE = 0.0;
      }
    }
  }

  public boolean fire(final int numberOfTorpedos) {
    if (numberOfTorpedos < 1 || numberOfTorpedos > this.torpedoCount) {
      throw new IllegalArgumentException("numberOfTorpedos");
    }

    boolean success = false;

    // simulate random overheating of the launcher bay which prevents firing
    final double r = this.rand.nextDouble();

    if (r >= FAILURE_RATE) {
      // successful firing
      this.torpedoCount -= numberOfTorpedos;
      success = true;
    } else {
      // simulated failure
      success = false;
    }

    return success;
  }

  public boolean isEmpty(){
    return this.torpedoCount <= 0;
  }

  public int getTorpedoCount() {
    return this.torpedoCount;
  }
}

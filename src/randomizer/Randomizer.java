package randomizer;

import java.util.Random;

/**
 * This interface exposes operations for generating random number.
 */
public interface Randomizer {
  /**
   * Get a random number by invoking this method.
   * @param bound the generated number will be between 0 and {@code bound}.
   * @return  generated number.
   */
  int getRandomNum(int bound);

  /**
   * Get the random object initialized by the class.
   * @return  random object.
   */
  Random getRandom();
}

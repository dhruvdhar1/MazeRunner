package randomizer;

import java.util.Random;

/**
 * This class implements operations for generating random numbers.
 */
public class RandomizerImpl implements Randomizer {

  private final Random random;

  /**
   * This constructor returns a non-seeded instance of this class.
   */
  public RandomizerImpl() {
    this.random = new Random();
  }

  /**
   * This constructor returns a pre-seeded instance of this class.
   * @param seed value.
   */
  public RandomizerImpl(int seed) {
    this.random = new Random(seed);
  }

  @Override
  public int getRandomNum(int bound) {
    return random.nextInt(bound);
  }

  @Override
  public Random getRandom() {
    return random;
  }
}

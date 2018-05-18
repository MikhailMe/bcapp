package UtilsTest;

import com.bbs.handlersystem.Utils.RandomGenerator;
import org.junit.Assert;
import org.junit.Test;

public class RandomGeneratorTest {

    private static final int LENGTH = 10;

    @Test
    public void testRandomString() {
        for (int i = 0; i < LENGTH*LENGTH; i++) {
            String one = RandomGenerator.createRandomString(LENGTH);
            String two = RandomGenerator.createRandomString(LENGTH);
            Assert.assertNotEquals(one, two);
            String three = RandomGenerator.createRandomString(LENGTH);
            Assert.assertNotEquals(one, three);
            Assert.assertNotEquals(two, three);
        }
    }

    @Test
    public void testRandomDigitString() {
        for (int i = 0; i < LENGTH*LENGTH; i++) {
            String one = RandomGenerator.createRandomDigitsString(LENGTH);
            String two = RandomGenerator.createRandomDigitsString(LENGTH);
            Assert.assertNotEquals(one, two);
            String three = RandomGenerator.createRandomDigitsString(LENGTH);
            Assert.assertNotEquals(one, three);
            Assert.assertNotEquals(two, three);
        }
    }

}

package UtilsTest;

import com.bbs.handlersystem.Utils.RandomGenerator;
import org.junit.Assert;
import org.junit.Test;

public class RandomGeneratorTest {

    private static final int LENGTH = 10;

    @Test
    public void testRandomString() {
        for (int i = 0; i < LENGTH*LENGTH; i++) {
            String one = RandomGenerator.randomString(LENGTH);
            String two = RandomGenerator.randomString(LENGTH);
            Assert.assertNotEquals(one, two);
            String three = RandomGenerator.randomString(LENGTH);
            Assert.assertNotEquals(one, three);
            Assert.assertNotEquals(two, three);
        }
    }

    @Test
    public void testRandomDigitString() {
        for (int i = 0; i < LENGTH*LENGTH; i++) {
            String one = RandomGenerator.randomDigitsString(LENGTH);
            String two = RandomGenerator.randomDigitsString(LENGTH);
            Assert.assertNotEquals(one, two);
            String three = RandomGenerator.randomDigitsString(LENGTH);
            Assert.assertNotEquals(one, three);
            Assert.assertNotEquals(two, three);
        }
    }

}

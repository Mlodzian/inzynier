import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Mldz on 2015-11-21.
 */

public class RunnerTest {

    @Test(expected = NullPointerException.class)
    public void checkNull(){
        throw new NullPointerException();
    }

    @Test
    public void checkTrueAssert(){
        Assert.assertTrue(true);
    }

    @Test
    public void checkfail(){
    }
}

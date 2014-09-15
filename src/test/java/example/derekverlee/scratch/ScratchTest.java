package example.derekverlee.scratch;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScratchTest {

    @Test
    public void metaTest() {
        assertThat(true, is(true));
    }

    @Test
    public void trival_ingrediant() {
        Scratch.Ingredient<Integer> theNumber5 = new Scratch.Ingredient<Integer>() {
            @Override
            public Integer fetch() {
                return 5;
            }
        };

        assertThat(theNumber5.fetch(),is(5));
    }


}
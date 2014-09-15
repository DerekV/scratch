package example.derekverlee.scratch;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
            public Integer obtain() {
                return 5;
            }
        };

        assertThat(theNumber5.obtain(), is(5));
    }

    @Test
    public void cook_returns_a_Cooker() {
        Scratch.Recipe recipe = Scratch.createRecipe();
        assertThat(recipe, is(notNullValue()));
    }

    @Test
    public void cooking_with_one_step() {
        Scratch.Step doNothing = new Scratch.Step() {

        };

        Scratch.createRecipe().step(doNothing).cook();
    }

}
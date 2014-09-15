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
        Scratch.Recipe<Void> recipe = Scratch.createRecipe(Void.class);
        assertThat(recipe, is(notNullValue()));
    }

    @Test
    public void cooking_with_nil_step() {
        Scratch.Step<Void,Void> doNothing = new Scratch.Step<Void,Void>() {

            @Override
            public Void apply(Void out) {
                // do nothing
                return null;
            }
        };

        Scratch.createRecipe(Void.class).step(doNothing).cook();
    }

    @Test
    public void cooking_up_a_number() {
        Scratch.Step<Void,Integer> just7 = new Scratch.Step<Void,Integer>() {
            @Override
            public Integer apply(Void in) {
                return 7;
            }
        };

        Scratch.Recipe<Integer> recipeFor7 = Scratch.createRecipe(Integer.class).step(just7);
        assertThat(recipeFor7.cook(),is(7));
    }

    @Test
    public void adding_up_some_numbers() {
        Scratch.Step<Void,Integer> just7 = new Scratch.Step<Void,Integer>() {
            @Override
            public Integer apply(Void in) {
                return 7;
            }
        };

        Scratch.Step<Integer,Integer> add4 = new Scratch.Step<Integer,Integer>() {
            @Override
            public Integer apply(Integer in) {
                return in + 4;
            }
        };

        Scratch.Recipe<Integer> recipeFor7 = Scratch.createRecipe(Integer.class).step(just7).step(add4);
        assertThat(recipeFor7.cook(),is(11));
    }

    @Test
    public void change_up_the_types() {
        Scratch.Step<Void,Integer> just7 = new Scratch.Step<Void,Integer>() {
            @Override
            public Integer apply(Void in) {
                return 7;
            }
        };

        Scratch.Step<Integer,Integer> add4 = new Scratch.Step<Integer,Integer>() {
            @Override
            public Integer apply(Integer in) {
                return in + 4;
            }
        };


        Scratch.Step<Integer, String> intToString = new Scratch.Step<Integer, String>() {
            @Override
            public String apply(Integer in) {
                return Integer.toString(in);
            }
        };

        Scratch.Recipe<String> recipeFor7 = Scratch.createRecipe(String.class).step(just7).step(add4).step(intToString);
        assertThat(recipeFor7.cook(),is("11"));
    }

}
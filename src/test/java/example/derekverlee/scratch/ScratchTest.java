package example.derekverlee.scratch;

import org.junit.Test;

import static example.derekverlee.scratch.Scratch.createRecipe;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScratchTest {

    public static final Scratch.Step<Void, Integer> JUST_7 = new Scratch.Step<Void, Integer>() {
        @Override
        public Integer apply(Void in) {
            return 7;
        }
    };
    public static final Scratch.Step<Integer, Integer> ADD_4 = new Scratch.Step<Integer, Integer>() {
        @Override
        public Integer apply(Integer in) {
            return in + 4;
        }
    };
    public static final PassThrough<Integer> PASS_THROUGH_INT = new PassThrough<>();


    @Test
    public void cooking_with_nil_step() {
        Scratch.Step<Void, Void> doNothing = new Scratch.Step<Void, Void>() {

            @Override
            public Void apply(Void out) {
                // do nothing
                return null;
            }
        };

        createRecipe(Void.class).startWith(doNothing).lastly(doNothing).cook();
    }

    @Test
    public void cooking_up_a_number() {
        Scratch.Recipe<Integer> recipeFor7 = createRecipe(Integer.class).startWith(JUST_7).lastly(PASS_THROUGH_INT);
        assertThat(recipeFor7.cook(), is(7));
    }

    @Test
    public void adding_up_some_numbers() {
        Scratch.Recipe<Integer> recipeFor7 = createRecipe(Integer.class).startWith(JUST_7).lastly(ADD_4);
        assertThat(recipeFor7.cook(), is(11));
    }

    @Test
    public void change_up_the_types() {
        Scratch.Step<Integer, String> intToString = new Scratch.Step<Integer, String>() {
            @Override
            public String apply(Integer in) {
                return Integer.toString(in);
            }
        };

        Scratch.Recipe<String> recipeFor7 =
                createRecipe(String.class)
                        .startWith(JUST_7)
                        .step(ADD_4)
                        .lastly(intToString);
        assertThat(recipeFor7.cook(), is("11"));
    }


    private static class PassThrough<T> implements Scratch.Step<T,T> {
        @Override
        public T apply(T in) {
            return in;
        }
    };
}
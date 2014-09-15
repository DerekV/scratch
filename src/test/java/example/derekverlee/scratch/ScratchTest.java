package example.derekverlee.scratch;

import org.junit.Test;

import static example.derekverlee.scratch.Scratch.createRecipe;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScratchTest {

    public static final Scratch.Step<Void, Integer> JUST_7 = new Scratch.Step<Void, Integer>() {
        @Override
        public void apply(Void in, Scratch.StepResultPasser<Integer> passResult) {
            passResult.pass(7);
        }
    };
    public static final Scratch.Step<Integer, Integer> ADD_4 = new Scratch.Step<Integer, Integer>() {
        @Override
        public void apply(Integer in, Scratch.StepResultPasser<Integer> passResult) {
            passResult.pass(in + 4);
        }
    };
    public static final PassThrough<Integer> PASS_THROUGH_INT = new PassThrough<>();
    public static final PassThrough<Void> PASS_THROUGH_NULL = new PassThrough<Void>();


    @Test
    public void cooking_with_nil_step() throws Exception {
        createRecipe(Void.class).startWith(PASS_THROUGH_NULL).lastly(PASS_THROUGH_NULL).cook();
    }

    @Test
    public void cooking_up_a_number() throws Exception {
        Scratch.Recipe<Integer> recipeFor7 = createRecipe(Integer.class).startWith(JUST_7).lastly(PASS_THROUGH_INT);
        assertThat(recipeFor7.cook(), is(7));
    }

    @Test
    public void adding_up_some_numbers() throws Exception {
        Scratch.Recipe<Integer> recipeFor7 = createRecipe(Integer.class).startWith(JUST_7).lastly(ADD_4);
        assertThat(recipeFor7.cook(), is(11));
    }

    @Test
    public void change_up_the_types() throws Exception {
        Scratch.Step<Integer, String> intToString = new Scratch.Step<Integer, String>() {
            @Override
            public void apply(Integer in, Scratch.StepResultPasser<String> result) {
                result.pass(Integer.toString(in));
            }
        };

        Scratch.Recipe<String> recipeFor7 =
                createRecipe(String.class)
                        .startWith(JUST_7)
                        .step(ADD_4)
                        .lastly(intToString);
        assertThat(recipeFor7.cook(), is("11"));
    }


    @Test
    public void async_step() throws Exception {
        Scratch.Step<Integer, String> intToString = new Scratch.Step<Integer, String>() {
            @Override
            public void apply(Integer in, Scratch.StepResultPasser<String> result) {
                result.pass(Integer.toString(in));
            }
        };

        Scratch.Step<String, String> asyncAddWord = new Scratch.Step<String, String>() {
            @Override
            public void apply(final String in, final Scratch.StepResultPasser cb ) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        simulateBackgroudWork();
                        cb.pass(in + " Dwarves");
                    }
                }).start();
            }
        };

        Scratch.Recipe<String> recipeFor7 =
                createRecipe(String.class)
                        .startWith(JUST_7)
                        .step(intToString)
                        .lastly(asyncAddWord);
        assertThat(recipeFor7.cook(), is("7 Dwarves"));
    }

    private void simulateBackgroudWork() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static class PassThrough<T> implements Scratch.Step<T,T> {
        @Override
        public void apply(T in, Scratch.StepResultPasser<T> result) {
            result.pass(in);
        }
    };
}
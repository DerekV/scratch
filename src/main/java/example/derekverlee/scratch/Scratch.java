package example.derekverlee.scratch;

import java.util.ArrayList;
import java.util.List;

public class Scratch {
    public static <Outcome> RecipeBuilderStarter<Outcome> createRecipe(Class<Outcome> clazz) {
        return new RecipeBuilderStarter<>();
    }


    public static interface Recipe<Outcome> {
        Outcome cook();

    }

    public static class RecipeBuilder<FinalT, OutT> {
        private final List<Step> steps = new ArrayList<>();

        @SuppressWarnings("unchecked")
        <NextT> RecipeBuilder<FinalT, NextT> step(Step<OutT, NextT> nextStep) {
            steps.add(nextStep);
            return (RecipeBuilder<FinalT, NextT>) this;
        }

        Recipe<FinalT> lastly(Step<OutT, FinalT> lastStep) {
            steps.add(lastStep);
            return new Recipe<FinalT>() {
                @Override
                public FinalT cook() {
                    Object current = null; // start with null, first step should expect it
                    for(Step step : steps) {
                        current = step.apply(current);
                    }
                    return (FinalT) current;
                }
            };
        }
    }

    public static interface Step<Input, Output> {
        Output apply(Input out);
    }

    public static class RecipeBuilderStarter<FinalOutcome> {
        <A> RecipeBuilder<FinalOutcome, A> startWith(Step<Void, A> firstStep) {
            return new RecipeBuilder<FinalOutcome, Void>().step(firstStep);
        }
    }

}

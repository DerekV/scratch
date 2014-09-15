package example.derekverlee.scratch;

public class Scratch {
    public static <Outcome> StartRecipe<Outcome> createRecipe(Class<Outcome> clazz) {
        return new StartRecipe<Outcome>() {
            @Override
            public <Next> PartialRecipe<Outcome, Next> startWith(Step<Void, Next> firstStep) {
                return new SimpleRecipe<Outcome, Next>(firstStep.apply(null));
            }
        };
    }

    public static interface Recipe<Outcome> {
        Outcome cook();
    }

    public static interface Step<Input,Output> {
        Output apply(Input out);
    }

    public static interface StartRecipe<FinalOutcome> {
        <Next> PartialRecipe<FinalOutcome,Next> startWith(Step<Void,Next> firstStep);
    }

    public static interface PartialRecipe<FinalOutcome,Current> {
        <Out> PartialRecipe<FinalOutcome, Out> step(Step<Current, Out> nextStep);
        Recipe<FinalOutcome> lastly(Step<Current,FinalOutcome> lastStep);
    }


    private static class SimpleRecipe<FinalOutcome, Current> implements PartialRecipe<FinalOutcome, Current> {
        public final Current value;

        SimpleRecipe(Current value) {
            this.value = value;
        }

        @Override
        public <Out> PartialRecipe<FinalOutcome, Out> step(Step<Current, Out> nextStep) {
            Out output = nextStep.apply(value);
            return new SimpleRecipe<>(output);
        }

        @Override
        public Recipe<FinalOutcome> lastly(final Step<Current, FinalOutcome> lastStep) {
            return new Recipe<FinalOutcome>() {
                @Override
                public FinalOutcome cook() {
                    return lastStep.apply(value);
                }
            };
        }
    }
}

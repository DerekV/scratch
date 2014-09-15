package example.derekverlee.scratch;

public class Scratch {
    public static <Outcome> Recipe<Outcome> createRecipe(Class<Outcome> clazz) {
        return new Recipe<Outcome>() {
            public Outcome out;

            @Override
            public Recipe<Outcome> step(Step<Outcome> nextStep) {
                out = nextStep.apply(out);
                return this;
            }

            @Override
            public Outcome cook() {
                return out;
            }
        };
    }

    public interface Ingredient<T> {
        T obtain();
    }

    public interface Recipe<Outcome> {
        Recipe<Outcome> step(Step<Outcome> nextStep);

        Outcome cook();
    }

    public static interface Step<T> {
        T apply(T out);
    }
}

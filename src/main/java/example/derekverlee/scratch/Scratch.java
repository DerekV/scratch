package example.derekverlee.scratch;

public class Scratch {
    public static Recipe createRecipe() {
        return new Recipe() {
            @Override
            public Recipe step(Step nextStep) {
                return this;
            }

            @Override
            public void cook() {

            }
        };
    }

    public interface Ingredient<T> {
        T obtain();
    }

    public interface Recipe {
        Recipe step(Step nextStep);

        void cook();
    }

    public static interface Step {
    }
}

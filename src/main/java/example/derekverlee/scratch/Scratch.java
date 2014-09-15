package example.derekverlee.scratch;

public class Scratch {
    public static <Outcome> Recipe<Outcome> createRecipe(Class<Outcome> clazz) {
        return new SimpleRecipe<>();
    }

    public interface Recipe<Outcome> {
        <In, Out> Recipe<Outcome> step(Step<In,Out> nextStep);

        Outcome cook();
    }

    public static interface Step<Input,Output> {
        Output apply(Input out);
    }

    private static class SimpleRecipe<Outcome> implements Recipe<Outcome> {
        public Object out;

        @Override
        public <In,Out> Recipe<Outcome> step(Step<In,Out> nextStep) {
            out = nextStep.apply((In)out);
            return this;
        }

        @Override
        public Outcome cook() {
            return (Outcome) out;
        }
    }
}

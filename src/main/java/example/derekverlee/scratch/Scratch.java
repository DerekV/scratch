package example.derekverlee.scratch;

public class Scratch {
    public static Cook cook() {
        return new Cook() {
            @Override
            public Cook step(Step nextStep) {
                return this;
            }

            @Override
            public void run() {

            }
        };
    }

    public interface Ingredient<T> {
        T obtain();
    }

    public interface Cook {
        Cook step(Step nextStep);

        void run();
    }

    public static interface Step {
    }
}

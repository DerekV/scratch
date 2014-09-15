package example.derekverlee.scratch;

public class Scratch {
    public static Cook cook() {
        return new Cook() {
        };
    }

    public interface Ingredient<T> {
        T obtain();
    }

    public interface Cook {
    }
}

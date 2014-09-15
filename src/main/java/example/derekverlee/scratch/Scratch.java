package example.derekverlee.scratch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Scratch {
    public static <Outcome> RecipeBuilderStarter<Outcome> createRecipe(Class<Outcome> clazz) {
        return new RecipeBuilderStarter<>();
    }


    public static interface Recipe<Outcome> {
        Outcome cook() throws InterruptedException;

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
                public FinalT cook() throws InterruptedException {
                    final List<FinalT> finalOutcomeBox = new ArrayList<>(1);
                    final Iterator<Step> stepIterator = steps.iterator();
                    final CountDownLatch latch = new CountDownLatch(1);
                    RecipeCallback<FinalT> finalCb = new RecipeCallback<FinalT>() {
                        @Override
                        public void onSuccess(FinalT outcome) {
                            finalOutcomeBox.add(outcome);
                            latch.countDown();
                        }
                    };
                    if(stepIterator.hasNext()) {
                        Step firstStep = stepIterator.next();
                        firstStep.apply(null, new StepResultRecurser(stepIterator, finalCb));
                    }

                    latch.await();
                    FinalT finalOutcome = finalOutcomeBox.get(0);
                    return finalOutcome;
                }

            };
        }
    }

    public static interface Step<Input, Output> {
        void apply(Input in, StepResultPasser<Output> passResult);
    }

    public static class RecipeBuilderStarter<FinalOutcome> {
        <A> RecipeBuilder<FinalOutcome, A> startWith(Step<Void, A> firstStep) {
            return new RecipeBuilder<FinalOutcome, Void>().step(firstStep);
        }
    }

    public interface StepResultPasser<ResultT> {
        void pass(ResultT in);
    }

    private static class StepResultRecurser implements StepResultPasser<Object> {

        private final Iterator<Step> stepIterator;
        private final RecipeCallback finalCb;

        public StepResultRecurser(Iterator<Step> stepIterator, RecipeCallback finalCb) {
            this.stepIterator = stepIterator;
            this.finalCb = finalCb;
        }

        @Override
        public void pass(Object in) {
            if(stepIterator.hasNext()) {
                Step step = stepIterator.next();
                step.apply(in, new StepResultRecurser(stepIterator, finalCb));
            } else {
                finalCb.onSuccess(in);
            }
        }
    }

    public static interface RecipeCallback<FinalT> {
        void onSuccess(FinalT outcome);
    }
}

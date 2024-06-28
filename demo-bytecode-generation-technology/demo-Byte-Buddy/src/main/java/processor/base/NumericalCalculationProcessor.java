package processor.base;

import processor.BaseProcessor;

public class NumericalCalculationProcessor extends BaseProcessor<Integer, Integer> {

    @Override
    public Integer process(Integer input, Object... data) throws Exception {
        return input + 1;
    }
}
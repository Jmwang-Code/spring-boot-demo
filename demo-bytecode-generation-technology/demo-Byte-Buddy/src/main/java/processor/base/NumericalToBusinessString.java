package processor.base;

import processor.BaseProcessor;

public class NumericalToBusinessString extends BaseProcessor<Integer, String>{

    @Override
    public String process(Integer input, Object... data) throws Exception {
        return "JMWANG"+input.toString();
    }
}

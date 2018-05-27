package com.silence.rootfeature.validation.validators;


import com.silence.rootfeature.validation.BaseValidator;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateListener;
import com.silence.rootfeature.validation.ValidateUtil;

/**
 * Created by violet on 16/3/1.
 */
public class PatternStringValidator extends BaseValidator<String> {
    private String pattern;

    public PatternStringValidator(TargetValueCreator<String> targetValueCreator) {
        super(targetValueCreator);
    }

    public PatternStringValidator(ValidateListener validateListener) {
        super(validateListener);
    }

    public PatternStringValidator(TargetValueCreator<String> targetValueCreator, ValidateListener validateListener) {
        super(targetValueCreator, validateListener);
    }

    @Override
    public boolean validation() {
        return ValidateUtil.isPattern(getTargetValue(), getPattern());
    }

    public String getPattern() {
        return pattern;
    }

    public PatternStringValidator setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }
}

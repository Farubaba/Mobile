package com.silence.rootfeature.validation.validators;

import com.silence.rootfeature.validation.BaseValidator;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateListener;
import com.silence.rootfeature.validation.ValidateUtil;

/**
 * Created by violet on 16/3/1.
 */
public class MaxLengthValidator extends BaseValidator<String> {
    private int maxLength = 20;

    public MaxLengthValidator(TargetValueCreator<String> targetValueCreator) {
        super(targetValueCreator);
    }

    public MaxLengthValidator(ValidateListener validateListener) {
        super(validateListener);
    }

    public MaxLengthValidator(TargetValueCreator<String> targetValueCreator, ValidateListener validateListener) {
        super(targetValueCreator, validateListener);
    }

    @Override
    public boolean validation() {
        return ValidateUtil.isValidMaxLength(getTargetValue(), getMaxLength());
    }

    public int getMaxLength() {
        return maxLength;
    }

    public MaxLengthValidator setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }
}

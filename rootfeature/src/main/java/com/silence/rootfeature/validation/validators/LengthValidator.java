package com.silence.rootfeature.validation.validators;


import com.silence.rootfeature.validation.BaseValidator;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateListener;
import com.silence.rootfeature.validation.ValidateUtil;

/**
 * Created by violet on 16/3/1.
 */
public class LengthValidator extends BaseValidator<String> {
    private int length = 20;

    public LengthValidator(TargetValueCreator<String> targetValueCreator) {
        super(targetValueCreator);
    }

    public LengthValidator(ValidateListener validateListener) {
        super(validateListener);
    }

    public LengthValidator(TargetValueCreator<String> targetValueCreator, ValidateListener validateListener) {
        super(targetValueCreator, validateListener);
    }

    @Override
    public boolean validation() {
        return ValidateUtil.isRightLength(getTargetValue(),getLength());
    }

    public int getLength() {
        return length;
    }

    public LengthValidator setLength(int length) {
        this.length = length;
        return this;
    }
}

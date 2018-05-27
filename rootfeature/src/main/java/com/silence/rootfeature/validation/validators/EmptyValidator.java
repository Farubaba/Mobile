package com.silence.rootfeature.validation.validators;

import com.silence.rootfeature.validation.BaseValidator;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateListener;
import com.silence.rootfeature.validation.ValidateUtil;

/**
 * Created by violet on 16/3/1.
 */
public class EmptyValidator extends BaseValidator<String> {
    public EmptyValidator(TargetValueCreator<String> targetValueCreator) {
        super(targetValueCreator);
    }

    public EmptyValidator(ValidateListener validateListener) {
        super(validateListener);
    }

    public EmptyValidator(TargetValueCreator<String> targetValueCreator, ValidateListener validateListener) {
        super(targetValueCreator, validateListener);
    }

    @Override
    public boolean validation() {
        return ValidateUtil.isNotEmpty(getTargetValue());
    }
}

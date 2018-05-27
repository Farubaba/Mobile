package com.silence.rootfeature.validation.validators;

import com.silence.rootfeature.validation.BaseValidator;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateListener;
import com.silence.rootfeature.validation.ValidateUtil;

/**
 * 校验邮箱格式是否合法
 * Created by violet on 16/2/24.
 */
public class EmailAddressValidator extends BaseValidator<String> {

    public EmailAddressValidator(TargetValueCreator<String> targetValueCreator) {
        super(targetValueCreator);
    }

    public EmailAddressValidator(ValidateListener validateListener) {
        super(validateListener);
    }

    public EmailAddressValidator(TargetValueCreator<String> targetValueCreator, ValidateListener validateListener) {
        super(targetValueCreator, validateListener);
    }

    @Override
    public boolean validation() {
        return ValidateUtil.isEmail(getTargetValue());
    }
}

package com.silence.rootfeature.validation.validators;


import com.silence.rootfeature.validation.BaseValidator;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateListener;
import com.silence.rootfeature.validation.ValidateUtil;

/**
 * 在此描述该类用途
 *
 * @author chenpu
 * @version V1.0
 * @date 2016-04-27 15:49
 * @tips 在此提出您对代码的建议
 */
public class ChineseLengthValidator extends BaseValidator<String> {
    public ChineseLengthValidator(TargetValueCreator<String> targetValueCreator) {
        super(targetValueCreator);
    }

    public ChineseLengthValidator(ValidateListener validateListener) {
        super(validateListener);
    }

    public ChineseLengthValidator(TargetValueCreator<String> targetValueCreator, ValidateListener validateListener) {
        super(targetValueCreator, validateListener);
    }

    @Override
    public boolean validation() {
        return ValidateUtil.minAddressChineseLength(getTargetValue());
    }
}

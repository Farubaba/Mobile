package com.silence.rootfeature.validation.validators;


import com.silence.rootfeature.validation.BaseValidator;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateListener;
import com.silence.rootfeature.validation.ValidateUtil;

/**
 * 中文姓名校验
 *
 * @author chenpu
 * @version V1.0
 * date :  2016-03-07 16:29
 * tips 在此提出您对代码的建议
 */
public class ChineseNameValidator extends BaseValidator<String> {

    public ChineseNameValidator(TargetValueCreator<String> targetValueCreator) {
        super(targetValueCreator);
    }

    public ChineseNameValidator(ValidateListener validateListener) {
        super(validateListener);
    }

    public ChineseNameValidator(TargetValueCreator<String> targetValueCreator, ValidateListener validateListener) {
        super(targetValueCreator, validateListener);
    }

    @Override
    public boolean validation() {
        return ValidateUtil.isChineseName(getTargetValue());
    }
}

package com.silence.rootfeature.validation.validators;


import com.silence.rootfeature.validation.BaseValidator;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateListener;
import com.silence.rootfeature.validation.ValidateUtil;

/**
 * 电话号码校验
 *
 * @author chenpu
 * @version V1.0
 * date :  2016-03-07 18:20
 * tips 在此提出您对代码的建议
 */
public class MobileValidator extends BaseValidator<String> {
    /**
     * 字符串中间是否可以带*号mask字符，默认不可以
     */
    private boolean mayWithStarsCenter = false;

    public MobileValidator(TargetValueCreator<String> targetValueCreator) {
        super(targetValueCreator);
    }

    public MobileValidator(ValidateListener validateListener) {
        super(validateListener);
    }

    public MobileValidator(TargetValueCreator<String> targetValueCreator, ValidateListener validateListener) {
        super(targetValueCreator, validateListener);
    }

    public boolean isMayWithStarsCenter() {
        return mayWithStarsCenter;
    }

    public void setMayWithStarsCenter(boolean mayWithStarsCenter) {
        this.mayWithStarsCenter = mayWithStarsCenter;
    }

    @Override
    public boolean validation() {
        if(isMayWithStarsCenter()){
            return ValidateUtil.isPhoneNumberMayWithStarCenter(getTargetValue());
        }else{
            return ValidateUtil.isPhoneNumber(getTargetValue());
        }
    }
}

package com.silence.rootfeature.validation.validators;


import com.silence.rootfeature.validation.BaseValidator;
import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateListener;
import com.silence.rootfeature.validation.ValidateUtil;

/**
 * 银行卡号校验
 *
 * @author chenpu
 * @version V1.0
 * @date 2016-03-08 10:52
 * @tips 在此提出您对代码的建议
 */
public class BankCardValidator extends BaseValidator<String> {
    /**
     * 字符串中间是否可以带*号mask字符，默认不可以
     */
    private boolean mayWithStarsCenter = false;
    public BankCardValidator(TargetValueCreator<String> targetValueCreator) {
        super(targetValueCreator);
    }

    public BankCardValidator(ValidateListener validateListener) {
        super(validateListener);
    }

    public BankCardValidator(TargetValueCreator<String> targetValueCreator, ValidateListener validateListener) {
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
            return ValidateUtil.isBankCardMayWithStarsCenter(getTargetValue());
        }else{
            return ValidateUtil.isBankCard(getTargetValue());
        }
    }
}

package com.silence.rootfeature.validation;


import com.silence.rootfeature.logger.LogManager;

import java.util.LinkedList;
import java.util.List;

/**
 * 条件验证管理器，用于链式验证所有的条件。
 *
 * Created by violet on 16/2/24.
 */
public class ValidateManager {
    public static final String TAG = ValidateManager.class.getSimpleName();
    private List<Validator> validators = new LinkedList<Validator>();
    /**
     * 整个manager完全执行完成之后的回调对象，<br>
     * 如果成功则回调{@link ActionCallback#onSuccess()};<br>
     * 如果失败则回调{@link ActionCallback#onFailed()};<br>
     */
    private ActionCallback actionCallback;
    private boolean ignoreFinalValidateListener = false;
    private boolean ignoreFinalPassCallback = false;
    private boolean ignoreFinalFailedCallback = false;

    public ValidateManager(){

    }

    public static ValidateManager newInstance(){
        return new ValidateManager();
    }

    /**
     * 添加一个Validator到已有的Validator List中。按添加顺序执行。
     * @param validator
     * @return
     */
    public ValidateManager addOrderedValidator(Validator validator){
        validators.add(validator);
        return this;
    }

    public ValidateManager removeValidator(Validator validator){
        validators.remove(validator);
        return this;
    }

    /**
     * 控制在某个条件验证失败后，是否应该终止整个条件验证。
     * @param interrupt
     * @return
     */
    public boolean validate(boolean interrupt){
        LogManager.getInstance().dt(TAG,"validate interrupt = "+ interrupt);
        boolean validateResult = true;
        for(Validator validator : validators){
            validator.setStopOnValidateFailed(interrupt);
            boolean validatePass = validator.validate();
            LogManager.getInstance().dt(TAG,"validator.validate() return = "+ validatePass);
            if(validatePass){
                continue;
            }else{
                validateResult = validatePass;
                if(interrupt){
                    break;
                }else{
                    continue;
                }
            }
        }
        callFinalValidateListener(validateResult);
        return validateResult;
    }

    /**
     * 决定是否调用validate流程结束之后的最终回调。
     * @param validateResult
     */
    private void callFinalValidateListener(boolean validateResult) {
        if(!isIgnoreFinalValidateListener()){
            if(getActionCallback() != null){
                if(validateResult){
                    if(isIgnoreFinalPassCallback()){
                        LogManager.getInstance().dt(TAG,"isIgnoreFinalPassCallback = true");
                    }else{
                        LogManager.getInstance().dt(TAG,"isIgnoreFinalPassCallback = false, execute getFinalValidateListener().onPass()");
                        getActionCallback().onSuccess();
                    }
                }else{
                    if(isIgnoreFinalFailedCallback()){
                        LogManager.getInstance().dt(TAG,"isIgnoreFinalFailedCallback = true");
                    }else{
                        LogManager.getInstance().dt(TAG, "isIgnoreFinalFailedCallback = false, execute getFinalValidateListener().onFailed()");
                        getActionCallback().onFailed();
                    }
                }
            }
        }else{
            LogManager.getInstance().dt(TAG,"isIgnoreFinalValidateListener = true, end.");
        }
    }

    /**
     * 一次性验证所有validator，最终返回是否全部通过PASS,即使中途有未通过的validator，也不终止，继续验证。
     * @return
     */
    public boolean validateAll(){
        return validate(false);
    }

    /**
     * 逐一验证validator，遇到错误就结束验证并返回false;只有全部通过才返回true;
     * @return
     */
    public boolean validate(){
        return validate(true);
    }

    public ValidateManager clear(){
        validators.clear();
        return this;
    }

    public List<Validator> getValidators() {
        return validators;
    }

    public ValidateManager setValidators(List<Validator> validators) {
        this.validators = validators;
        return this;
    }

    public ActionCallback getActionCallback() {
        return actionCallback;
    }

    public ValidateManager setActionCallback(ActionCallback actionCallback) {
        this.actionCallback = actionCallback;
        return this;
    }

    public boolean isIgnoreFinalValidateListener() {
        return ignoreFinalValidateListener;
    }

    public ValidateManager setIgnoreFinalValidateListener(boolean ignoreFinalValidateListener) {
        this.ignoreFinalValidateListener = ignoreFinalValidateListener;
        return this;
    }

    public boolean isIgnoreFinalPassCallback() {
        return ignoreFinalPassCallback;
    }

    public void setIgnoreFinalPassCallback(boolean ignoreFinalPassCallback) {
        this.ignoreFinalPassCallback = ignoreFinalPassCallback;
    }

    public boolean isIgnoreFinalFailedCallback() {
        return ignoreFinalFailedCallback;
    }

    public void setIgnoreFinalFailedCallback(boolean ignoreFinalFailedCallback) {
        this.ignoreFinalFailedCallback = ignoreFinalFailedCallback;
    }
}

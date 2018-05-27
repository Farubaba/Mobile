package com.silence.rootfeature.validation;

import android.text.TextUtils;

import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.IDCardUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供基础的验证逻辑
 * Created by violet on 16/2/24.
 */
public class ValidateUtil {
    public static final String TAG = "ValidateUtil";
    public static final String EMPTY = "";
    public static final String REG_EMAIL = "^([a-z0-9A-Z]+([_|\\.\\-]*))+([a-z0-9A-Z_-])*@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)*\\.)+[a-zA-Z]{2,}$";
    public static final String REG_NUMBER = "[0-9]*";
    public static final String REG_NUMBER_WITH_STARS_CENTER = "^[0-9]*[\\*]*[0-9]*$";
    public static final String REG_PHONE = "^1[3|4|5|7|8]\\d{9}";
    public static final String REG_PHONE_WITH_STARS_CENTER = "^1[3|4|5|7|8][\\*]*[0-9]*$";

    /**
     * 判断是否value不为空（null或空字符串）
     *
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(value.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串长度是否符合要求，最短minLength位
     *
     * @param value
     * @param minLength
     * @return
     */
    public static boolean isValidMinLength(String value, int minLength) {
        if (isNotEmpty(value)) {
            if (value.length() >= minLength) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串长度是否符合要求，最长maxLength位
     *
     * @param value
     * @param maxLength
     * @return
     */
    public static boolean isValidMaxLength(String value, int maxLength) {
        if (isNotEmpty(value)) {
            if (value.length() <= maxLength) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串长度是否正好为length
     *
     * @param value
     * @param length
     * @return
     */
    public static boolean isRightLength(String value, int length) {
        if (isNotEmpty(value)) {
            if (value.length() == length) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断给定的字符串是否符合，regex定义的规则
     *
     * @param value
     * @param regex
     * @return
     */
    public static boolean isPattern(String value, String regex) {
        if (isNotEmpty(value)) {
            if (isNotEmpty(regex)) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(value);
                boolean result = matcher.matches();
                return result;
            } else {
                LogManager.getInstance().dt(TAG, "pattern regex is null");
            }
        }
        return false;
    }

    /**
     * 判断给定的字符串是否符合邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return isPattern(email, REG_EMAIL);
    }

    /**
     * 电话号码中间可能出现*号
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberMayWithStarCenter(String phoneNumber){
        if (isPattern(phoneNumber, REG_PHONE_WITH_STARS_CENTER)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        if (isPattern(phoneNumber, REG_PHONE)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isIdCard15(String idCard15) {
        return true;
    }

    public static boolean isIdCard18(String idCard18) {
        return true;
    }

    public static boolean isIdCard(String idCard) {
        return IDCardUtil.IDCardValidate(idCard);
    }

    public static boolean isChineseName(String name) {
        if (name.length() < 2 || name.length() > 10) {
            return false;
        } else {
            Boolean isChineseName = true;
            //            String a="[\u4e00-\u9fa5]{1,2}(·){0,1}[\u4e00-\u9fa5]{1,3}";
            String chinese = "[\u4e00-\u9fa5]{2,10}";
            if (isNotEmpty(name) && isPattern(name, chinese)) {
                isChineseName = true;
            } else {
                isChineseName = false;
            }
            return isChineseName;
        }
    }

    private static boolean isSupportNumbersOfBankCard(String bankNo){
        return bankNo.length() == 16 || bankNo.length() == 17 || bankNo.length() == 19;
    }

    /**
     * 判断银行卡是否只有数字 和 * 两种字符，并且满足银行卡位数
     * @param bankNo
     * @return
     */
    public static boolean isBankCardMayWithStarsCenter(String bankNo){
        if((isPattern(bankNo, REG_NUMBER_WITH_STARS_CENTER) || isPattern(bankNo, REG_NUMBER))
                && isSupportNumbersOfBankCard(bankNo)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isBankCard(String bankNo) {
        if (isPattern(bankNo, REG_NUMBER) && isSupportNumbersOfBankCard(bankNo)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean minAddressChineseLength(String name) {

        int i = 0;
        //            String a="[\u4e00-\u9fa5]{1,2}(·){0,1}[\u4e00-\u9fa5]{1,3}";
        String chinese = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(chinese);
        Matcher m;
        for (int count = 0; count < name.length(); count++) {
            char temp = name.charAt(count);
            m = p.matcher(String.valueOf(temp));
            if (m.find()) {
                i++;
            }
        }
        if (i < 4) {
            return false;
        } else {
            return true;
        }

    }

}

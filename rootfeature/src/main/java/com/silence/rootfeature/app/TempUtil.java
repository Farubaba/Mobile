package com.silence.rootfeature.app;

import android.widget.EditText;

/**
 * 临时工具类,添加了各种各样的工具方法，以后考虑抽取到对应的工具类中
 *
 * @author violet
 * date :  2018/3/5 20:17
 */

public class TempUtil {
    public static void setSelectionEnd(EditText editText) {
        if (editText != null) {
            try {
                editText.setSelection(editText.getText().length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

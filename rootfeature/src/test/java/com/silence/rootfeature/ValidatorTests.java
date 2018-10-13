package com.silence.rootfeature;

import android.text.TextUtils;
import android.widget.EditText;

import com.silence.rootfeature.validation.TargetValueCreator;
import com.silence.rootfeature.validation.ValidateUtil;
import com.silence.rootfeature.validation.validators.EmptyValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
/**
 * @author violet
 * date :  2018/3/11 15:41
 */

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTests {

    @Mock
    TextUtils textUtils;
//    @Mock
//    EditText editText;
//    @Test
//    public  void testEmptyValidator(){
//        EmptyValidator emptyValidator = new EmptyValidator(new TargetValueCreator<String>() {
//            @Override
//            public String createTargetValue() {
//                return "not empty";
//            }
//        });
//
//        assertTrue(emptyValidator.validation());
//
//    }

    @Test
    public void testValidatorUtil(){
    }
}

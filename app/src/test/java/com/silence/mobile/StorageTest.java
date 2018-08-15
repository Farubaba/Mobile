package com.silence.mobile;

import android.os.Environment;

import com.silence.rootfeature.app.AppUtil;
import com.silence.rootfeature.logger.LogManager;

import org.junit.Test;

import java.io.File;

/**
 * @author violet
 * @date 2018/6/29 12:00
 */

public class StorageTest {
    @Test
    public void externalStoragePath(){

        File[] dirs = AppUtil.getAppContext().getExternalFilesDirs(Environment.DIRECTORY_PICTURES);
        for(File dir : dirs){
            String state = Environment.getExternalStorageState(dir);
            LogManager.getInstance().d(dir.getAbsolutePath() + " state = " + state);
        }

    }
}

package com.silence.rootfeature;

import android.os.Environment;

import com.silence.rootfeature.utils.StorageUtilTest;

import org.junit.Test;

/**
 * @author violet
 * date :  2018/6/29 18:03
 */

public class StorageTest {

    @Test
    public void storagePath(){
        StorageUtilTest.getInternalFilesDir();
        StorageUtilTest.getInternalCacheDir();
        StorageUtilTest.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        StorageUtilTest.getExternalStoragePublicDir(Environment.DIRECTORY_PICTURES);
    }

    @Test
    public void testmath(){
        double d = 2225234/23425234234L;

        String name = "zhangsanfeng";

    }
}

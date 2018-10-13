package com.silence.rootfeature.file;

import com.silence.rootfeature.utils.Encrypter;

/**
 * @author violet
 * date :  2018/7/1 16:46
 */

public class MD5CheckFileInfo extends FileInfo {

    @Override
    public boolean checkComplete() {
        if(targetFile != null && fileMessagedigest != null){
            return fileMessagedigest.equals(Encrypter.getMD5(getTargetFile()));
        }
        return false;
    }
}

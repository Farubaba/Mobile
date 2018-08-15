package com.silence.rootfeature.file;

/**
 * @author violet
 * @date 2018/7/1 16:46
 */

public class LengthCheckFileInfo extends FileInfo {

    @Override
    public boolean checkComplete() {
        return getTotalFileLength() == getWriteFileLength();
    }
}

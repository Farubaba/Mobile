package com.silence.rootfeature.file;

import java.io.File;

/**
 * 文件写入状态类
 */
public abstract class FileInfo {

    protected File targetFile;
    protected long totalFileLength;
    protected long writeFileLength;
    protected String fileMessagedigest;

    public File getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    public long getTotalFileLength() {
        return totalFileLength;
    }

    public void setTotalFileLength(long totalFileLength) {
        this.totalFileLength = totalFileLength;
    }

    public long getWriteFileLength() {
        return writeFileLength;
    }

    public void setWriteFileLength(long writeFileLength) {
        this.writeFileLength = writeFileLength;
    }

    public double getProgress() {
        double progress = 0;
        if(totalFileLength > 0){
            progress =  getWriteFileLength() / getTotalFileLength();
        }

        return progress;
    }

    public String getFileMessagedigest() {
        return fileMessagedigest;
    }

    public void setFileMessagedigest(String fileMessagedigest) {
        this.fileMessagedigest = fileMessagedigest;
    }

    /**
     * 验证最后写入的文件完整性
     * @return
     */
    public abstract boolean checkComplete();
}
package com.lijiazhen.springboot.vo;

public class HdfsFile {

    private String name;
    private boolean isDir;
    private long len;

    public HdfsFile() {
    }
    public HdfsFile(String name, boolean idDir, long len) {
        this.name = name;
        this.isDir = idDir;
        this.len = len;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        this.isDir = dir;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }
}

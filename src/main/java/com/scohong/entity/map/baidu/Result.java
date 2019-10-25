package com.scohong.entity.map.baidu;

/**
 * @Author: scohong
 * @Date: 2019/10/11 17:00
 * @Description:
 */
public class Result {
    private AddressDetail location;
    private int precise;
    private int confidence;
    private int comprehension;
    private String level;

    public AddressDetail getLocation() {
        return location;
    }

    public void setLocation(AddressDetail location) {
        this.location = location;
    }

    public int getPrecise() {
        return precise;
    }

    public void setPrecise(int precise) {
        this.precise = precise;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public int getComprehension() {
        return comprehension;
    }

    public void setComprehension(int comprehension) {
        this.comprehension = comprehension;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

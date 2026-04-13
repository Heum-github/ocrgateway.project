package com.cjh.domain.ocr.shared.vo;

public class RegionBox {
    private int left;
    private int top;
    private int width;
    private int height;

    private double resolutionRate = 1;

    public RegionBox(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    public RegionBox(int left, int top, int width, int height, double resolutionRate) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.resolutionRate = resolutionRate;
    }

    public void setResolutionRate(double resolutionRate) {
        this.resolutionRate = resolutionRate;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNewLeft() {
        return (int)(left * resolutionRate);
    }

    public int getNewTop() {
        return (int)(top * resolutionRate);
    }

    public int getNewWidth() {
        return (int)(width * resolutionRate);
    }

    public int getNewHeight() {
        return (int)(height * resolutionRate);
    }
}

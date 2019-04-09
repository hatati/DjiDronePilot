package com.example.nermi.djidronepilot;

import android.app.Activity;

import java.io.IOException;

public class CustomCNN extends ImageClassifier {

    /**
     * An array to hold inference results, to be feed into Tensorflow Lite as outputs. This isn't part
     * of the super class, because we need a primitive array here.
     */
    private float[][] labelProbArray = null;

    private String modelPath = "";
    private String labelPath = "";
    private int imageSizeX;
    private int imageSizeY;

    /**
     * Initializes an {@code CustomCNN}.
     *

     * @param activity
     */
    CustomCNN(Activity activity) throws IOException {
        super(activity);
        labelProbArray = new float[1][getNumLabels()];
    }

    CustomCNN(Activity activity, String modelPath, String labelPath, int imageSizeX, int imageSizeY) throws IOException{
        super(activity);
        labelProbArray = new float[1][getNumLabels()];

        this.modelPath = modelPath;
        this.labelPath = labelPath;
        this.imageSizeX = imageSizeX;
        this.imageSizeY = imageSizeY;
    }

    @Override
    protected String getModelPath() {
        if(modelPath != null)
            return modelPath;

        return "landing_stripe-CNN-RGB.tflite";
    }

    @Override
    protected String getLabelPath() {
        if(labelPath != null)
            return labelPath;

        return "labels_landing_stripe.txt";
    }

    @Override
    protected int getImageSizeX() {
        if(imageSizeX != 0)
            return imageSizeX;

        return 80;
    }

    @Override
    protected int getImageSizeY() {
        if(imageSizeY != 0)
            return imageSizeY;

        return 80;
    }

    @Override
    protected int getNumBytesPerChannel() {
        return 4;
    }

    @Override
    protected void addPixelValue(int pixelValue) {
        imgData.putFloat(((pixelValue >> 16) & 0xFF) / 255.f);
        imgData.putFloat(((pixelValue >> 8) & 0xFF) / 255.f);
        imgData.putFloat((pixelValue & 0xFF) / 255.f);
    }

    @Override
    protected float getProbability(int labelIndex) {
        return labelProbArray[0][labelIndex];
    }

    @Override
    protected void setProbability(int labelIndex, Number value) {
        labelProbArray[0][labelIndex] = value.floatValue();
    }

    @Override
    protected float getNormalizedProbability(int labelIndex) {
        return labelProbArray[0][labelIndex];
    }

    @Override
    protected void runInference() {
        tflite.run(imgData, labelProbArray);
    }

    public void setLabelPath(String labelPath) {
        this.labelPath = labelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public void setImageSizeX(int imageSizeX) {
        this.imageSizeX = imageSizeX;
    }

    public void setImageSizeY(int imageSizeY) {
        this.imageSizeY = imageSizeY;
    }
}

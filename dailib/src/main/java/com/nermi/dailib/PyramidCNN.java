package com.nermi.dailib;

import android.app.Activity;

import java.io.IOException;

public class PyramidCNN extends ImageClassifier {

    /**
     * An array to hold inference results, to be feed into Tensorflow Lite as outputs. This isn't part
     * of the super class, because we need a primitive array here.
     */
    private float[][] labelProbArray = null;

    /**
     * Initializes an {@code PyramidCNN}.
     *

     * @param activity
     */
    PyramidCNN(Activity activity) throws IOException {
        super(activity);
        labelProbArray = new float[1][getNumLabels()];
    }

    @Override
    protected String getModelPath() {
        return "pyramid_CNN_RGB.tflite";
    }

    @Override
    protected String getLabelPath() {
        return "pyramid_labels.txt";
    }

    @Override
    protected int getImageSizeX() {
        return 100;
    }

    @Override
    protected int getImageSizeY() {
        return 100;
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
}

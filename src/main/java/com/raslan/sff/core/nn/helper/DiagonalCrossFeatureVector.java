package com.raslan.sff.core.nn.helper;

import java.util.List;

import com.raslan.sff.core.data.CharacterMapper;
import com.raslan.sff.core.data.InputImage;

public class DiagonalCrossFeatureVector extends AbstractFeatureVector {

    private static final int ZONE_SIZE = 5;

    private InputImage inputImage;

    public DiagonalCrossFeatureVector(int inputSize, int outputSize) {
        super(inputSize, outputSize);
    }

    public DiagonalCrossFeatureVector(int inputSize, double[] output) {
        super(inputSize, output);
    }

    public DiagonalCrossFeatureVector(double[] input, double[] output) {
        super(input, output);
    }

    public DiagonalCrossFeatureVector(List<Double> input, List<Double> output) {
        super(input, output);
    }

    public DiagonalCrossFeatureVector(InputImage inputImage, CharacterMapper mapper) {
        super(65, inputImage.getBinarySymbol(mapper));
        this.inputImage = inputImage;
        calculateVectors();
    }

    private void calculateVectors() {
        int[][] image = inputImage.getImage().getMatrix();
        int width = inputImage.getImage().getWidth();
        int height = inputImage.getImage().getHeight();
        int counter = 0;
        double[] vector = getInputs();
        for (int i = 0; i < height; i += ZONE_SIZE) {
            for (int j = 0; j < width; j += ZONE_SIZE) {
                vector[counter++] = calculateZone(getZone(i, j, image));
            }
        }

        int hCount = width / ZONE_SIZE;
        int vCount = height / ZONE_SIZE;
        double[] hMean = new double[hCount];
        for (int i = 0; i < hCount; i++) {
            double hSum = 0;
            for (int j = 0; j < vCount; j++) {
                hSum += vector[i * hCount + j];
            }
            hMean[i] = hSum / hCount;
        }

        double[] vMean = new double[vCount];
        for (int i = 0; i < vCount; i++) {
            double vSum = 0;
            for (int j = 0; j < hCount; j++) {
                vSum += vector[j * hCount + i];
            }
            vMean[i] = vSum / hCount;
        }

        int offset = hCount * vCount;
        for (int i = 0; i < hCount; i++) {
            vector[offset + i] = hMean[i];
        }
        offset += hCount;
        for (int i = 0; i < vCount; i++) {
            vector[offset + i] = vMean[i];
        }

        int size = 15;
        double[] crossVector = new double[size];
        int step = height / size;
        for (int i = 0; i < size; i++) {
            int firstIndex = i * step;
            for (int j = 0; j < width; j++) {
                if (image[firstIndex][j] == 1) {
                    crossVector[i] += 1 / (double) width;
                }
            }
        }

        offset = hCount * vCount + hCount + vCount;
        for (int i = 0; i < size; i++) {
            vector[offset + i] = crossVector[i];
        }

        offset += size;
        Tuple points = countPoints(image, width, height);
        vector[offset] = 1 / (1 + (double) points.endPoints);
        vector[offset + 1] = 1 / (1 + (double) points.crossPoints);
    }

    private int[][] getZone(int iOffset, int jOffset, int[][] input) {
        int[][] zone = new int[ZONE_SIZE][ZONE_SIZE];
        for (int i = 0; i < ZONE_SIZE; i++) {
            for (int j = 0; j < ZONE_SIZE; j++) {
                zone[i][j] = input[iOffset + i][jOffset + j];
            }
        }
        return zone;
    }

    private double calculateZone(int[][] zone) {
        int count = 2 * ZONE_SIZE - 1;
        double sum = 0;
        for (int i = 0; i < count; i++) {
            int z = i < ZONE_SIZE ? 0 : i - ZONE_SIZE + 1;
            int sliceSize = i - z + 1;
            int sliceSum = 0;
            for (int j = z; j < sliceSize; j++) {
                if (zone[j][i - j] == 1) {
                    sliceSum++;
                }
            }
            sum += sliceSum / (double) sliceSize;
        }
        return sum / count;
    }

    private Tuple countPoints(int[][] image, int width, int height) {
        int endPoints = 0;
        int crossPoints = 0;
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (image[i][j] == 0) {
                    continue;
                }

                int p2 = image[i - 1][j];
                int p3 = image[i - 1][j + 1];
                int p4 = image[i][j + 1];
                int p5 = image[i + 1][j + 1];
                int p6 = image[i + 1][j];
                int p7 = image[i + 1][j - 1];
                int p8 = image[i][j - 1];
                int p9 = image[i - 1][j - 1];

                int diagonalSum = p3 + p5 + p7 + p9;
                int crossSum = p2 + p4 + p6 + p8;
                int sum = crossSum + diagonalSum;
                if (sum == 0 || sum == 1) {
                    endPoints++;
                } else if (crossSum > 2 || diagonalSum > 2) {
                    crossPoints++;
                } else if (p2 + p4 > 1 || p4 + p6 > 1 || p6 + p8 > 1 || p8 + p2 > 1 ||
                        p3 + p5 > 1 || p5 + p7 > 1 || p7 + p9 > 1 || p9 + p3 > 1) {
                    crossPoints++;
                }
            }
        }
        return new Tuple(endPoints, crossPoints);
    }

    private class Tuple {
        public int endPoints;
        public int crossPoints;

        public Tuple(int endPoints, int crossPoints) {
            this.endPoints = endPoints;
            this.crossPoints = crossPoints;
        }
    }
}

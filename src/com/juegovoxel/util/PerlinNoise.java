package com.juegovoxel.util;

public class PerlinNoise {

    public static double noise(double x, double y) {
        int n = (int)x + (int)y * 57;
        n = (n << 13) ^ n;
        int nn = (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;
        return 1.0 - ((double)nn / 1073741824.0);
    }

    public static double smoothNoise(double x, double y) {
        double corners = (noise(x - 1, y - 1) + noise(x + 1, y - 1) + noise(x - 1, y + 1) + noise(x + 1, y + 1)) / 16;
        double sides = (noise(x - 1, y) + noise(x + 1, y) + noise(x, y - 1) + noise(x, y + 1)) / 8;
        double center = noise(x, y) / 4;
        return corners + sides + center;
    }

    public static double interpolate(double a, double b, double x) {
        double ft = x * Math.PI;
        double f = (1 - Math.cos(ft)) * 0.5;
        return a * (1 - f) + b * f;
    }

    public static double interpolatedNoise(double x, double y) {
        int integerX = (int)x;
        double fractionalX = x - integerX;

        int integerY = (int)y;
        double fractionalY = y - integerY;

        double v1 = smoothNoise(integerX, integerY);
        double v2 = smoothNoise(integerX + 1, integerY);
        double v3 = smoothNoise(integerX, integerY + 1);
        double v4 = smoothNoise(integerX + 1, integerY + 1);

        double i1 = interpolate(v1, v2, fractionalX);
        double i2 = interpolate(v3, v4, fractionalX);

        return interpolate(i1, i2, fractionalY);
    }

    public static double perlinNoise(double x, double y) {
        double total = 0;
        int n = 4; // Número de octavas
        double persistence = 0.5;

        for (int i = 0; i < n; i++) {
            double frequency = Math.pow(2, i);
            double amplitude = Math.pow(persistence, i);

            total += interpolatedNoise(x * frequency, y * frequency) * amplitude;
        }

        return total;
    }
}

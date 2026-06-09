package net.awyvrix.threadWeave.content.general.custom;

import net.minecraft.core.BlockPos;

public class Calculations {
    public static double[][] multiply(double[][] a, double[][] b) {
        int n = a.length;
        double[][] r = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += a[i][k] * b[k][j];
                }
                r[i][j] = sum;
            }
        }

        return r;
    }

    public static double heavyProcessor(BlockPos pos) {
        double acc = 0;
        int size = 200;

        for (int i = 0; i < size; i++) {
            for (int j = 1; j < 200; j++) {
                acc += Math.sin(i * 0.5) * Math.cos(j * 0.25);
                acc += Math.sqrt(i * j + 1.234);
            }
        }

        long seed = pos.asLong();
        for (int i = 0; i < 5000; i++) {
            seed ^= (seed << 7);
            seed ^= (seed >> 9);
            seed ^= (seed << 8);
            acc += seed * 0.0000001;
        }

        double[][] m = new double[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                m[i][j] = (i + 1) * (j + 1) * 0.001;
            }
        }

        for (int k = 0; k < 10; k++) {
            multiply(m, m);
        }
        return acc;
    }
}

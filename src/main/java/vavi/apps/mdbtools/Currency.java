/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 */

package vavi.apps.mdbtools;


import java.util.Arrays;


/**
 * these routines are copied from the freetds project which does something very
 * similar
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 050408 nsano ported from mdbtool <br>
 */
class Currency {

    /** */
    private static final int MAX_PRECISION = 20;

    /** */
    public static String moneyToString(byte[] buffer, int start) {
        final int numberOfBytes = 8;
        byte[] b = new byte[numberOfBytes];
        System.arraycopy(buffer, start, b, 0, numberOfBytes);

        int[] multiplier = new int[MAX_PRECISION];
        int[] temp = new int[MAX_PRECISION];
        int[] product = new int[MAX_PRECISION];

        int negative = 0;

        multiplier[0] = 1;

        if ((b[7] & 0x01) != 0) {
            // negative number -- preform two's complement
            negative = 1;
            for (int i = 0; i < numberOfBytes; i++) {
                b[i] = (byte) ~b[i];
            }
            for (int i = 0; i < numberOfBytes; i++) {
                b[i] = (byte) (b[i] + 1);
                if (b[i] != 0) {
                    break;
                }
            }
        }

        b[7] = 0;
        for (int pos = 0; pos < numberOfBytes; pos++) {
            multiplyByte(product, b[pos] & 0xff, multiplier);

            System.arraycopy(multiplier, 0, temp, 0, MAX_PRECISION);
            Arrays.fill(multiplier, 0);
            multiplyByte(multiplier, 256, temp);
        }
        return ((negative != 0) ? "-" : "") + arrayToString(product, 4);
    }

    private static void multiplyByte(int[] product, int num, int[] multiplier) {
        int[] number = new int[] {
                num % 10,
                (num / 10) % 10,
                (num / 100) % 10
        };

        int top = MAX_PRECISION - 1;
        while (top >= 0 && multiplier[top] == 0)
            top--;
        int start = 0;
        for (int i = 0; i <= top; i++) {
            for (int j = 0; j < 3; j++) {
                product[j + start] += multiplier[i] * number[j];
            }
            doCarry(product);
            start++;
        }
    }

    private static void doCarry(int[] product) {
        for (int j = 0; j < MAX_PRECISION; j++) {
            if (product[j] > 9) {
                product[j + 1] += product[j] / 10;
                product[j] = product[j] % 10;
            }
        }
    }

    private static String arrayToString(int[] array, int scale) {
        int top = MAX_PRECISION - 1;
        while (top >= 0 && top > scale && array[top] == 0)
            top--;

        if (top == -1) {
            return "0";
        }

        int j = 0;
        char[] s = new char[100];
        // TODO find a better number
        for (int i = top; i >= 0; i--) {
            if (top + 1 - j == scale)
                s[j++] = '.';
            s[j++] = (char) (array[i] + '0');
        }
        return new String(s, 0, j);
    }
}

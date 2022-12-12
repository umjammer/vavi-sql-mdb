/*
 * MDB Tools - A library for reading MS Access database files
 *
 * Copyright (C) 2000 Brian Bruns.
 */

package vavi.apps.mdbtools;


/**
 * these routines are copied from the freetds project which does something very
 * similar
 *
 * @author Brian Bruns
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (nsano)
 * @version 0.00 050408 nsano ported from mdbtool <br>
 */
public class Currency {

    /** */
    private static final int MAXPRECISION = 20;

    /** */
    public static String mdb_money_to_string(MdbFile mdb, int start) {
        final int numberOfBytes = 8;
        byte[] b = new byte[numberOfBytes];
        System.arraycopy(mdb.getPageBuffer(), start, b, 0, numberOfBytes);

        String s;
        int[] multiplier = new int[MAXPRECISION];
        int[] temp = new int[MAXPRECISION];
        int[] product = new int[MAXPRECISION];

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
            multiply_byte(product, b[pos] & 0xff, multiplier);

            System.arraycopy(multiplier, 0, temp, 0, MAXPRECISION);
            for (int i = 0; i < MAXPRECISION; i++) {
                multiplier[i] = 0;
            }
            multiply_byte(multiplier, 256, temp);
        }
        if (negative != 0) {
            s = "-" + array_to_string(product, 4);
        } else {
            s = array_to_string(product, 4);
        }
        return s;
    }

    private static int multiply_byte(int[] product, int num, int[] multiplier) {
        int[] number = new int[3];
        int i, top, j, start;

        number[0] = num % 10;
        number[1] = (num / 10) % 10;
        number[2] = (num / 100) % 10;

        for (top = MAXPRECISION - 1; top >= 0 && multiplier[top] == 0; top--)
            ;
        start = 0;
        for (i = 0; i <= top; i++) {
            for (j = 0; j < 3; j++) {
                product[j + start] += multiplier[i] * number[j];
            }
            do_carry(product);
            start++;
        }
        return 0;
    }

    private static int do_carry(int[] product) {
        int j;

        for (j = 0; j < MAXPRECISION; j++) {
            if (product[j] > 9) {
                product[j + 1] += product[j] / 10;
                product[j] = product[j] % 10;
            }
        }
        return 0;
    }

    static String array_to_string(int[] array, int scale) {
        int top, i, j;

        for (top = MAXPRECISION - 1; top >= 0 && top > scale && array[top] == 0; top--)
            ;

        if (top == -1) {
            return "0";
        }

        j = 0;
        char[] s = new char[100];
        // TODO find a better number
        for (i = top; i >= 0; i--) {
            if (top + 1 - j == scale)
                s[j++] = '.';
            s[j++] = (char) (array[i] + '0');
        }
        return new String(s, 0, j);
    }
}

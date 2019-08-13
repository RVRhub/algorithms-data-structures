package rvr.algo;


import java.io.BufferedOutputStream;
import java.io.IOException;

public final class BinaryStdOutExtended {

    private static BufferedOutputStream out;
    private static boolean isInitialized = false;
    private static int n;
    private static int buffer;

    private BinaryStdOutExtended() { }

    private static void initialize() {
        out = new BufferedOutputStream(System.out);
        buffer = 0;
        n = 0;
        isInitialized = true;
    }

    public static void write(int x) {
        writeByte((x >>> 24) & 0xff);
        writeByte((x >>> 16) & 0xff);
        writeByte((x >>>  8) & 0xff);
        writeByte((x >>>  0) & 0xff);
    }

    public static void write(boolean x) {
        writeBit(x);
    }

    public static void write(char x, int r) {
        if (r == 8) {
            write(x);
            return;
        }
        if (r < 1 || r > 16) throw new IllegalArgumentException("Illegal value for r = " + r);
        if (x >= (1 << r))   throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    public static void close() {
        flush();
        try {
            out.close();
            isInitialized = false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void flush() {
        clearBuffer();
        try {
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeByte(int x) {
        if (!isInitialized) initialize();

        assert x >= 0 && x < 256;

        // optimized if byte-aligned
        if (n == 0) {
            try {
                out.write(x);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // otherwise write one bit at a time
        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    private static void writeBit(boolean bit) {
        if (!isInitialized) initialize();

        // add bit to buffer
        buffer <<= 1;
        if (bit) buffer |= 1;

        // if buffer is full (8 bits), write out as a single byte
        n++;
        if (n == 8) clearBuffer();
    }

    private static void clearBuffer() {
        if (!isInitialized) initialize();

        if (n == 0) return;
        if (n > 0) buffer <<= (8 - n);
        try {
            out.write(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        n = 0;
        buffer = 0;
    }

}

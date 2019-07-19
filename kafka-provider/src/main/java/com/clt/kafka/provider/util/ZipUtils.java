package com.clt.kafka.provider.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZipUtils {
    public ZipUtils() {
    }

    public static byte[] zip(byte[] bs) throws IOException {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        Throwable var2 = null;

        try {
            Deflater ex = new Deflater();
            ex.setInput(bs);
            ex.finish();
            byte[] output = new byte[1024];

            while(!ex.finished()) {
                int got = ex.deflate(output);
                o.write(output, 0, got);
            }

            o.flush();
            byte[] var16 = o.toByteArray();
            return var16;
        } catch (Throwable var14) {
            var2 = var14;
            throw var14;
        } finally {
            if (o != null) {
                if (var2 != null) {
                    try {
                        o.close();
                    } catch (Throwable var13) {
                        var2.addSuppressed(var13);
                    }
                } else {
                    o.close();
                }
            }

        }
    }

    public static byte[] unzip(byte[] bs) throws IOException, DataFormatException {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        Throwable var2 = null;

        try {
            Inflater ex = new Inflater();
            ex.setInput(bs);
            byte[] result = new byte[1024];

            while(!ex.finished()) {
                int resultLength = ex.inflate(result);
                o.write(result, 0, resultLength);
            }

            ex.end();
            o.flush();
            byte[] var16 = o.toByteArray();
            return var16;
        } catch (Throwable var14) {
            var2 = var14;
            throw var14;
        } finally {
            if (o != null) {
                if (var2 != null) {
                    try {
                        o.close();
                    } catch (Throwable var13) {
                        var2.addSuppressed(var13);
                    }
                } else {
                    o.close();
                }
            }

        }
    }
}

package com.clt.kafka.consumer.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZipUtils {

    public static byte[] zip(byte[] bs) throws IOException {

        try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
            Deflater ex = new Deflater();
            ex.setInput(bs);
            ex.finish();
            byte[] output = new byte[1024];

            while (!ex.finished()) {
                int got = ex.deflate(output);
                o.write(output, 0, got);
            }

            o.flush();
            return o.toByteArray();
        }

    }

    public static byte[] unzip(byte[] bs) throws IOException, DataFormatException {

        try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
            Inflater ex = new Inflater();
            ex.setInput(bs);
            byte[] result = new byte[1024];

            while (!ex.finished()) {
                int resultLength = ex.inflate(result);
                o.write(result, 0, resultLength);
            }

            ex.end();
            o.flush();
            return o.toByteArray();
        }

    }

}

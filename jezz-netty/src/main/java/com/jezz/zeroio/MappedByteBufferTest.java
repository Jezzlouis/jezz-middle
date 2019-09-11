package com.jezz.zeroio;

import org.junit.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class MappedByteBufferTest {

    @Test
    public void MBBTest() throws IOException {
        File file = new File("/Users/jezzlouis/Documents/db.txt");

        long len = file.length();

        byte[] ds = new byte[(int) len];

        MappedByteBuffer mappedByteBuffer = new FileInputStream(file).getChannel().map(FileChannel.MapMode.READ_ONLY, 0, len);

        for (int offset = 0; offset < len; offset++) {
            byte b = mappedByteBuffer.get();
            ds[offset] = b;
        }

        Scanner scan = new Scanner(new ByteArrayInputStream(ds)).useDelimiter(" ");

        while (scan.hasNext()) {
            System.out.print(scan.next() + " ");
        }
    }

    @Test
    public void ChannelTransferTest() throws IOException {
        String files[] = new String[1];
        files[0] = "/Users/jezzlouis/Documents/db.txt";
        for (int i = 0; i < files.length; i++) {
            FileInputStream fileInputStream = new FileInputStream(files[i]);
            FileChannel fileChannel = fileInputStream.getChannel();
            fileChannel.transferTo(0,fileChannel.size(), Channels.newChannel(System.out));
            fileChannel.close();
            fileInputStream.close();
        }
    }



}
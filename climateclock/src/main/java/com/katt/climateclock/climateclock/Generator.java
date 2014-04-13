package com.katt.climateclock.climateclock;
import android.content.Context;
import android.net.Uri;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by Tarun on 4/12/14.
 *
 * Uses File I/O in order to generate values in a wave file
 *
 *
 */
public class Generator {

    //Get the context using the getContext instruction, the filename is the name of the wave file
    //for example if file is "rainy.wav" parameter would be "rainy"
    public void generateHeader(Wave wavFile, Context con, String fileName) {

        InputStream in;
       // File wave = new File(fileName.getPath());
        char firstByte;
        char secondByte;
        char thirdByte;
        char fourthByte;


        try {


            in = con.getResources().openRawResource(con.getResources().getIdentifier(fileName, "raw", con.getPackageName()));;

            wavFile.RIFF[0] = (char) in.read();

            wavFile.RIFF[1] = (char) in.read();

            wavFile.RIFF[2] = (char) in.read();

            wavFile.RIFF[3] = (char) in.read();

            firstByte = (char) in.read();

            secondByte = (char) in.read();

            thirdByte = (char) in.read();

            fourthByte = (char) in.read();

            wavFile.chunkSize = little_endian_4(firstByte, secondByte, thirdByte, fourthByte);

            wavFile.WAVE[0] = (char) in.read();

            wavFile.WAVE[1] = (char) in.read();

            wavFile.WAVE[2] = (char) in.read();

            wavFile.WAVE[3] = (char) in.read();

            wavFile.fmt[0] = (char) in.read();

            wavFile.fmt[1] = (char) in.read();

            wavFile.fmt[2] = (char) in.read();

            wavFile.fmt[3] = (char) in.read();

            firstByte = (char) in.read();

            secondByte = (char) in.read();

            thirdByte = (char) in.read();

            fourthByte = (char) in.read();

            wavFile.subchunk1Size = little_endian_4(firstByte, secondByte, thirdByte, fourthByte);

            firstByte = (char) in.read();

            secondByte = (char) in.read();

            wavFile.audioFormat = little_endian_2(firstByte, secondByte);

            firstByte = (char) in.read();

            secondByte = (char) in.read();

            wavFile.numChan = little_endian_2(firstByte, secondByte);

            firstByte = (char) in.read();

            secondByte = (char) in.read();

            thirdByte = (char) in.read();

            fourthByte = (char) in.read();

            wavFile.samplesPerSec = little_endian_4(firstByte, secondByte, thirdByte, fourthByte);

            firstByte = (char) in.read();

            secondByte = (char) in.read();

            thirdByte = (char) in.read();

            fourthByte = (char) in.read();

            wavFile.bytesPerSec = little_endian_4(firstByte, secondByte, thirdByte, fourthByte);

            firstByte = (char) in.read();

            secondByte = (char) in.read();

            wavFile.blockAlign = little_endian_2(firstByte, secondByte);

            firstByte = (char) in.read();

            secondByte = (char) in.read();

            wavFile.bitsPerSample = little_endian_2(firstByte, secondByte);

            wavFile.extra = new char[wavFile.subchunk1Size - 16];

            int i = 0;

            while( i < wavFile.extra.length){

                wavFile.extra[i] = (char) in.read();
                i++;

            }

            wavFile.subchunk2ID[0] = (char) in.read();

            wavFile.subchunk2ID[1] = (char) in.read();

            wavFile.subchunk2ID[2] = (char) in.read();

            wavFile.subchunk2ID[3] = (char) in.read();

            firstByte = (char) in.read();

            secondByte = (char) in.read();

            thirdByte = (char) in.read();

            fourthByte = (char) in.read();

            wavFile.subchunk2Size = little_endian_4(firstByte, secondByte, thirdByte, fourthByte);
            //   System.out.println(wavFile.subchunk2Size);
            //  System.out.println(wavFile.audioFormat);
            // System.out.println(wavFile.bitsPerSample);
            // System.out.println(wavFile.numChan);

            wavFile.sizeOfData = (wavFile.subchunk2Size*8/wavFile.bitsPerSample*wavFile.numChan);

            wavFile.data = new short[wavFile.sizeOfData];

            for(i = 0; i < wavFile.sizeOfData; i++) {

                firstByte = (char) in.read();

                secondByte = (char) in.read();

                wavFile.data[i] = little_endian_2(firstByte,secondByte);

            }


            System.out.println("Data[5] = "+ wavFile.data[5]);

            System.out.println("Wave File has been Read!");

        } catch(Exception e) {

            System.out.println("There is a file error");
            e.printStackTrace();

        }


    }

    public void overlay(Wave baseWave, Wave soundBite, Context con, int insertTime) {

        //File outputFile = new File("res/output.wav");
        FileOutputStream out;
        int startClip;
        char[] holdE2 = new char[2];
        char[] holdE4 = new char[4];
        int i;

        try {

            out = con.openFileOutput("music/output.wav",con.MODE_PRIVATE);

            out.write(baseWave.RIFF[0]);

            out.write(baseWave.RIFF[1]);

            out.write(baseWave.RIFF[2]);

            out.write(baseWave.RIFF[3]);

            holdE4 = reverse_little_endian_4(baseWave.chunkSize);
            out.write(holdE4[0]);
            out.write(holdE4[1]);
            out.write(holdE4[2]);
            out.write(holdE4[3]);

            out.write(baseWave.WAVE[0]);

            out.write(baseWave.WAVE[1]);

            out.write(baseWave.WAVE[2]);

            out.write(baseWave.WAVE[3]);

            out.write(baseWave.fmt[0]);

            out.write(baseWave.fmt[1]);

            out.write(baseWave.fmt[2]);

            out.write(baseWave.fmt[3]);

            holdE4 = reverse_little_endian_4(baseWave.subchunk1Size);
            out.write(holdE4[0]);
            out.write(holdE4[1]);
            out.write(holdE4[2]);
            out.write(holdE4[3]);

            holdE2 = reverse_little_endian_2((short)baseWave.audioFormat);
            out.write(holdE2[0]);
            out.write(holdE2[1]);

            holdE2 = reverse_little_endian_2((short)baseWave.numChan);
            out.write(holdE2[0]);
            out.write(holdE2[1]);

            holdE4 = reverse_little_endian_4(baseWave.samplesPerSec);
            out.write(holdE4[0]);
            out.write(holdE4[1]);
            out.write(holdE4[2]);
            out.write(holdE4[3]);

            holdE4 = reverse_little_endian_4(baseWave.bytesPerSec);
            out.write(holdE4[0]);
            out.write(holdE4[1]);
            out.write(holdE4[2]);
            out.write(holdE4[3]);

            holdE2 = reverse_little_endian_2((short)baseWave.blockAlign);
            out.write(holdE2[0]);
            out.write(holdE2[1]);

            holdE2 = reverse_little_endian_2((short)baseWave.bitsPerSample);
            out.write(holdE2[0]);
            out.write(holdE2[1]);

            i = 0;

            while( i < baseWave.extra.length){

                out.write(baseWave.extra[i]);
                i++;

            }

            out.write(baseWave.subchunk2ID[0]);

            out.write(baseWave.subchunk2ID[1]);

            out.write(baseWave.subchunk2ID[2]);

            out.write(baseWave.subchunk2ID[3]);

            holdE4 = reverse_little_endian_4(baseWave.subchunk2Size);
            out.write(holdE4[0]);
            out.write(holdE4[1]);
            out.write(holdE4[2]);
            out.write(holdE4[3]);

            startClip = baseWave.samplesPerSec * insertTime * baseWave.numChan;

            System.out.println("Starting to create output file");
            System.out.println("Data[5] = "+ baseWave.data[5]);
            for(i = 0; i < startClip; i++) {

                //System.out.println(i);
                holdE2 = reverse_little_endian_2(baseWave.data[i]);
                out.write(holdE2[0]);
                out.write(holdE2[1]);

            }

            int k = 0;

            for(k = 0; k < soundBite.sizeOfData; k++) {

                holdE2 = reverse_little_endian_2_int(baseWave.data[i]+soundBite.data[k]);
                out.write(holdE2[0]);
                out.write(holdE2[1]);
                i++;
            }

            int j = 0;

            for(j = 0; j < baseWave.sizeOfData - soundBite.sizeOfData - startClip; j++){


                holdE2 = reverse_little_endian_2(baseWave.data[i]);
                out.write(holdE2[0]);
                out.write(holdE2[1]);
                i++;

            }


        } catch (Exception e ) {

            System.out.println("Error!");
            e.printStackTrace();
        }


        System.out.println("Output file created");

    }

    private int little_endian_4(char firstByte, char secondByte, char thirdByte, char fourthByte) {

        int result;

        result = fourthByte;
        result = (result << 8);
        result += thirdByte;
        result = (result << 8);
        result += secondByte;
        result = (result << 8);
        result += firstByte;

        return result;

    }


    private short little_endian_2(char firstByte, char secondByte){

        short result;

        result = (short) secondByte;
        result = (short) (result << 8);
        result += (short) firstByte;

        return result;

    }

    private char[] reverse_little_endian_2(short endianNum) {

        char[] bytes = new char[2];

        // System.out.println(endianNum);
        bytes[0] = (char) (endianNum % 256);

        bytes[1] = (char) ((endianNum >> 8) % 256);


        // System.out.println(bytes[1] + bytes[0]);

        return bytes;
    }


    private char[] reverse_little_endian_2_int(int endianNum) {

        char[] bytes = new char[2];

        // System.out.println(endianNum);
        bytes[0] = (char) (endianNum % 256);

        bytes[1] = (char) ((endianNum >> 8) % 256);


        // System.out.println(bytes[1] + bytes[0]);

        return bytes;
    }
    private char[] reverse_little_endian_4(int endianNum){

        char[] bytes = new char[4];

        bytes[0] = (char) (endianNum % 256);
        bytes[1] = (char) ((endianNum >> 8) % 256);
        bytes[2] = (char) ((endianNum >> 16) % 256);
        bytes[3] = (char) ((endianNum >> 24) % 256);

        return bytes;
    }





}

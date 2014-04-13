package com.katt.climateclock.climateclock;

/**
 * Created by Tarun on 4/12/14.
 *
 * This is a new Wave Class. Contains information from Wave files
 *
 */
public class Wave {


        public char[] RIFF = new char[4];
        public int chunkSize;
        public char[] WAVE = new char[4];
        public char[] fmt = new char[4];
        public int subchunk1Size;
        public short audioFormat;
        public short numChan;
        public int samplesPerSec;
        public int bytesPerSec;
        public short blockAlign;
        public short bitsPerSample;
        public char[] extra;
        public char[] subchunk2ID = new char[4];
        public int subchunk2Size;
        public short[] data;

        public int sizeOfData;

        public Wave() {


        }


}

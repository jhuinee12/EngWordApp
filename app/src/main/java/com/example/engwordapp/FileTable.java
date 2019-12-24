package com.example.engwordapp;

import java.io.IOException;
import java.io.InputStream;

// raw 폴더에 있는 영어단어 파일을 불러와서 내용을 data 배열에 담는 클래스
// 그리고나서 FileSplit0 클래스파일에 데이터를 넘여주는 역할
public class FileTable {

    InputStream fi; // InputStream은 byte 단위로 파일에서 프로그램으로 데이터를 운반

    private FileSplit0 word;
    private FileSplit1 word2;

    // 단어공부 파일 읽어오기
    public void loadFile(int num) {     // num 값에 따라 openRawResource() 메소드를 이용해 high01.txt, high08,txt 파일을 불러옴
        fi = StudyView.mContext.getResources().openRawResource(R.raw.high01 + num);

        try {
            byte[] data = new byte[fi.available()]; // available() 메소드를 이용해서 파일의 크기를 구함
            fi.read(data);  // 입력스트림에서 자료를 읽어 data 배열에 저장
            fi.close();
            String s = new String(data, "UTF-8");
            word = new FileSplit0(s);
        } catch (IOException e) {}
    }

    // 평가파일 읽어오기
    public void loadFile2(int num) {    // num 값에 따라 text00.txt, text01.txt, text02.txt 등의 파일을 불러옴 => num=0 : text00.txt
        fi = StudyView5.mContext.getResources().openRawResource(R.raw.test00 + num);

        try {
            byte[] data = new byte[fi.available()];
            fi.read(data);
            fi.close();
            String s = new String(data, "UTF-8");
            word2 = new FileSplit1(s);
        } catch (IOException e) {}
    }
}

package com.example.engwordapp;

// [단어공부]에 사용되는 파일들을 questionNum[][]에 담는 역할 => static 변수로 설정하여 다른 클래스에서 쉽게 접근
// 파일 입출력 시 발생하는 오류를 방지하기 위해 try~catch 구문 사용
public class FileSplit0 {
    public static String questionNum[][] = new String[100][10];

    public FileSplit0(String str) {
        String tmp[] = str.split("\n");
        String s;
        char ch;
        for (int i=0; i<tmp.length; i++) {
            s = tmp[i];
            String tmp2[] = s.split(":");
            for (int j=0; j<10; j++) {
                tmp2[j] = tmp2[j].trim();   // trim 메소드를 사용하여 빈 공간 없애기
                questionNum[i][j] = tmp2[j];
            }
        }
    }
}

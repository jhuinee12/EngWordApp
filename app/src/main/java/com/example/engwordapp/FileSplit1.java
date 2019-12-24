package com.example.engwordapp;

// FileTable에서 넘겨온 자료를 split 메소드를 활용하여 줄단위("\n")로 잘라 tmp[] 자료를 입력
// tmp.length는 text00.txt 파일의 행 수 1000을 의미
public class FileSplit1 {
    public static String questionNum2[][] = new String[1000][10];   // 파일의 내용 담기
    public static String questionNum[][] = new String[100][10];   // 추출된 문제 100개 담기

    public FileSplit1 (String str) {
        String tmp[] = str.split("\n");
        String s;
        char ch;
        for (int i=0; i<tmp.length; i++) {
            s = tmp[i];
            String tmp2[] = s.split(":");

            for (int j=0; j<10; j++) {
                tmp2[j] = tmp2[j].trim();   // trim 메소드 : 앞 뒤 공백을 없애줌
                questionNum2[i][j] = tmp2[j];
            }
        }
        makeHundred();
    }

    public void makeHundred() {
        int selectedQuestion;
        double randomNum;

        for (int i=0; i<1000; i++)
            questionNum2[i][9] = "";
        for (int i=0; i<100; i++) {
            do {
                randomNum = Math.random();
                selectedQuestion = (int) ((randomNum*(1000)));
            } while (questionNum2[selectedQuestion][9] == "yes");

            // 동일한 문제 출제 방지
            for (int j=0; j<10; j++) {
                FileSplit1.questionNum[i][j] = FileSplit1.questionNum2[selectedQuestion][j];
                FileSplit1.questionNum[i][0] = Integer.toString(i+1);
            }
            questionNum2[selectedQuestion][9] = "yes";
        }
    }
}

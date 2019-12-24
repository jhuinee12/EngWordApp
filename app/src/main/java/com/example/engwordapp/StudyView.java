package com.example.engwordapp;

/* <<SQLiteOpenHelper>>
MyDBHelper 생성자 : DB 생성
onCreate() : Create Table 명령을 사용해서 테이블 생성
onUpgrade() : 데이터베이스가 업그레이드 될 필요가 있을 때 기존 테이블 삭제 및 새로운 테이블 생성
getWritableDatabase() : 쓰고 읽을 수 있는 데이터베이스를 연다. SQLiteDatabase 객체를 반환하고 SQLiteDatabase가 가지고 있는 메소드 (execSQL, close 등)을 사용
getReadableDatabase() : 읽을 수 있는 데이터베이스를 연다. SQLiteDatabase 객체를 반환
*/

/* <<SQLiteDatabase Class>>
execSQL()
    insert into : 레코드를 테이블에 추가하기
    Update : 존재하는 레코드값 변경하기
    Delete : 테이블에서 레코드 삭제하기
close() : DB닫기
query(), rawQuery() : query() 실행 후 커서 반환. Select문을 사용할 때는 rawQuery() 사용
*/

/* <<Cursor Interface>>
moveToFirst() : 커서를 레코드 제일 첫 행으로 이동
moveToNext() : 커서를 레코드 다음 행으로 이동
moveToLast() : 커서를 레코드 마지막 행으로 이동
moveToPosition(i) : 커서를 원하는 위치에 놓기
*/

/*
<테이블 생성하기>
CREATE TABLE 테이블명 (_id, 컬럼명 타입 옵션, 컬럼명 타입 옵션);
<레코드 삽입하기>
INSERT INTO 테이블명 values (값, 값, 값);
<레코드 삭제하기>
DELETE FROM 테이블명 WHERE 조건;
*/

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;

import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.lang.annotation.Inherited;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class StudyView extends SurfaceView implements Callback, GestureOverlayView.OnGestureListener, android.view.GestureDetector.OnGestureListener {
    static int soundOk = 1;
    int questionNumber = 0;
    int numberOfquestion = 99;

    int textSizeForG4 = 0;
    int textSizeChanging = 0;
    int textSizeChanging2 = 0;

    int answerButton = 0;
    int answerUser = 0;

    int starIng = 0;
    int starIndex = 0;
    int starX, starY;

    int oNumber = 0;
    int xNumber = 0;

    int whatStudy = 0;

    int submenuOk = 0;
    int submenuOk2 = 0; // submenuOk와 submenuOk2 값이 0일 때 학습화면이 나옴

    // 값이 1이면 [단어장등록] 아이콘이 화면에 제시
    int wordSave = 0;

    double rand;
    int btnPressed = 0;

    String[] wordForDelete = {"", "", "", "", ""};
    String wordToDelete = "";

    // SurfaceView
    static StudyThread mThread;
    SurfaceHolder mHolder;
    static Context mContext;
    FileTable mFile;

    // db
    MyDBHelper m_helper;    // MyDBHelper 클래스 사용을 위한 객체 선언

    Cursor cursor;
    int dicOk = 0;
    int movePosition = 0;

    MyButton1 btnNext;  // btnNext : next
    MyButton1 btnPrevious;  // btnPrevious : previous
    MyButton1 btnWordSelection;
    MyButton1 btnMyNote;
    MyButton1 btnExit;
    MyButton1 btnRandom;    // btn : random
    MyButton1 btnNum1;  // btn : number1
    MyButton1 btnNum2;  // btn : number2
    MyButton1 btnNum3;  // btn : number3
    MyButton1 btnNum4;  // btn : number4
    MyButton1 btnNum5;  // btn : number5 사용안함
    MyButton1 btnNextQuestion;
    MyButton1 btnSolveAgain;

    String whichSubject = "선택단어 1";

    // sub menu 8개
    MyButton1 btnSub1;
    MyButton1 btnSub2;
    MyButton1 btnSub3;
    MyButton1 btnSub4;
    MyButton1 btnSub5;
    MyButton1 btnSub6;
    MyButton1 btnSub7;
    MyButton1 btnSub8;

    MyButton1 btnWordSave;
    MyButton1 btnKakaoQSending;

    MyButton1 btnLeftArrow; // left
    MyButton1 btnRightArrow;    // right
    MyButton1 btnClose; // close button in circle

    MyButton1 btnForDictionary[];

    // 단어 전체 삭제 버튼 : 여기서는 사용안함
    MyButton1 btnAllDelete;

    // 제스처 기능 (손가락으로 화면 드래그하기)에 사용되는 변수들
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureScanner;

    int btnPreCount = 0;
    int btnNextCount = 0;
    int btnSelectCount = 0;
    int btnMyNoteCount = 0;
    int btnRanCount = 0;

    int btnNum1Count = 0;
    int btnNum2Count = 0;
    int btnNum3Count = 0;
    int btnNum4Count = 0;

    // 스마트기기 화면 가로크기 및 세로크기 값
    static int Width, Height;

    // 예를 들어 서브메뉴에서 수능#2 를 선택하면 변수 subNumber에는 2가 저장
    int subNumber = 1;

    Bitmap chilpan;
    Bitmap answerx;
    Bitmap answero;
    Bitmap cap;
    Bitmap explain;

    Bitmap star[] = new Bitmap[4];  // 1,2,3,4 버튼 클릭시 원이 나옴
    static SoundPool sdPool;
    static int dindongdaeng, taeng;

    public StudyView (Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mHolder = holder;
        mContext = context;
        mThread = new StudyThread(holder, context);

        gestureScanner = new GestureDetector(this);

        initAll();
        makeQuestion(subNumber);
        setFocusable(true);
    }

    private void initAll() {
        // MyDBHelper 클래스 사용을 위한 객체 생성, 두번째 파라미터가 데이터베이스 이름
        m_helper = new MyDBHelper(mContext, "testforeng.db", null, 1);
        Display display = ((WindowManager) mContext.getSystemService
                (Context.WINDOW_SERVICE)).getDefaultDisplay();
        Width = display.getWidth();
        Height = display.getHeight();
        textSizeChanging = (int) (Width*64/1280);
        if (Width>1700) textSizeForG4 = 120;

        mFile = new FileTable();

        // 다음 문제 버튼
        btnNext = new MyButton1(30, 14+20, 0);
        // new 연산자를 이용해서 MyButton1 객체를 생성한다. 3개의 파라미터를 넘겨준다
        // 첫 번째 파라미터는 x좌표, 두번째 파라미터는 y좌표, 세번째 파라미터는 그림파일과 관련 있다 (MyButton1 클래스 참고)

        // 이전 버튼
        btnPrevious = new MyButton1(btnNext.x + btnNext.w*2, 14+20, 1); // Next
        // 단어선택
        btnWordSelection = new MyButton1(btnPrevious.x + btnNext.w*2, 14+20, 2);
        // 내 노트 버튼
        btnMyNote = new MyButton1(btnWordSelection.x + btnNext.w*2, 14+20, 4);
        // 나가기 버튼
        btnExit = new MyButton1(Width - btnNext.w*2 - btnNext.w/3, 14+20, 5);
        // 문제번호를 랜덤으로 나오게 하는 버튼
        btnRandom = new MyButton1(btnMyNote.x + btnNext.w*2, 14+20, 6);

        // 객관식 1번 버튼
        btnNum1 = new MyButton1(btnNext.x+70+50, btnNext.y+btnNext.h*2 + 93, 7);
        // 객관식 2번 버튼
        btnNum2 = new MyButton1(btnNext.x+70+50, btnNext.y+btnNext.h*2 + 8, 8);
        // 객관식 3번 버튼
        btnNum3 = new MyButton1(btnNext.x+70+50, btnNext.y+btnNext.h*2 + 8, 9);
        // 객관식 4번 버튼
        btnNum4 = new MyButton1(btnNext.x+70+50, btnNext.y+btnNext.h*2 + 8, 10);
        // 객관식 5번 버튼이며 사용 안함
        btnNum5 = new MyButton1(btnNext.x+70+50, btnNext.y+btnNext.h*2 + 8, 11);

        // 문제를 푼 후에 제시되는 다음문제 버튼
        btnNextQuestion = new MyButton1(Width - btnWordSelection.w*6, btnNum1.y+btnNum1.h*2+1, 12);
        // 문제를 푼 후에 제시되는 다시풀기 버튼
        btnSolveAgain = new MyButton1(Width - btnWordSelection.w*6, btnNextQuestion.y + btnNextQuestion.h*2+1, 13);
        // 문제를 푼 후에 제시되는 단어장 등록 버튼
        btnWordSave = new MyButton1(Width - btnWordSelection.w*6, btnNextQuestion.y+btnNextQuestion.h*2+1, 23);
        // 문제를 푼 후에 제시되는 카카오 문제보내기 버튼
        btnKakaoQSending = new MyButton1(Width - btnWordSelection.w*6, btnSolveAgain.y+btnSolveAgain.h*2+1, 23);

        // sub menu 단어 선택 메뉴
        btnSub1 = new MyButton1(btnPrevious.x + 10, btnWordSelection.y+btnWordSelection.h*2+5, 15);
        btnSub2 = new MyButton1(btnSub1.x + btnSub1.w*2, btnSub1.y, 16);
        btnSub3 = new MyButton1(btnSub2.x + btnSub2.w*2, btnSub2.y, 17);
        btnSub4 = new MyButton1(btnSub3.x + btnSub3.w*2, btnSub3.y, 18);
        btnSub5 = new MyButton1(btnSub4.x + btnSub4.w*2, btnSub4.y, 19);
        btnSub6 = new MyButton1(btnPrevious.x + 10, btnSub1.y + btnSub1.h*2, 20);
        btnSub7 = new MyButton1(btnSub1.x + btnSub1.w*2, btnSub1.y+btnSub1.h*2, 21);
        btnSub8 = new MyButton1(btnSub2.x + btnSub2.w*2, btnSub1.y+btnSub1.h*2, 22);

        // 내사전에서 왼쪽, 오른쪽 버튼
        btnLeftArrow = new MyButton1(btnPrevious.x, Height-btnNext.h*2, 26);
        btnRightArrow = new MyButton1(btnPrevious.x + 150, Height-btnNext.h*2, 27);
        // 닫기 버튼
        btnClose = new MyButton1(Width - btnNext.w*2, Height-btnNext.h*2, 28);
        // 단어장에서 사용되는 삭제버튼. 한 화면에 5개씩 단어가 삭제버튼과 함께 제시됨
        btnForDictionary = new MyButton1[5];
        // 삭제버튼
        for (int i=0; i<5; i++)
            btnForDictionary[i] = new MyButton1(Width - btnNum1.w*4, btnNum1.h*4+i*btnNum1.h*2+btnNum1.h/12*i, 29);

        answerx = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.answerx);
        int xxx = Width / 6;
        answerx = Bitmap.createScaledBitmap(answerx, xxx, xxx, true);
        answero = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.answero);
        answero = Bitmap.createScaledBitmap(answero, xxx, xxx, true);
        explain = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.explain);
        explain = Bitmap.createScaledBitmap(explain, Width / 11, Height / 7, true);
        cap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.cap);
        cap = Bitmap.createScaledBitmap(cap, Width / 12, Height / 14, true);

        for (int i = 0; i < 4; i++) {
            star[i] = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.circlewhite);
            star[i] = Bitmap.createScaledBitmap(star[i], btnNum1.w * 2 + i * 2, btnNum1.w * 2 + i * 2, true);
        }

        sdPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        dindongdaeng = sdPool.load(mContext, R.raw.dingdongdaeng, 1);
        taeng = sdPool.load(mContext, R.raw.taeng, 2);
    }

    // 문제만들기
    public void makeQuestion(int x) {
        mFile.loadFile(x);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.setRunning(true);
        try {
            if (mThread.getState() == Thread.State.TERMINATED) {
                mThread = new StudyThread(getHolder(), mContext);
                mThread.setRunning(true);
                setFocusable(true);
                mThread.start();
            } else {
                mThread.start();
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        StopStudy();

        boolean retry = true;
        mThread.setRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (Exception e) {
            }
        }
    }

    private void StopStudy() {
        mThread.StopThread();
    }

    class StudyThread extends Thread {
        boolean canRun = true;
        boolean isWait = false;
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();

        public StudyThread(SurfaceHolder holder, Context context) {
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.create("", Typeface.BOLD));

            paint2.setColor(Color.WHITE);
            paint2.setAntiAlias(true);
            paint2.setTypeface(Typeface.create("", Typeface.BOLD));

            paint3.setColor(Color.WHITE);
            paint3.setAntiAlias(true);
            paint3.setTypeface(Typeface.create("", Typeface.BOLD));

            paint.setTextSize(TypedValue.COMPLEX_UNIT_DIP);
            paint2.setTextSize(35);
            paint3.setTextSize(40);
        }

        public void setRunning(boolean b) {
            // TODO Auto-generated method stub
        }

        public void DrawAll(Canvas canvas) {
            canvas.drawBitmap(chilpan, 0, 0, null);

            textSizeChanging = (int) (Width*58/1280); // aha hear I have to adjust
            textSizeChanging2 = (int) (Width*40/1280);

            // 텍스트 크기 설정
            paint.setTextSize(Width/23); // 영어 글씨
            paint2.setTextSize(Width/40); // 맞은 개수, 틀린 개수 글씨
            paint3.setTextSize(Width/32); // 영어단어 해설에 나오는 글씨

            if (submenuOk == 0 && submenuOk2 == 0) {
                if (questionNumber > numberOfquestion)
                    questionNumber = numberOfquestion;
                if (questionNumber < 0) questionNumber = 0;

                // 문제번호
                canvas.drawText(FileSplit0.questionNum[questionNumber][0], btnNext.x + btnNum1.w, btnNext.y + btnNext.h*3, paint);
                // 문제
                canvas.drawText(FileSplit0.questionNum[questionNumber][1], btnNext.x + btnNum1.w*3, btnNext.y+btnNext.h*3, paint);

                // 객관식 선택버튼 1,2,3,4
                canvas.drawText(FileSplit0.questionNum[questionNumber][2], btnNext.x+btnNum1.w*6, btnNum1.y+btnNum1.w+btnNum1.w/2, paint);
                canvas.drawText(FileSplit0.questionNum[questionNumber][3], btnNext.x+btnNum1.w*6, btnNum1.y+btnNum1.w+btnNum1.w/2, paint);
                canvas.drawText(FileSplit0.questionNum[questionNumber][4], btnNext.x+btnNum1.w*6, btnNum1.y+btnNum1.w+btnNum1.w/2, paint);
                canvas.drawText(FileSplit0.questionNum[questionNumber][5], btnNext.x+btnNum1.w*6, btnNum1.y+btnNum1.w+btnNum1.w/2, paint);
            }

            // submenuOk 값이 1이면 메뉴를 의미한다. submenuOk 값이 1이면 내단어장이 나온다.
            // submenuOk와 submenuOk2 값이 모두 0일 때 문제를 풀 수 있는 학습장면이 나오도록 하였다.
            if (submenuOk == 0 && submenuOk2 == 0) {
                // 1~4
                canvas.drawBitmap(btnNum1.button_img, btnNum1.x, btnNum1.y, null);
                canvas.drawBitmap(btnNum2.button_img, btnNum2.x, btnNum2.y, null);
                canvas.drawBitmap(btnNum3.button_img, btnNum3.x, btnNum3.y, null);
                canvas.drawBitmap(btnNum4.button_img, btnNum4.x, btnNum4.y, null);
                canvas.drawBitmap(btnKakaoQSending.button_img, btnKakaoQSending.x, btnKakaoQSending.y, null);
            }

            if (submenuOk == 0 && submenuOk2 == 0) {
                canvas.drawBitmap(btnNext.button_img, btnNext.x, btnNext.y, null);
                canvas.drawBitmap(btnPrevious.button_img, btnPrevious.x, btnPrevious.y, null);
                canvas.drawBitmap(btnWordSelection.button_img, btnWordSelection.x, btnWordSelection.y, null);
                canvas.drawBitmap(btnMyNote.button_img, btnMyNote.x, btnMyNote.y, null);
                canvas.drawBitmap(btnExit.button_img, btnExit.x, btnExit.y, null);
                canvas.drawBitmap(btnRandom.button_img, btnRandom.x, btnRandom.y, null);
            }

            // sub menu
            if (submenuOk == 1) {
                canvas.drawBitmap(chilpan, 0, 0, null);
                canvas.drawText("각 서브메뉴에는 100개의 단어가 있습니다.", btnNext.x + 50 + 50, btnNext.h*2, paint);
                canvas.drawBitmap(btnClose.button_img, btnClose.x, btnClose.y, null);
                canvas.drawBitmap(btnSub1.button_img, btnSub1.x, btnSub1.y, null);
                canvas.drawBitmap(btnSub2.button_img, btnSub2.x, btnSub2.y, null);
                canvas.drawBitmap(btnSub3.button_img, btnSub3.x, btnSub3.y, null);
                canvas.drawBitmap(btnSub4.button_img, btnSub4.x, btnSub4.y, null);
                canvas.drawBitmap(btnSub5.button_img, btnSub5.x, btnSub5.y, null);
                canvas.drawBitmap(btnSub6.button_img, btnSub6.x, btnSub6.y, null);
                canvas.drawBitmap(btnSub7.button_img, btnSub7.x, btnSub7.y, null);
                canvas.drawBitmap(btnSub8.button_img, btnSub8.x, btnSub8.y, null);
            }

            if (submenuOk == 0 && submenuOk2 == 0) {
                canvas.drawText(whichSubject, Width/2, btnExit.h, paint2);
                canvas.drawText("맞은 개수:" + Integer.toString(oNumber)+"개 틀린 개수:"+Integer.toString(xNumber)+"개",Width/2,btnExit.h*2,paint2);
            }

            if (answerButton == 1 && dicOk == 0) {
                int sss = 0;
                sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                if (sss == answerUser)
                    canvas.drawBitmap(answero, btnNum1.x+280, btnNum1.y+20, null);
                else
                    canvas.drawBitmap(answerx, btnNum1.x+280, btnNum1.y+20, null);

                // 다음문제
                canvas.drawBitmap(btnNextQuestion.button_img, btnNextQuestion.x, btnNextQuestion.y, null);
                // 다시풀기
                canvas.drawBitmap(btnSolveAgain.button_img, btnSolveAgain.x, btnSolveAgain.y, null);

                // 단어장 풀기
                if (answerButton == 1 && wordSave == 1)
                    canvas.drawBitmap(btnWordSave.button_img, btnWordSave.x, btnWordSave.y, null);

                // 해설 아이콘 및 문제 해설
                canvas.drawBitmap(explain, 8, btnNum4.y+btnNum4.h*2-50, null);
                canvas.drawText(FileSplit0.questionNum[questionNumber][7], 27, btnNum4.y+btnNum4.h*4, paint3);
                canvas.drawText(FileSplit0.questionNum[questionNumber][8], 27, btnNum4.y+btnNum4.h*4+btnNum1.h, paint3);
            }
            // 객관식 버튼이 터치되면 원모양이 나옴
            if (starIng == 1) {
                starIndex += 1;
                if (starIndex <= 15) {
                    starIng = 0;
                    starIndex = 0;
                } else
                    canvas.drawBitmap(star[starIndex/4], starX - starIndex/4, starY-starIndex/4, null);
            }

            // 버튼이 눌리면 다른 이미지가 일정시간 동안 나오도록 하기 위한 설정
            if (btnPressed == 1) {
                btnPreCount++;
                btnNextCount++;
                btnSelectCount++;
                btnMyNoteCount++;
                btnRanCount++;

                btnNum1Count++;
                btnNum2Count++;
                btnNum3Count++;
                btnNum4Count++;
            }

            if (btnPreCount == 15) {
                btnPreCount = 0;
                btnPrevious.btn_released();
            }
            if (btnNextCount == 15) {
                btnNextCount = 0;
                btnNext.btn_released();
            }
            if (btnSelectCount == 15) {
                btnSelectCount = 0;
                btnWordSelection.btn_released();
            }
            if (btnMyNoteCount == 15) {
                btnMyNoteCount = 0;
                btnMyNote.btn_released();
            }
            if (btnRanCount == 15) {
                btnRanCount = 0;
                btnRandom.btn_released();
            }
            if (btnNum1Count == 15) {
                btnNum1Count = 0;
                btnNum1.btn_released();
            }
            if (btnNum2Count == 15) {
                btnNum2Count = 0;
                btnNum2.btn_released();
            }
            if (btnNum3Count == 15) {
                btnNum3Count = 0;
                btnNum3.btn_released();
            }
            if (btnNum4Count == 15) {
                btnNum4Count = 0;
                btnNum4.btn_released();
            }

            // 내노트 나타나기
            if (dicOk == 1) {
                canvas.drawBitmap(chilpan, 0,0,null);
                SQLiteDatabase db = m_helper.getReadableDatabase();
                cursor = db.query("englishWordTable", null, null, null, null, null, null);
                int numofdb = cursor.getCount();

                if (movePosition>numofdb)
                    movePosition -= 5;
                else if (movePosition == numofdb)
                    movePosition -= 5;

                if (movePosition <= 0)
                    movePosition = 0;

                for (int i=0; i<5; i++) {
                    if (cursor.moveToPosition(movePosition+i) == false)
                        break;
                    canvas.drawText((movePosition+i+1) + " " + cursor.getString(1) + ":" + cursor.getString(2),
                            btnExit.w*3, btnExit.h*4+btnExit.w*3/2*i, paint);

                    // 현재 내노트에 있는 단어 5개 대한 영어단어를 삭제를 위해 wordForDelete[]에 담는다
                    wordForDelete[i] = cursor.getString(1);
                }

                // left,right arrow and close button in circle format
                canvas.drawBitmap(btnLeftArrow.button_img, btnLeftArrow.x, btnLeftArrow.y, null);
                canvas.drawBitmap(btnRightArrow.button_img, btnRightArrow.x, btnRightArrow.y, null);
                canvas.drawBitmap(btnClose.button_img, btnClose.x, btnClose.y, null);

                int imsy;
                int x = 0;
                for (int i=0; i<5; i++) {
                    canvas.drawText("저장된 단어수 : " + Integer.toString(numofdb), 100, 100, paint2);
                    imsy = 0;
                    if (numofdb == 0) {
                        canvas.drawText("단어가 없습니다!", 70, 180+90*i, paint);
                        break;
                    }

                    canvas.drawBitmap(btnForDictionary[i].button_img, btnForDictionary[i].x, btnForDictionary[i].y+btnExit.h/3, null);

                    x = (numofdb-1) / 5;
                    if ((movePosition)/5 < x)
                        imsy = 1;
                    else
                        imsy = 0;

                    if (imsy == 0){
                        if (numofdb%5 == 4 && i == 3) break;
                        if (numofdb%5 == 3 && i == 2) break;
                        if (numofdb%5 == 2 && i == 1) break;
                        if (numofdb%5 == 1 && i == 0) break;
                    }
                }

                cursor.close();
                db.close();
            }
        } // end of drawall

        public void run() {
            Canvas canvas = null;
            while (canRun) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        DrawAll(canvas);
                    } // sync
                } finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                } // try

                synchronized (this) {
                    if (isWait)
                        try {
                            wait();
                        } catch (Exception e) {
                            // nothing
                        }
                } // sync
            } // while
        } // run

        public void StopThread() {
            canRun = false;
            synchronized (this) {
                this.notify();
            }
        }
    } // Thread

    // 터치이벤트
    // onTouchEvent를 통해서 전달되는 MotionEvent 객체(event)에는 좌표와 액션코드 같은 많은 정보가 들어 있다.
    // event.getX(), event.getY()를 통해서 터치한 화면의 x,y 좌표를 구할 수 있다.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = 0, y = 0;

        synchronized (mHolder) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x = (int) event.getX();
                y = (int) event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
            }
        } // end of sync

        // 이전 버튼을 터치하면 문제번호(answerButton)가 1씩 감소한다.
        // answerButton 값이 0이 되면 문제를 풀 수 있게 된다.
        // btnPrevious.btn_press() 메소드가 호출되면 눌려진 이미지가 이전 버튼으로 표시된다.
        if (x>btnNext.x && x<(btnNext.x+btnNext.w*2) && y > btnNext.y && y<(btnNext.y+btnNext.h*2)) {
            questionNumber -= 1;
            answerButton = 0;
            btnPreCount = 0;
            btnPressed = 1;
            btnNext.btn_press();
            submenuOk = 0;
            dicOk = 0;
        }

        if (x>btnPrevious.x && x<(btnPrevious.x+btnPrevious.w*2) && y > btnPrevious.y && y<(btnPrevious.y+btnPrevious.h*2)) {
            questionNumber += 1;
            answerButton = 0;
            btnPreCount = 0;
            btnPressed = 1;
            btnPrevious.btn_press();
            submenuOk = 0;
            dicOk = 0;
        }

        if (x>btnWordSelection.x && x<(btnWordSelection.x+btnWordSelection.w*2) && y > btnWordSelection.y && y<(btnWordSelection.y+btnWordSelection.h*2)) {
            questionNumber += 1;
            answerButton = 0;
            btnSelectCount = 0;
            btnPressed = 1;
            submenuOk = 0;
            dicOk = 0;
            whatStudy = 0;
            submenuOk2 = 0;
        }

        if (x>btnMyNote.x && x<(btnMyNote.x+btnMyNote.w*2) && y > btnMyNote.y && y<(btnMyNote.y+btnMyNote.h*2)) {
            btnMyNote.btn_press();
            btnMyNoteCount = 0;
            btnPressed = 1;
            dicOk = 1;
            submenuOk = 0;
        }

        // exit button
        if (dicOk != 1) {
            if (x>btnExit.x && x<(btnExit.x+btnExit.w*2) && y > btnExit.y && y<(btnExit.y+btnExit.h*2)) {
                btnPressed = 1;
                System.exit(0);
                dicOk = 0;
                submenuOk = 0;
            }
        }

        if (x>btnRandom.x && x<(btnRandom.x+btnRandom.w*2) && y > btnRandom.y && y<(btnRandom.y+btnRandom.h*2)) {
            rand = Math.random();
            questionNumber = (int) ((rand*numberOfquestion+2));
            answerButton = 0;
            btnRanCount = 0;
            btnPressed = 1;
            btnRandom.btn_press();
            submenuOk = 0;
            dicOk = 0;
        }

        if (answerButton == 0) {
            // 1,2,3,4 선택 버튼
            if (dicOk == 0 && submenuOk == 0 && submenuOk2 == 0) {
                if (x > btnNum1.x && x < (btnNum1.x + btnNum1.w * 2) && y > btnNum1.y && y < (btnNum1.y + btnNum1.h * 2)) {
                    int sss = 0;
                    submenuOk = 0;
                    dicOk = 0;
                    answerUser = 1;
                    btnNum1.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    btnNum1Count = 0;
                    btnPressed = 1;
                    starIng = 1;
                    starX = btnNum1.x;
                    starY = btnNum1.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    // 정답이면 딩동댕 소리가 나고 오답이면 땡 소리가 나도록  하기
                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.dindongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                    } else {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.taeng, 1, 1, 9, 0, 1);
                        xNumber++;
                    }
                }
            }
            if (dicOk == 0 && submenuOk == 0 && submenuOk2 == 0) {
                if (x > btnNum2.x && x < (btnNum2.x + btnNum2.w * 2) && y > btnNum2.y && y < (btnNum2.y + btnNum2.h * 2)) {
                    int sss = 0;
                    submenuOk = 0;
                    dicOk = 0;
                    answerUser = 1;
                    btnNum2.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    btnNum2Count = 0;
                    btnPressed = 1;
                    starIng = 1;
                    starX = btnNum2.x;
                    starY = btnNum2.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.dindongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                    } else {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.taeng, 1, 1, 9, 0, 1);
                        xNumber++;
                    }
                }
            }
            if (dicOk == 0 && submenuOk == 0 && submenuOk2 == 0) {
                if (x > btnNum3.x && x < (btnNum3.x + btnNum3.w * 2) && y > btnNum3.y && y < (btnNum3.y + btnNum3.h * 2)) {
                    int sss = 0;
                    submenuOk = 0;
                    dicOk = 0;
                    answerUser = 3;
                    btnNum3.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    btnNum3Count = 0;
                    btnPressed = 1;
                    starIng = 1;
                    starX = btnNum3.x;
                    starY = btnNum3.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.dindongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                    } else {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.taeng, 1, 1, 9, 0, 1);
                        xNumber++;
                    }
                }
            }
            if (dicOk == 0 && submenuOk == 0 && submenuOk2 == 0) {
                if (x > btnNum4.x && x < (btnNum4.x + btnNum4.w * 2) && y > btnNum4.y && y < (btnNum4.y + btnNum4.h * 2)) {
                    int sss = 0;
                    submenuOk = 0;
                    dicOk = 0;
                    answerUser = 3;
                    btnNum4.btn_press();
                    answerButton = 1;
                    wordSave = 1;
                    btnNum3Count = 0;
                    btnPressed = 1;
                    starIng = 1;
                    starX = btnNum4.x;
                    starY = btnNum4.y;

                    sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                    if (sss == answerUser) {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.dindongdaeng, 1, 1, 9, 0, 1);
                        oNumber++;
                    } else {
                        if (soundOk == 1)
                            StudyView.sdPool.play(StudyView.taeng, 1, 1, 9, 0, 1);
                        xNumber++;
                    }
                }
            }
        } // end of answerButton

        if (answerButton == 1) {
            if (x > btnNextQuestion.x && x < (btnNextQuestion.x + btnNextQuestion.w * 2) && y > btnNextQuestion.y && y < (btnNextQuestion.y + btnNextQuestion.h * 2)) {
                btnNextQuestion.btn_press();
                questionNumber += 1;
                answerButton = 0;
                submenuOk = 0;
            }
        }
        if (answerButton == 1) {
            if (x > btnSolveAgain.x && x < (btnSolveAgain.x + btnSolveAgain.w * 2) && y > btnSolveAgain.y && y < (btnSolveAgain.y + btnSolveAgain.h * 2)) {
                btnSolveAgain.btn_press();
                answerButton = 0;
                submenuOk = 0;
            }
        }

        if (answerButton == 1 && wordSave == 1) {
            if (x < btnWordSave.x && x < (btnWordSave.x + btnWordSave.w * 2) && y > btnWordSave.y && y < (btnWordSave.y + btnWordSave.h * 2)) {
                wordSave = 0;
                btnWordSave.btn_press();
                submenuOk = 0;

                // sss는 정답 숫자를 입력받는다.
                int sss = Integer.parseInt(FileSplit0.questionNum[questionNumber][6].trim());

                dicOk = 0;
                SQLiteDatabase db = m_helper.getWritableDatabase();

                // sss+1은 정답에 해당되는 영어단어
                // questionNum[questionNumber][1]에서 첨자 1에 들어가는 것은 단어 뜻이다 (한글)
                String sql = String.format("INSERT INTO englishWordTable VALUES(NULL, '%s', '%s');",
                        FileSplit0.questionNum[questionNumber][sss + 1],
                        FileSplit0.questionNum[questionNumber][1]);

                db.execSQL(sql);
                db.close();

                Toast toast = Toast.makeText(mContext, "단어가 저장되었습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        // 카카오톡으로 문제 보내기
        if (submenuOk == 0 && answerButton == 0) {
            if (x>btnKakaoQSending.x && x<(btnKakaoQSending.x + btnKakaoQSending.w*2) && y>btnKakaoQSending.y && y<(btnKakaoQSending.y + btnKakaoQSending.h*2))
                controlButton();
        }

        // submenu 1~8
        if (submenuOk == 1) {
            if (x>btnSub1.x && x<(btnSub1.x + btnSub1.w*2) && y>btnSub1.y && y<(btnSub1.y + btnSub1.h*2)) {
                btnSub1.btn_press();
                submenuOk = 0;
                whichSubject = "선택단어1";
                int subNumber = 1;
                makeQuestion(subNumber);
            }
        }
        if (submenuOk == 1) {
            if (x>btnSub2.x && x<(btnSub2.x + btnSub2.w*2) && y>btnSub2.y && y<(btnSub2.y + btnSub2.h*2)) {
                whichSubject = "선택단어2";
                btnSub2.btn_press();
                submenuOk = 0;
                int subNumber = 2;
                makeQuestion(subNumber);
            }
        }
        if (submenuOk == 1) {
            if (x>btnSub3.x && x<(btnSub3.x + btnSub3.w*2) && y>btnSub3.y && y<(btnSub3.y + btnSub3.h*2)) {
                whichSubject = "선택단어3";
                btnSub3.btn_press();
                submenuOk = 0;
                int subNumber = 3;
                makeQuestion(subNumber);
            }
        }
        if (submenuOk == 1) {
            if (x>btnSub4.x && x<(btnSub4.x + btnSub4.w*2) && y>btnSub4.y && y<(btnSub4.y + btnSub4.h*2)) {
                whichSubject = "선택단어4";
                btnSub3.btn_press();
                submenuOk = 0;
                int subNumber = 4;
                makeQuestion(subNumber);
            }
        }
        if (submenuOk == 1) {
            if (x>btnSub5.x && x<(btnSub5.x + btnSub5.w*2) && y>btnSub5.y && y<(btnSub5.y + btnSub5.h*2)) {
                whichSubject = "선택단어5";
                btnSub5.btn_press();
                submenuOk = 0;
                int subNumber = 3;
                makeQuestion(subNumber);
            }
        }
        if (submenuOk == 1) {
            if (x>btnSub6.x && x<(btnSub6.x + btnSub6.w*2) && y>btnSub6.y && y<(btnSub6.y + btnSub6.h*2)) {
                whichSubject = "선택단어6";
                btnSub6.btn_press();
                submenuOk = 0;
                int subNumber = 3;
                makeQuestion(subNumber);
            }
        }
        if (submenuOk == 1) {
            if (x>btnSub7.x && x<(btnSub7.x + btnSub7.w*2) && y>btnSub7.y && y<(btnSub7.y + btnSub7.h*2)) {
                whichSubject = "선택단어7";
                btnSub7.btn_press();
                submenuOk = 0;
                int subNumber = 7;
                makeQuestion(subNumber);
            }
        }
        if (submenuOk == 1) {
            if (x>btnSub8.x && x<(btnSub8.x + btnSub8.w*2) && y>btnSub8.y && y<(btnSub8.y + btnSub8.h*2)) {
                whichSubject = "선택단어8";
                btnSub8.btn_press();
                submenuOk = 0;
                int subNumber = 8;
                makeQuestion(subNumber);
            }
        }

        // left arrow button in circle
        if (dicOk == 1) {
            if (x>btnLeftArrow.x && x<(btnLeftArrow.x + btnLeftArrow.w*2) && y>btnLeftArrow.y && y<(btnLeftArrow.y + btnLeftArrow.h*2)) {
                btnLeftArrow.btn_press();
                submenuOk = 0;
                dicOk = 1;
                movePosition -= 5;
                if (movePosition<0) movePosition = 0;
            }
        }
        // right arrow button in circle
        if (dicOk == 1) {
            if (x>btnRightArrow.x && x<(btnRightArrow.x + btnRightArrow.w*2) && y>btnRightArrow.y && y<(btnRightArrow.y + btnRightArrow.h*2)) {
                btnRightArrow.btn_press();
                submenuOk = 0;
                movePosition += 5;
            }
        }

        if (dicOk == 1 || submenuOk == 1 || submenuOk2 == 1) {
            if (x>btnClose.x && x<(btnClose.x + btnClose.w*2) && y>btnClose.y && y<(btnClose.y + btnClose.h*2)) {
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();;
                }

                btnClose.btn_press();
                submenuOk = 0;
                submenuOk2 = 0;
                dicOk = 0;
            }
        }

        if (dicOk == 1) {
            if (x>btnForDictionary[0].x && x<(btnForDictionary[0].x + btnForDictionary[0].w*2) && y>btnForDictionary[0].y && y<(btnForDictionary[0].y + btnForDictionary[0].h*2)) {
                if (wordForDelete[0] != null) wordToDelete = wordForDelete[0];
            }
            if (x>btnForDictionary[1].x && x<(btnForDictionary[1].x + btnForDictionary[1].w*2) && y>btnForDictionary[1].y && y<(btnForDictionary[1].y + btnForDictionary[1].h*2)) {
                if (wordForDelete[1] != null) wordToDelete = wordForDelete[1];
            }
            if (x>btnForDictionary[2].x && x<(btnForDictionary[2].x + btnForDictionary[2].w*2) && y>btnForDictionary[2].y && y<(btnForDictionary[2].y + btnForDictionary[2].h*2)) {
                if (wordForDelete[2] != null) wordToDelete = wordForDelete[2];
            }
            if (x>btnForDictionary[3].x && x<(btnForDictionary[3].x + btnForDictionary[3].w*2) && y>btnForDictionary[3].y && y<(btnForDictionary[3].y + btnForDictionary[3].h*2)) {
                if (wordForDelete[3] != null) wordToDelete = wordForDelete[3];
            }
            if (x>btnForDictionary[4].x && x<(btnForDictionary[4].x + btnForDictionary[4].w*2) && y>btnForDictionary[4].y && y<(btnForDictionary[4].y + btnForDictionary[4].h*2)) {
                if (wordForDelete[4] != null) wordToDelete = wordForDelete[4];
            }

            SQLiteDatabase db = m_helper.getWritableDatabase();
            String sql = String.format("DELETE FROM englishWordTable WHERE eWord = '%s'", wordToDelete);
            db.execSQL(sql);

            try {
                Thread.sleep(130);
            } catch (InterruptedException e) {}

            db.close();
        }

        return gestureScanner.onTouchEvent(event);
    }

    public void controlButton() {
        ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();
        Map<String, String> metaInfoAndroid = new Hashtable<String, String>(1);
        metaInfoAndroid.put("os","android");
        metaInfoAndroid.put("devicetype","phone");
        metaInfoAndroid.put("installurl","m");
        metaInfoAndroid.put("executeurl","csc");
        Map<String, String> metaInfoIOS = new Hashtable<String, String>(1);
        metaInfoIOS.put("os", "ios");
        metaInfoIOS.put("devicetype", "phone");
        metaInfoIOS.put("installurl", "your iOS app install url");
        metaInfoIOS.put("executeurl", "");
        metaInfoArray.add(metaInfoAndroid);
        metaInfoArray.add(metaInfoIOS);
/*        KakaoLink kakaoLink = KakaoLink.getLink(mContext);
        if (!kakaoLink.isAvailableIntent())
            return;
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "친구야 영어단어문제를 풀어 보렴~"+'\n');
            intent.putExtra(Intent.EXTRA_TEXT, "(문제) " +
                    FileSplit0.questionNum[questionNumber][1] + "?" + '\n' +
                    "1번:" + FileSplit0.questionNum[questionNumber][2] + " " + '\n' +
                    "2번:" + FileSplit0.questionNum[questionNumber][3] + " " + '\n' +
                    "3번:" + FileSplit0.questionNum[questionNumber][4] + " " + '\n' +
                    "4번:" + FileSplit0.questionNum[questionNumber][5]);
            intent.setPackage("com.kakao.talk");
            mContext.startActivity(intent);
        } catch (Exception ex) {}*/
        return;
    }

    public void controlButton2() {
        ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();
        Map<String, String> metaInfoAndroid = new Hashtable<String, String>(1);
        metaInfoAndroid.put("os","android");
        metaInfoAndroid.put("devicetype","phone");
        metaInfoAndroid.put("installurl","m");
        metaInfoAndroid.put("executeurl","csc");
        Map<String, String> metaInfoIOS = new Hashtable<String, String>(1);
        metaInfoIOS.put("os", "ios");
        metaInfoIOS.put("devicetype", "phone");
        metaInfoIOS.put("installurl", "your iOS app install url");
        metaInfoIOS.put("executeurl", "");
        metaInfoArray.add(metaInfoAndroid);
        metaInfoArray.add(metaInfoIOS);
/*        KakaoLink kakaoLink = KakaoLink.getLink(mContext);
        if (!kakaoLink.isAvailableIntent())
            return;
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "정답을 보내요"+'\n');
            intent.putExtra(Intent.EXTRA_TEXT, "(정답) " +
                    FileSplit0.questionNum[questionNumber][1] + "?" + " " +
                    "" + FileSplit0.questionNum[questionNumber][6] + " " + '\n' +
                    " " + FileSplit0.questionNum[questionNumber][7] + " " + '\n' +
                    " " + FileSplit0.questionNum[questionNumber][8] + " " + '\n');
            intent.setPackage("com.kakao.talk");
            mContext.startActivity(intent);
        } catch (Exception ex) {}*/
        return;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        synchronized (mHolder) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    break;
                default:
            }
        }
        return false;
    }

    // SQLiteOpenHelper 클래스를 상속받아서 데이터베이스 생성하기
    class MyDBHelper extends SQLiteOpenHelper {
        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 테이블 생성하기
        @Override
        public void onCreate(SQLiteDatabase db) {   // 데이터베이스가 처음으로 생성될 때 호출, SQLiteDatabase 클래스의 객체를 생성하여 사용함
            db.execSQL("CREATE TABLE englishWordTable (_id INTEGER PRIMARY KEY AUTOINCREMENT," + "eWorld TEXT, kWord Text);");  // 테이블 생성하기
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS englishWordTable");
            onCreate(db);
        }
    }   // end of MyDBHelper

    @Override
    public void onGesture(GestureOverlayView arg0, MotionEvent arg1) {

    }
    @Override
    public void onGestureCancelled(GestureOverlayView arg0, MotionEvent arg1) {

    }
    @Override
    public void onGestureEnded(GestureOverlayView arg0, MotionEvent arg1) {

    }
    @Override
    public void onGestureStarted(GestureOverlayView arg0, MotionEvent arg1) {

    }
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY()-e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;

            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                questionNumber += 1;
                answerButton = 0;
            }

            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                questionNumber -= 1;
                answerButton = 0;
            }

            // down to up swipe
            else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {

            }

            // up to down swipe
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

            }
        } catch (Exception e) {

        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {

    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
} // End of SurfaceView
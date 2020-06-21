package kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.Oiler;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotA;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotABody;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotAHead;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotALeg;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotB;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotBBody;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotBHead;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotBLeg;

public class MainWorld extends GameWorld2 {
    private static final int BALL_COUNT = 10;
    public static final String PREFS_NAME = "Prefs";
    public static final String PREF_KEY_HIGHSCORE = "highscore";
    private static final String TAG = MainWorld.class.getSimpleName();

    private MediaPlayer mediaPlayer;

    private PlayState playState = PlayState.Normal;

    private RobotAHead robotAHead;
    private RobotABody robotABody;
    private RobotALeg robotALeg;

    private RobotBHead robotBHead;
    private RobotBBody robotBBody;
    private RobotBLeg robotBLeg;

    private ArrayList<RobotA> m_listRobotA;
    private ArrayList<RobotB> m_listRobotB;

    private Oiler oiler;

    private boolean m_bRobotAssembled;

    public static float planeHeight;

    public static void create() {
        if(singleton != null) {
            return;
        }
        singleton = new MainWorld();
    }

    public static MainWorld get() {
        return (MainWorld) singleton;
    }

    private enum PlayState {
        Normal, Paused, GameOver
    }

    // java는 enum이 정수와 호환 x
    public enum Layer {
        Robot,
        Oiler,
        UI,
        END
    }
    private final float m_arrRobotGenTime[] = {
            4.75f,
            7.0f,
            9.25f,
            11.5f,
            23.0625f,
            25.1875f,
            27.625f,
            29.75f,
            41.3125f,
            43.4375f,
            45.6875f,
            48.0625f,
            59.5625f,
            62.125f,
            64.375f,
            66.625f,
            66.625f,
            78.25f,
            80.4375f,
            82.625f,
            85.125f,
    };
    private int m_iRobotGentimeIndex;

    @Override
    protected void initObjects() {
        Resources res = view.getResources();

        planeHeight = 100.f;

        mediaPlayer = MediaPlayer.create(this.getContext(), R.raw.robot_bg);
        mediaPlayer.setLooping(false); // true:무한반복
        mediaPlayer.start();


//        add(Layer.BackGround, new TileScrollBackground(R.raw.earth,
//                TileScrollBackground.Orientation.vertical, 25));
//
//        add(Layer.BackGround, new ImageScrollBackground(R.mipmap.clouds,
//                ImageScrollBackground.Orientation.vertical, 100));






        m_bRobotAssembled = false;
        planeHeight = getBottom() - 200;

//        int tempX = (getRight() - getLeft()) / 2;
//        oiler = Oiler.get(tempX, getTop() - 100);
        oiler = Oiler.get(200, 200);
        add(Layer.Oiler, oiler);


        m_iRobotGentimeIndex = 0;

        m_listRobotA = new ArrayList<>();
        m_listRobotB = new ArrayList<>();

        startGame();

    }

    @Override
    protected int getLayerCount() {
        return Layer.END.ordinal();
    }

    private void startGame() {
        playState = PlayState.Normal;

//        SharedPreferences prefs = view.getContext().getSharedPreferences(PREFS_NAME,
//                Context.MODE_PRIVATE);



    }

    public void endGame() {

        playState = PlayState.GameOver;

    }

    public ArrayList<GameObject> objectsAtLayer(Layer layer) {
        return layers.get(layer.ordinal());
    }

    public void add(Layer layer, final GameObject object) {
        add(layer.ordinal(), object);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN) {
            oiler.setTouch(true);
        }
        else if(action == MotionEvent.ACTION_UP) {
            oiler.setTouch(false);
//            Log.i(TAG, "Touch : " + Float.toString(getCurrentTimeSeconds() - getStartTime()));


        }

        return true;
    }

//    @Override
//    public void addScore(int score) {
//        scoreObject.addScore(score);
//    }

    @Override
    public void update() {

        super.update();

        if(playState != PlayState.Normal) {
            return;
        }

        GenerateRobot();

        DeleteRobot();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }


    private void GenerateRobot() {

        // 배열에 저장되어있는 시간 외의 것은 삭제
        if (m_iRobotGentimeIndex >= m_arrRobotGenTime.length)
        {
            return;
        }

        // 로봇 부품의 위치가 맞다면
        if (m_bRobotAssembled == true
        )
        {
            // 부품을 제거할 것이므로 false
            m_bRobotAssembled = false;


            float x = 0;
            float y = 0;

//            if (robotABody != null)

//                if (0 <= m_iRobotGentimeIndex - 1
//                        && m_iRobotGentimeIndex  - 1<= 15)
                if (robotABody != null) {
                    x = robotABody.getPosX();
                    y = robotABody.getPosY();

                    RobotA tempR = RobotA.get(x, y);
                    m_listRobotA.add(tempR);
                    this.add(Layer.Robot, tempR);

                    robotALeg.setPosX(-10000);
                    robotABody.setPosX(-10000);
                    robotAHead.setPosX(-10000);

                    if (robotBBody != null)
                        robotBBody.setPosX(-10000);
                    if (robotBHead != null)
                        robotBHead.setPosX(-10000);
                    if (robotBLeg != null)
                        robotBLeg.setPosX(-10000);


                    remove(robotALeg);
                    remove(robotABody);
                    remove(robotAHead);
                    remove(robotALeg);
                    remove(robotABody);
                    remove(robotAHead);

                    robotALeg = null;
                    robotABody = null;
                    robotAHead = null;

                }
//            else if (16 <= m_iRobotGentimeIndex - 1
//                        && m_iRobotGentimeIndex  - 1<= m_arrRobotGenTime.length - 1)
            else if (robotBBody != null)
            {
                x = robotBBody.getPosX();
                y = robotBBody.getPosY();

                RobotB tempR = RobotB.get(x, y);
                m_listRobotB.add(tempR);
                add(Layer.Robot, tempR);


                robotBLeg.setPosX(-10000);
                robotBBody.setPosX(-10000);
                robotBHead.setPosX(-10000);

                this.remove(robotBLeg);
                this.remove(robotBBody);
                this.remove(robotBHead);

                robotBLeg = null;
                robotBBody = null;
                robotBHead = null;

            }

        }


        float tempF = getCurrentTimeSeconds() - getStartTime();
        if (tempF >= m_arrRobotGenTime[m_iRobotGentimeIndex] - 3.0f)
        {
            ++m_iRobotGentimeIndex;


//            Log.i(TAG, Float.toString(tempF));

            //            Log.i(TAG, Integer.toString(m_iRobotGentimeIndex));

            if (0 <= m_iRobotGentimeIndex
                && m_iRobotGentimeIndex <= 15)
            {
                remove(robotAHead);
                robotAHead = RobotAHead.get(getRight() - 100, -1600);
                add(Layer.Robot, robotAHead);

                remove(robotABody);
                robotABody = RobotABody.get(getRight() - 100, -800);
                add(Layer.Robot, robotABody);

                remove(robotALeg);
                robotALeg = RobotALeg.get(getRight() - 100, 0);
                add(Layer.Robot, robotALeg);
            }
            else if (16 <= m_iRobotGentimeIndex
                    && m_iRobotGentimeIndex <= m_arrRobotGenTime.length)
            {
                remove(robotBHead);
                robotBHead = RobotBHead.get(getRight() - 100, -1600);
                add(Layer.Robot, robotBHead);

                remove(robotBBody);
                robotBBody = RobotBBody.get(getRight() - 100, -800);
                add(Layer.Robot, robotBBody);

                remove(robotBLeg);
                robotBLeg = RobotBLeg.get(getRight() - 100, 0);
                add(Layer.Robot, robotBLeg);
            }

//            Log.i(TAG, Integer.toString(m_iRobotGentimeIndex));


        }


        if (robotAHead != null)
        {
            if (robotAHead.isAssembled() == true
            && m_bRobotAssembled == false)
            {
                m_bRobotAssembled = true;
//                robotAHead.setAssembled(false);


            }
        }

        if (robotBHead != null)
        {
            if (robotBHead.isAssembled() == true
                    && m_bRobotAssembled == false)
            {
                m_bRobotAssembled = true;
//                robotBHead.setAssembled(false);


            }
        }


    }

    public void DeleteRobot() {
        RectF tempR = new RectF();
        for (RobotA r : m_listRobotA) {
            if (r == null) {
                continue;
            }
            r.getBox(tempR);
            if (tempR.right < getLeft()) {
                this.remove(r);
                this.remove(r);
                m_listRobotA.remove(r);
                break;
            }
        }

        for (RobotB r : m_listRobotB) {
            if (r == null) {
                continue;
            }
            r.getBox(tempR);
            if (tempR.right < getLeft()) {
                this.remove(r);
                this.remove(r);
                m_listRobotB.remove(r);
                break;
            }
        }

    }


}

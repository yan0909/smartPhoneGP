package kr.ac.kpu.game.andgp.kse.RhythmGame.game.scene;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameScene;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.MainWorld;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.BitmapObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.Oiler;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotA;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotABody;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotAHead;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotALeg;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotB;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotBBody;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotBHead;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.RobotBLeg;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.util.CollisionHelper;
import kr.ac.kpu.game.andgp.kse.RhythmGame.ui.activity.GameActivity;


public class Robot extends GameScene {
    private static final String TAG = MainWorld.class.getSimpleName();

    private MediaPlayer mediaPlayer;

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

    private boolean m_bGameEnd;
    private GameTimer timer;


    public Robot() {
    }


    public enum Layer {
        bg,
        Robot,
        Oiler,
        UI,
        END
    }


    @Override
    public void enter() {
        super.enter();
        initObjects();
    }
    @Override
    public void exit() {
        mediaPlayer.release();
        super.exit();
    }

    private final float m_arrRobotGenTime[] = {
            4.75f,
            11.55f,
            18.42f,
            22.96f,
            29.81f,
            36.61f,
            43.41f,
            50.21f,
            57.01f,
            63.81f,
            70.61f,
            77.41f,
            84.21f,
//            62.125f,
//            64.375f,
//            66.625f,
//            66.625f,
//            78.25f,
//            80.4375f,
//            82.625f,
//            85.125f,
    };
    private int m_iRobotGentimeIndex;

    public void initObjects() {
        Random random = new Random();

        planeHeight = 100.f;

        mediaPlayer = MediaPlayer.create(GameActivity.instance, R.raw.robot_bg);
        GameTimer.setStartTime();

        timer = new GameTimer(1000, 1000);

        mediaPlayer.setLooping(false); // true:무한반복
        mediaPlayer.start();


//        add(Layer.BackGround, new TileScrollBackground(R.raw.earth,
//                TileScrollBackground.Orientation.vertical, 25));
//
//        add(Layer.BackGround, new ImageScrollBackground(R.mipmap.clouds,
//                ImageScrollBackground.Orientation.vertical, 100));


        m_bRobotAssembled = false;
        planeHeight = UiBridge.metrics.size.y - 200;

//        int tempX = (getRight() - getLeft()) / 2;
//        oiler = Oiler.get(tempX, getTop() - 100);
        oiler = Oiler.get(200, 200);
        gameWorld.add(Layer.Oiler.ordinal(), oiler);
        gameWorld.add(Layer.bg.ordinal(), new BitmapObject(UiBridge.metrics.size.x / 2, UiBridge.metrics.size.y / 2, UiBridge.metrics.size.x, UiBridge.metrics.size.y, R.mipmap.robot_bg));


        m_iRobotGentimeIndex = 0;

        m_listRobotA = new ArrayList<>();
        m_listRobotB = new ArrayList<>();

        m_bGameEnd = false;

    }

    @Override
    protected int getLayerCount() {
        return Layer.END.ordinal();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            oiler.setTouch(true);
        } else if (action == MotionEvent.ACTION_UP) {
            oiler.setTouch(false);
//            Log.i(TAG, "Touch : " + Float.toString(GameTimer.getCurrentTimeNanos() - GameTimer.getStartTime()));


        }

        return true;
    }


    public void update() {
        super.update();


        // Robot과 Oiler의 충돌체크
        ArrayList<GameObject> robots = gameWorld.objectsAtLayer(Layer.Robot.ordinal());
        ArrayList<GameObject> oilers = gameWorld.objectsAtLayer(Layer.Oiler.ordinal());
        Oiler tempOiler = null;

        for (GameObject r : oilers) {
            tempOiler = (Oiler) r;
        }
        for (GameObject r : robots) {
            if (r instanceof RobotA)
            {
                RobotA robotA = (RobotA) r;
                if (CollisionHelper.collides(robotA, tempOiler)) {
                    robotA.setDocked(true);
                    robotA.setPos(tempOiler.getHeadPosX(), robotA.getPosY());
                } else {
                    robotA.setDocked(false);
                }
                break;
            }
            else if (r instanceof RobotB)
            {
                RobotB robotB = (RobotB) r;
                if (CollisionHelper.collides(robotB, tempOiler)) {
                    robotB.setDocked(true);
                    robotB.setPos(tempOiler.getHeadPosX(), robotB.getPosY());
                } else {
                    robotB.setDocked(false);
                }
                break;
            }
        }


        GenerateRobot();

        DeleteRobot();

        if(timer.getRawIndex() >= 92000)
            pop();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }


    private void GenerateRobot() {

        // 배열에 저장되어있는 시간 외의 것은 삭제
        if (m_iRobotGentimeIndex >= m_arrRobotGenTime.length) {
            return;
        }

        // 로봇 부품의 위치가 맞다면
        if (m_bRobotAssembled == true) {
            // 부품을 제거할 것이므로 false
            m_bRobotAssembled = false;


            float x = 0;
            float y = 0;

            if (robotABody != null)
            {
                x = robotABody.getPosX();
                y = robotABody.getPosY();

                RobotA tempR = RobotA.get(x, y);
                m_listRobotA.add(tempR);
                gameWorld.add(Layer.Robot.ordinal(), tempR);
                Log.i(TAG, "ROBOT GEN ::: ROBOT GEN ::: " + Float.toString(GameTimer.getRealCurrentTimeSeconds()));

                robotALeg.remove();
                robotABody.remove();
                robotAHead.remove();

                robotALeg = null;
                robotABody = null;
                robotAHead = null;

            }
            else if (robotBBody != null) {
                x = robotBBody.getPosX();
                y = robotBBody.getPosY();

                RobotB tempR = RobotB.get(x, y);
                m_listRobotB.add(tempR);
                gameWorld.add(Layer.Robot.ordinal(), tempR);


                robotBLeg.remove();
                robotBBody.remove();
                robotBHead.remove();
                robotBLeg = null;
                robotBBody = null;
                robotBHead = null;

            }

        }


        // 부품 생성
        float tempF = GameTimer.getCurrentTimeSeconds() - GameTimer.getStartTime();
        if (tempF >= m_arrRobotGenTime[m_iRobotGentimeIndex] - 2.f)
        {
            ++m_iRobotGentimeIndex;

            int screenWidth = UiBridge.metrics.size.x;
            int screenHeight = UiBridge.metrics.size.y;

//            Log.i(TAG, Float.toString(tempF));


            if (0 <= m_iRobotGentimeIndex
                    && m_iRobotGentimeIndex <= 15)
            {
                if (robotAHead != null)
                    return;
//                    robotAHead.remove();
                robotAHead = RobotAHead.get(screenWidth - 100, -1600);
                gameWorld.add(Layer.Robot.ordinal(), robotAHead);

                if (robotABody != null) {
                    return;
//                    robotABody.remove();
                }

                robotABody = RobotABody.get(screenWidth - 100, -800);
                gameWorld.add(Layer.Robot.ordinal(), robotABody);

                if (robotALeg != null) {
                    return;
//                    robotALeg.remove();
                }
                robotALeg = RobotALeg.get(screenWidth - 100, 0);
                gameWorld.add(Layer.Robot.ordinal(), robotALeg);
            }
            else if (16 <= m_iRobotGentimeIndex
                    && m_iRobotGentimeIndex <= m_arrRobotGenTime.length)
            {
                if (robotBHead != null)
                    robotBHead.remove();
                robotBHead = RobotBHead.get(screenWidth - 100, -1600);
                gameWorld.add(Layer.Robot.ordinal(), robotBHead);

                if (robotBBody != null)
                    robotBBody.remove();
                robotBBody = RobotBBody.get(screenWidth - 100, -800);
                gameWorld.add(Layer.Robot.ordinal(), robotBBody);

                if (robotBLeg != null)
                    robotBLeg.remove();
                robotBLeg = RobotBLeg.get(screenWidth - 100, 0);
                gameWorld.add(Layer.Robot.ordinal(), robotBLeg);
            }

//            Log.i(TAG, Integer.toString(m_iRobotGentimeIndex));



        }

        if (robotAHead != null) {
//            if (robotAHead.isAssembled() == true
//                    && m_bRobotAssembled == false)
            if (robotAHead.isAssembled() == true)
            {
                m_bRobotAssembled = true;
                robotAHead.setAssembled(false);
            }
        }

        if (robotBHead != null) {
//            if (robotBHead.isAssembled() == true
//                    && m_bRobotAssembled == false) {
            if (robotBHead.isAssembled() == true)
            {
                m_bRobotAssembled = true;
                robotBHead.setAssembled(false);
            }
        }
    }
    public void DeleteRobot()
    {
        RectF tempR = new RectF();
        for (RobotA r : m_listRobotA) {
            if (r == null) {
                continue;
            }
            r.getBox(tempR);
            if (tempR.right < 0) {
                r.remove();
                m_listRobotA.remove(r);
                break;
            }
        }

        for (RobotB r : m_listRobotB) {
            if (r == null) {
                continue;
            }
            r.getBox(tempR);
            if (tempR.right < 0) {
                r.remove();
                m_listRobotB.remove(r);

                m_bGameEnd = true;

                mediaPlayer.stop();
                pop();

                break;
            }
        }

    }


}

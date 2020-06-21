package kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.BoxCollidable;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.Recyclable;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameWorld2;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.MainWorld;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.sound.SoundEffects;

public class RobotA extends GameObject implements Recyclable, BoxCollidable {
    public static final float MOVING_SPEED = 600;
    public static final float SUCCESS_MOVING_SPEED = 800;
    public static final float FALLING_SPEED = 1500;

    private static final int FRAME_COUNT = 1;
    private static final int FRAME_PER_SECOND = 6;

    private static final String TAG = RobotA.class.getSimpleName();


    private static float HEIGHT;

    private final float CHARGE_TERM = 0.3f;

    private int halfSize;
    private FrameAnimationBitmap fab_idle;
    private FrameAnimationBitmap fab_success;
    private FrameAnimationBitmap fab_charging0;
    private FrameAnimationBitmap fab_charging1;
    private FrameAnimationBitmap fab_charging2;
    private FrameAnimationBitmap fab_charging3;
    private float x;
    private float y;

    private boolean m_bIsDocked;

    private float m_fFirstDockedTime;


    private enum State {
        Idle, Charging0, Charging1, Charging2, Charging3, Success
    }
    private State m_eState;


    private float m_fLastChargeTime;





    /////////////////////////////////




    private RobotA() {}




    public static RobotA get(float x, float y)
    {
//        GameWorld2 gw = GameWorld2.get();
//        RobotA robotA = (RobotA)gw.getRecyclePool().get(RobotA.class);
//        if(robotA == null) {
//            robotA = new RobotA();
//        }

        RobotA robotA = new RobotA();
        robotA.fab_idle = new FrameAnimationBitmap(R.mipmap.robot_obja_idle2, FRAME_PER_SECOND, FRAME_COUNT);
        robotA.fab_success = new FrameAnimationBitmap(R.mipmap.robot_obja_success, FRAME_PER_SECOND, FRAME_COUNT);
        robotA.fab_charging0 = new FrameAnimationBitmap(R.mipmap.robot_obja_charging0, FRAME_PER_SECOND, FRAME_COUNT);
        robotA.fab_charging1 = new FrameAnimationBitmap(R.mipmap.robot_obja_charging1, FRAME_PER_SECOND, FRAME_COUNT);
        robotA.fab_charging2 = new FrameAnimationBitmap(R.mipmap.robot_obja_charging2, FRAME_PER_SECOND, FRAME_COUNT);
        robotA.fab_charging3 = new FrameAnimationBitmap(R.mipmap.robot_obja_charging3, FRAME_PER_SECOND, FRAME_COUNT);
        robotA.halfSize = robotA.fab_idle.getHeight() / 2;
        HEIGHT = robotA.fab_idle.getHeight();
        robotA.x = x;
        robotA.y = y;
        robotA.m_bIsDocked = false;
        robotA.m_eState = State.Idle;
        return robotA;
    }



    @Override
    public void update()
    {


        if (m_bIsDocked)
        {
            // 충전 스택 쌓기
            if (GameTimer.getRealCurrentTimeSeconds() - m_fLastChargeTime >= CHARGE_TERM) {
                m_fLastChargeTime = GameTimer.getRealCurrentTimeSeconds();
                if (m_eState == State.Charging0) {
                    m_eState = State.Charging1;
                }
                else if (m_eState == State.Charging1) {
                    m_eState = State.Charging2;
                    SoundEffects.get().play(R.raw.robot_charging_c);
                }
                else if (m_eState == State.Charging2) {
                    m_eState = State.Charging3;
                }
                else if (m_eState == State.Charging3) {

                    m_eState = State.Success;
                }
            }
        }
        else {
            if (m_eState == State.Success) {
                x -= SUCCESS_MOVING_SPEED * GameTimer.getTimeDiffSeconds();
            }
            else {
                x -= MOVING_SPEED * GameTimer.getTimeDiffSeconds();
            }
        }
//        Log.d(TAG, "Check" + Long.toString(gw.getCurrentTimeNanos()));





    }

    @Override
    public void draw(Canvas canvas) {
        if (m_eState == State.Idle) {
            fab_idle.draw(canvas, x, y);
        }
        else if (m_eState == State.Charging0) {
            fab_charging0.draw(canvas, x, y);
        }
        else if (m_eState == State.Charging1) {
            fab_charging1.draw(canvas, x, y);
        }
        else if (m_eState == State.Charging2) {
            fab_charging2.draw(canvas, x, y);
        }
        else if (m_eState == State.Charging3) {
            fab_charging3.draw(canvas, x, y);
        }
        else if (m_eState == State.Success) {
            fab_success.draw(canvas, x, y);
        }

    }

    public void getBox(RectF rect) {
        int hw = fab_idle.getWidth() / 2 - 30;
        int hh = fab_idle.getHeight() / 2;
        rect.left = x - hw;
        rect.right = x + hw;
        rect.top = y - hh;
        rect.bottom = y + hh;

    }

    @Override
    public void recycle() {

    }

    public static float getHeight()
    {
        return HEIGHT;
    }


    public void setPos(float _x, float _y)
    {
        x = _x;
        y = _y;
    }

    public float getPosX()
    {
        return x;
    }
    public float getPosY()
    {
        return y;
    }

    public void setPosX(float _x) {
        x = _x;
    }
    public void setPosY(float _y) {
        y = _y;
    }


    public void setDocked(boolean _b) {
        if (!m_bIsDocked && _b) {
            setFirstDockedTime();
            m_eState = State.Charging0;
        }


        m_bIsDocked = _b;
    }

    private void setFirstDockedTime() {
        m_fFirstDockedTime = GameTimer.getRealCurrentTimeSeconds();
        m_fLastChargeTime = m_fFirstDockedTime;
    }


}

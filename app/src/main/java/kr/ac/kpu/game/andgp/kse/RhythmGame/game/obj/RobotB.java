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
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.sound.SoundEffects;

public class RobotB extends GameObject implements Recyclable, BoxCollidable {
    public static final float MOVING_SPEED = 700;
    public static final float SUCCESS_MOVING_SPEED = 900;
    public static final float FALLING_SPEED = 1500;

    private static final int FRAME_COUNT = 1;
    private static final int FRAME_PER_SECOND = 6;

    private static final String TAG = RobotB.class.getSimpleName();


    private static float HEIGHT;

    private final float CHARGE_TERM = 0.15f;

    private int halfSize;
    private FrameAnimationBitmap fab_idle;
    private FrameAnimationBitmap fab_success;
    private FrameAnimationBitmap fab_charging0;
    private FrameAnimationBitmap fab_charging1;
    private FrameAnimationBitmap fab_charging2;
    private FrameAnimationBitmap fab_charging3;
    private FrameAnimationBitmap fab_charging4;
    private FrameAnimationBitmap fab_charging5;
    private FrameAnimationBitmap fab_charging6;
    private FrameAnimationBitmap fab_charging7;
    private float x;
    private float y;

    private boolean m_bIsDocked;

    private float m_fFirstDockedTime;


    private enum State {
        Idle, Charging0, Charging1, Charging2, Charging3, Charging4, Charging5, Charging6, Charging7,Success
    }
    private State m_eState;


    private float m_fLastChargeTime;





    /////////////////////////////////




    private RobotB() {}




    public static RobotB get(float x, float y)
    {
        RobotB robotB = new RobotB();


        robotB.fab_idle = new FrameAnimationBitmap(R.mipmap.robot_objb_idle, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.fab_success = new FrameAnimationBitmap(R.mipmap.robot_objb_success, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.fab_charging0 = new FrameAnimationBitmap(R.mipmap.robot_objb_charging0, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.fab_charging1 = new FrameAnimationBitmap(R.mipmap.robot_objb_charging1, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.fab_charging2 = new FrameAnimationBitmap(R.mipmap.robot_objb_charging2, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.fab_charging3 = new FrameAnimationBitmap(R.mipmap.robot_objb_charging3, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.fab_charging4 = new FrameAnimationBitmap(R.mipmap.robot_objb_charging4, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.fab_charging5 = new FrameAnimationBitmap(R.mipmap.robot_objb_charging5, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.fab_charging6 = new FrameAnimationBitmap(R.mipmap.robot_objb_charging6, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.fab_charging7 = new FrameAnimationBitmap(R.mipmap.robot_objb_charging7, FRAME_PER_SECOND, FRAME_COUNT);
        robotB.halfSize = robotB.fab_idle.getHeight() / 2;
        HEIGHT = robotB.fab_idle.getHeight();
        robotB.x = x;
        robotB.y = y;
        robotB.m_bIsDocked = false;
        robotB.m_eState = State.Idle;
        return robotB;
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
                    SoundEffects.get().play(R.raw.robot_charging_a);
                }
                else if (m_eState == State.Charging1) {
                    m_eState = State.Charging2;
                }
                else if (m_eState == State.Charging2) {
                    m_eState = State.Charging3;
                }
                else if (m_eState == State.Charging3) {
                    m_eState = State.Charging4;
                }
                else if (m_eState == State.Charging4) {
                    m_eState = State.Charging5;
                }
                else if (m_eState == State.Charging5) {
                    m_eState = State.Charging6;
                }
                else if (m_eState == State.Charging6) {
                    m_eState = State.Charging7;
                }
                else if (m_eState == State.Charging7) {
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
        else if (m_eState == State.Charging4) {
            fab_charging4.draw(canvas, x, y);
        }
        else if (m_eState == State.Charging5) {
            fab_charging5.draw(canvas, x, y);
        }
        else if (m_eState == State.Charging6) {
            fab_charging6.draw(canvas, x, y);
        }
        else if (m_eState == State.Charging7) {
            fab_charging7.draw(canvas, x, y);
        }

        else if (m_eState == State.Success) {
            fab_success.draw(canvas, x, y);
        }

    }

    public void getBox(RectF rect) {
        int hw = fab_idle.getWidth() / 2 - 100;
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

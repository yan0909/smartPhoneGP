package kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.BoxCollidable;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameWorld;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameWorld2;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.MainWorld;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.util.CollisionHelper;


public class Oiler extends GameObject implements BoxCollidable {
    private static final int FRAME_COUNT = 1;
    private static final int HEAD_FRAME_COUNT = 2;
    private static final int FRAME_PER_SECOND = 6;
    private static final int OILER_HEAD_FALLING_SPEED = 500;
    private static final int OILER_HEAD_RISING_SPEED = 100;

    private FrameAnimationBitmap fab_oiler_body1;
    private FrameAnimationBitmap fab_oiler_body2;
    private FrameAnimationBitmap fab_oiler_head_idle;
    private FrameAnimationBitmap fab_oiler_head_docking;
    private float x;
    private float y;

    private float oiler_body2_x;
    private float oiler_body2_y;
    private float oiler_head_x;
    private float oiler_head_y;

    private float oiler_body2_length;
    private float oiler_body2_min_length;
    private float oiler_body2_max_length;




    private boolean m_bIsTouching;
    private boolean m_bIsDocking;


    private Oiler() {}


    public static Oiler get(float x, float y)
    {

        Oiler oiler = new Oiler();

        oiler.fab_oiler_body1 = new FrameAnimationBitmap(R.mipmap.oiler_body1, FRAME_PER_SECOND, FRAME_COUNT);
        oiler.fab_oiler_body2 = new FrameAnimationBitmap(R.mipmap.oiler_body2, FRAME_PER_SECOND, FRAME_COUNT);
        oiler.fab_oiler_head_idle = new FrameAnimationBitmap(R.mipmap.oiler_head_idle, FRAME_PER_SECOND, FRAME_COUNT);
        oiler.fab_oiler_head_docking = new FrameAnimationBitmap(R.mipmap.oiler_head, FRAME_PER_SECOND, HEAD_FRAME_COUNT);
        oiler.x = x;
        oiler.y = y;

        oiler.oiler_body2_max_length = oiler.fab_oiler_body2.getHeight();
        oiler.oiler_body2_min_length = oiler.fab_oiler_body2.getHeight() / 10;
        oiler.oiler_body2_length = oiler.oiler_body2_min_length;


        oiler.oiler_body2_x =  x + oiler.fab_oiler_body1.getWidth() / 2- oiler.fab_oiler_body2.getWidth() / 2;
        oiler.oiler_body2_y = y - oiler.fab_oiler_body2.getHeight() / 2
                            + oiler.fab_oiler_body2.getHeight() / 2;

        oiler.oiler_head_x =  oiler.oiler_body2_x;

        // 재사용
        oiler.fab_oiler_body2.setHeight((int)oiler.oiler_body2_length);
        oiler.oiler_body2_y = oiler.y
                                +  oiler.fab_oiler_body2.getHeight() / 2;
        oiler.oiler_head_y = oiler.oiler_body2_y
                            + oiler.oiler_body2_length / 2
                            + oiler.fab_oiler_head_idle.getHeight() / 2;

        oiler.m_bIsTouching = false;
        oiler.m_bIsDocking = false;

        return oiler;
    }



    @Override
    public void update()
    {

        // 터치 중
        if (m_bIsTouching) {
            // 로봇과 연결된 상태
            if (m_bIsDocking) {

            }
            // 로봇에 닿지 않은 상태 (로봇에 다가가는중)
            else {
                if (oiler_body2_length < oiler_body2_max_length) {
                    oiler_body2_length = oiler_body2_length + OILER_HEAD_FALLING_SPEED;
                }
                // 로봇에 연결된 상태이나 더 길어져 뚫고 들어가는 것을 방지
                // 로봇에 닿았다면 도킹 상태를 True
                if (oiler_body2_length >= oiler_body2_max_length) {
                    m_bIsDocking = true;
                    oiler_body2_length = oiler_body2_max_length;
                }
            }
        }
        // 터치를 뗀 상태
        else {
            m_bIsDocking = false;
            // 로봇에게서 멀어지는 중
            if (oiler_body2_length > oiler_body2_min_length) {
                oiler_body2_length = oiler_body2_length - OILER_HEAD_RISING_SPEED;
            }
            // 오일러의 최소 길이보다 더 짧아지는 것을 방지
            else {
                oiler_body2_length = oiler_body2_min_length;
            }
        }


        // body2와 head의 위치 계산
        // 재사용
        fab_oiler_body2.setHeight((int)oiler_body2_length);
        oiler_body2_y = y
//                + fab_oiler_body1.getHeight() / 2
                +  fab_oiler_body2.getHeight() / 2;
        oiler_head_y = oiler_body2_y
                + oiler_body2_length / 2
                + fab_oiler_head_idle.getHeight() / 2;




//        // Robot과 Oiler의 충돌체크
//        ArrayList<GameObject> robots = gw.objectsAtLayer(MainWorld.Layer.Robot);
//
//        for(GameObject r : robots) {
//            if(r instanceof RobotA) {
//                RobotA robotA = (RobotA) r;
//                if(CollisionHelper.collides(robotA, this))
//                {
////                    if (this.getPosX() - 10 < robotA.getPosX()
////                        && this.getPosX() + 10 > robotA.getPosX())
////                    {
////                        robotA.setDocked(true);
////                    }
//
//                    robotA.setDocked(true);
//                    robotA.setPos(this.getHeadPosX(), robotA.getPosY());
//                }
//                else {
//                    robotA.setDocked(false);
//                }
//                break;
//            }
//            else if(r instanceof RobotB) {
//                RobotB robotB = (RobotB) r;
//                if(CollisionHelper.collides(robotB, this))
//                {
////                    if (this.getPosX() - 10 < robotA.getPosX()
////                        && this.getPosX() + 10 > robotA.getPosX())
////                    {
////                        robotA.setDocked(true);
////                    }
//
//                    robotB.setDocked(true);
//                    robotB.setPos(this.getHeadPosX(), robotB.getPosY());
//                }
//                else {
//                    robotB.setDocked(false);
//                }
//                break;
//            }
//        }


    }

    @Override
    public void draw(Canvas canvas) {
        fab_oiler_body1.draw(canvas, x, y);
        fab_oiler_body2.draw(canvas, oiler_body2_x, oiler_body2_y);
        if (m_bIsDocking) {
            fab_oiler_head_docking.draw(canvas, oiler_head_x, oiler_head_y);
        }
        else {
            fab_oiler_head_idle.draw(canvas, oiler_head_x, oiler_head_y);
        }
    }

    @Override
    public void getBox(RectF rect) {
//        int hw = fab_oiler_body1.getWidth() / 2;
//        int hh = (fab_oiler_body1.getHeight()
//                + fab_oiler_body2.getHeight()
//                + fab_oiler_head_idle.getHeight()) / 2;
//        rect.left = x - hw;
//        rect.right = x + hw;
//        rect.top = y - hh;
//        rect.bottom = y + hh;

        int hw = fab_oiler_head_idle.getWidth() / 2;
        int hh = fab_oiler_head_idle.getHeight() / 2;
        rect.left = oiler_head_x - hw;
        rect.right = oiler_head_x + hw;
        rect.top = oiler_head_y - hh;
        rect.bottom = oiler_head_y + hh;

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

    public void setTouch(boolean _b) {
        m_bIsTouching = _b;
    }

    public float getHeadPosX() {
        return oiler_head_x;
    }
    public float getHeadPosY() {
        return oiler_head_y;
    }

}

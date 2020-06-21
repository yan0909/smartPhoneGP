package kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.Recyclable;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameWorld;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameWorld2;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.MainWorld;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.bitmap.FrameAnimationBitmap;


public class RobotBLeg extends GameObject implements Recyclable {
    private static final int FRAME_COUNT = 1;
    private static final int FRAME_PER_SECOND = 6;
    private int halfSize;
    private FrameAnimationBitmap fab;
    private float x;
    private float y;
    private static float HEIGHT;
    private boolean isAssembled;

    private RobotBLeg() {}


    public static RobotBLeg get(float x, float y)
    {
//        GameWorld2 gw = GameWorld2.get();
//        RobotBLeg robotHead = (RobotBLeg)gw.getRecyclePool().get(RobotBLeg.class);
//        if(robotHead == null) {
//            robotHead = new RobotBLeg();
//        }
        RobotBLeg robotHead = new RobotBLeg();

        robotHead.fab = new FrameAnimationBitmap(R.mipmap.robot_objb_legs, FRAME_PER_SECOND, FRAME_COUNT);
        robotHead.halfSize = robotHead.fab.getHeight() / 2;
        robotHead.x = x;
        robotHead.y = y;
        robotHead.isAssembled = false;
        HEIGHT = robotHead.fab.getHeight();

        return robotHead;
    }



    @Override
    public void update()
    {
        int screenWidth = UiBridge.metrics.size.x;
        int screenHeight = UiBridge.metrics.size.y;

        int hh = fab.getHeight() / 2;

        if (y + hh > screenHeight)
        {
            isAssembled = true;
            return;
        }


//        x += dx;
//        if(dx > 0 && x > gw.getRight() - halfSize || dx < 0 && x < gw.getLeft() + halfSize)
//        {
//            dx *= -1;
//        }


        y += RobotA.FALLING_SPEED * GameTimer.getTimeDiffSeconds();

    }

    @Override
    public void draw(Canvas canvas) {
        fab.draw(canvas, x, y);
    }

    public void getBox(RectF rect) {
        int hw = fab.getWidth() / 2;
        int hh = fab.getHeight() / 2;
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

    public boolean IsAssembled() {
        return isAssembled;
    }
}

package kr.ac.kpu.game.andgp.kse.RhythmGame.game.util;

import android.graphics.RectF;

import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.BoxCollidable;

public class CollisionHelper {
    private static RectF r1 = new RectF();
    private static RectF r2 = new RectF();

    public static boolean collides(BoxCollidable o1, BoxCollidable o2) {
        o1.getBox(r1);
        o2.getBox(r2);

        if(r1.left > r2.right)
            return false;
        if(r1.right < r2.left)
            return false;

        if(r1.top > r2.bottom)
            return false;
        if(r1.bottom < r2.top)
            return false;

        return true;
    }
}

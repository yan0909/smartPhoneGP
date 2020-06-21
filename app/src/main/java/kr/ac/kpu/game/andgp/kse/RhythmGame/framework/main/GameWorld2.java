package kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.Recyclable;


public abstract class GameWorld2 {
    protected View view;
    protected long frameTimeNanos;
    protected long timeDiffNanos;
    protected RecyclePool recyclePool = new RecyclePool();


    private float m_fStartTime;
    private boolean m_bWriteStartTime;
    //    private int scoreValue;
//    private int scoreDisplay;
//    private Paint scorePaint = new Paint();
//    private ObjectAnimator scoreAnimator;

//    public void setScoreDisplay(int scoreDisplay) {
//        this.scoreDisplay = scoreDisplay;
//    }

    public static GameWorld2 get()
    {
        if(singleton == null)
        {
            //singleton = new GameWorld();
            // Log.e
        }
        return singleton;
    }
    protected static GameWorld2 singleton;

    //private ArrayList<GameObject> objects;
    protected Rect rect;

    protected GameWorld2() {}

    public RecyclePool getRecyclePool() {
        return this.recyclePool;
    }

    public ArrayList<GameObject> objectsAtLayer(int index) {
        return layers.get(index);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void addScore(int score) {
    }

    public void initResources(View view) {
        this.view = view;

        initLayers();
        initObjects();
    }

    protected void initLayers() {
        layers = new ArrayList<>();
        int layerCount = getLayerCount();
        for(int i = 0; i < layerCount; ++i) {
            ArrayList<GameObject> layer = new ArrayList<>();
            layers.add(layer);
        }
    }

    abstract protected int getLayerCount();

    protected void initObjects() {
        m_bWriteStartTime = false;
    }

    protected ArrayList<ArrayList<GameObject>> layers;

    public void draw(Canvas canvas) {
        for(ArrayList<GameObject> layer : layers) {
            for(GameObject o : layer) {
                o.draw(canvas);
            }
        }
//        canvas.drawText("Score : " + scoreDisplay, 100, 100, scorePaint);
    }

    public long getTimeDiffNanos() {
        return GameTimer.getCurrentTimeNanos();
    }
    public float getTimeDiffInSecond() {
        return (float)(GameTimer.getCurrentTimeNanos() / 1000000000.0);
    }
    public long getCurrentTimeNanos() {
        return GameTimer.getCurrentTimeNanos();
    }
    public float getCurrentTimeSeconds() {
        return (float)(GameTimer.getCurrentTimeNanos() / 1000000000.0);
    }
    public float getRealCurrentTimeSeconds() {
        return (float)(frameTimeNanos / 1000000000.0) - m_fStartTime;
    }


    public void update() {
        this.frameTimeNanos = GameTimer.getCurrentTimeNanos();

        this.timeDiffNanos = GameTimer.getTimeDiffNanos();;
        if(rect == null) {
            return;
        }

        for(ArrayList<GameObject> layer : layers) {
            for(GameObject o : layer) {
                o.update();
            }
        }

//        objects.removeAll(trash);
        if(trash.size() > 0) {
            removeTrashObjects();
        }
        trash.clear();

        if (m_bWriteStartTime == false) {
            m_bWriteStartTime = true;
            m_fStartTime = getCurrentTimeSeconds();
        }
    }

    protected void removeTrashObjects() {
        for(int tIndex = trash.size() - 1; tIndex > 0; tIndex--) {
            GameObject tObj = trash.get(tIndex);
            for(ArrayList<GameObject> objects : layers) {
                int index = objects.indexOf(tObj);
                if(index >= 0) {
                    objects.remove(index);
                }
            }
            trash.remove(tIndex);
            if(tObj instanceof Recyclable) {
                ((Recyclable) tObj).recycle();
                getRecyclePool().add(tObj);
            }
        }
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public int getLeft() {
        return rect.left;
    }

    public int getRight() {
        return rect.right;
    }

    public int getTop() {
        return rect.top;
    }

    public int getBottom() {
        return rect.bottom;
    }

    public void doAction() {

    }

    public Resources getResources() {
        return view.getResources();
    }

    public Context getContext()
    {
        return view.getContext();
    }

    public void add(final int index, final GameObject object) {
        view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(index);
                objects.add(object);
            }
        });
    }

    protected ArrayList<GameObject> trash = new ArrayList<>();
    public void remove(GameObject object) {
        trash.add(object);
    }

    public void pause() {

    }

    public void resume() {

    }

    public float getStartTime() {
        return m_fStartTime;
    }
}

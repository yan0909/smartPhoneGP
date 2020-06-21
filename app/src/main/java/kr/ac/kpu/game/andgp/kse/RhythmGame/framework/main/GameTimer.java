package kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main;

public class GameTimer {
    private static final long NANOS_IN_ONE_SECOND = 1_000_000_000;
    private static final float NANOS_IN_ONE_SECOND_FLOAT = 1_000_000_000f;
    private static long currentTimeNanos;
    private static long diffNanos;
    private static float m_fStartTime;
    private static boolean m_bWriteStartTime = false;

    public static void setCurrentTimeNanos(long timeNanos) {
        diffNanos = timeNanos - currentTimeNanos;
        currentTimeNanos = timeNanos;
    }
    public static long getCurrentTimeNanos() {
        return currentTimeNanos;
    }
    public static long getCurrentTimeSeconds() {
        return currentTimeNanos / NANOS_IN_ONE_SECOND;
    }
    public static long getTimeDiffNanos() {
        return diffNanos;
    }
    public static float getTimeDiffSeconds() {
        return diffNanos / NANOS_IN_ONE_SECOND_FLOAT;
    }
    public static float getStartTime() { return m_fStartTime; }
    public static float getRealCurrentTimeSeconds() {
        return (float)(currentTimeNanos / 1000000000.0) - m_fStartTime;
    }
    public static void setStartTime() {
        if (m_bWriteStartTime == false)
            m_bWriteStartTime = true;
        m_fStartTime = getCurrentTimeSeconds();
    }



    protected final int count;
    protected final int fps;
    protected long time;

    public GameTimer(int count, int framesPerSecond) {
        this.count = count;
        this.fps = framesPerSecond;
        this.time = currentTimeNanos;
    }

    public int getRawIndex() {
        long elapsed = currentTimeNanos - this.time;
        return (int) (((elapsed * fps + NANOS_IN_ONE_SECOND / 2) / NANOS_IN_ONE_SECOND));
    }
    public int getIndex() {
        int index = getRawIndex();
        return index % count;
    }
    public boolean done() {
        int index = getRawIndex();
        return index >= count;
    }

    public void reset() {
        this.time = currentTimeNanos;
    }
}


package kr.ac.kpu.game.andgp.kse.RhythmGame.game.scene;

import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameScene;

public class Robot extends GameScene {
    public enum Layer {
        bg, player, ui, COUNT
    }

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }
}

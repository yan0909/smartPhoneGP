package kr.ac.kpu.game.andgp.kse.RhythmGame.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.ui.activity.GameActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, GameActivity.class));
    }

    public void onBtnStart(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }
}

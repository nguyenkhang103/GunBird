package com.example.kaido.gunbirdgame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaido.gunbirdgame.R;

public class MainActivity extends AppCompatActivity {
    private boolean isMuted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
        TextView highScoreTxt = findViewById(R.id.score);
        final SharedPreferences sharedPreferences = getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("High Score: " + sharedPreferences.getInt("highscore",0));
        isMuted = sharedPreferences.getBoolean("isMuted", false);
        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);
        if (isMuted)
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
        else
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);

        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMuted = !isMuted;
                if (isMuted)
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
                else
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isMuted", isMuted);
                editor.apply();
            }
        });

    }
}
package com.example.kaido.gunbirdgame.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.kaido.gunbirdgame.R;

import static com.example.kaido.gunbirdgame.views.GameView.screenRatioX;
import static com.example.kaido.gunbirdgame.views.GameView.screenRatioY;

public class Bullets {
    public int x, y, width, height;
    public Bitmap bullet;

    public Bullets(Resources resources) {
        bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 4;
        height /= 4;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
    }
    public Rect getColisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}

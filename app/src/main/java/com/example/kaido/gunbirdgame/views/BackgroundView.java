package com.example.kaido.gunbirdgame.views;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.kaido.gunbirdgame.R;

public class BackgroundView {
    int x = 0, y = 0;
    Bitmap background;

    BackgroundView(int screenX, int screenY, Resources resources) {
        background = BitmapFactory.decodeResource(resources, R.drawable.background);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }
}

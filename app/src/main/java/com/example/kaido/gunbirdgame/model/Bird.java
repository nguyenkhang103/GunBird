package com.example.kaido.gunbirdgame.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.kaido.gunbirdgame.R;

import static com.example.kaido.gunbirdgame.views.GameView.screenRatioX;
import static com.example.kaido.gunbirdgame.views.GameView.screenRatioY;

public class Bird {
    public int x = 0, y, width, height, birdCounter = 1;
    public Bitmap bird1, bird2, bird3, bird4;
    public int speed = 20;
    public boolean isDied = true;

    public Bird(Resources resources) {
        bird1 = BitmapFactory.decodeResource(resources, R.drawable.bird1);
        bird2 = BitmapFactory.decodeResource(resources, R.drawable.bird2);
        bird3 = BitmapFactory.decodeResource(resources, R.drawable.bird3);
        bird4 = BitmapFactory.decodeResource(resources, R.drawable.bird4);

        width = bird1.getWidth();
        height = bird1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bird1 = Bitmap.createScaledBitmap(bird1,width,height,false);
        bird2 = Bitmap.createScaledBitmap(bird2,width,height,false);
        bird3 = Bitmap.createScaledBitmap(bird3,width,height,false);
        bird4 = Bitmap.createScaledBitmap(bird4,width,height,false);

        y = -height;
    }

    public Bitmap getBird() {
        if (birdCounter == 1) {
            birdCounter++;
            return bird1;
        }
        if (birdCounter == 2) {
            birdCounter++;
            return bird2;
        }
        if (birdCounter == 3) {
            birdCounter++;
            return bird3;
        }
        birdCounter = 1;
        return bird4;
    }

    public Rect getColisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

}

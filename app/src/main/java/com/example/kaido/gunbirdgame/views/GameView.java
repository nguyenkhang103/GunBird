package com.example.kaido.gunbirdgame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.kaido.gunbirdgame.model.Bird;
import com.example.kaido.gunbirdgame.model.Bullets;
import com.example.kaido.gunbirdgame.model.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Flight flight;
    List<Bullets> bulletsList;
    private Bird[] birds;
    private Random random;
    private BackgroundView backgroundPre, backgroundNex;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        backgroundPre = new BackgroundView(screenX, screenY, getResources());
        backgroundNex = new BackgroundView(screenX, screenY, getResources());

        backgroundNex.x = screenX;
        paint = new Paint();
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        bulletsList = new ArrayList<>();
        flight = new Flight(this, screenY, getResources());
        birds = new Bird[4];

        for (int i =0; i < 4; i++ ) {
            Bird bird = new Bird(getResources());
            birds[i] = bird;

        }
        random = new Random();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        backgroundPre.x -= 10 * screenRatioX;
        backgroundNex.x -= 10 * screenRatioX;

        if (backgroundPre.x + backgroundPre.background.getWidth() < 0) {
            backgroundPre.x = screenX;
        }
        if (backgroundNex.x + backgroundNex.background.getWidth() < 0) {
            backgroundNex.x = screenX;
        }

        if (flight.isGoingUp) {
            flight.y -= 30 * screenRatioY;
        } else {
            flight.y += 30 * screenRatioY;
        }
        if (flight.y < 0) {
            flight.y = 0;
        }
        if (flight.y >= screenX - flight.height) {
            flight.y = screenY - flight.height;
        }

        List<Bullets> trash = new ArrayList<>();

        for (Bullets bullet : bulletsList) {
            if (bullet.x > screenX) {
                trash.add(bullet);
            }
            bullet.x += 50 * screenRatioX;

            for(Bird bird: birds) {
               if(Rect.intersects(bird.getColisionShape(), bullet.getColisionShape())){
                   bird.x = -500;
                   bullet.x = screenX + 500;
                   bird.isDied = true;
               }
            }
        }
        for (Bullets bullet : trash) {
            bulletsList.remove(bullet);
        }

        for (Bird bird : birds) {
            bird.x -= bird.speed;
            if(bird.x + bird.width < 0) {
                if(!bird.isDied) {
                    isGameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                bird.speed = random.nextInt(bound);

                if (bird.speed < 10 * screenRatioX)
                    bird.speed = (int) (10 * screenRatioX);

                bird.x = screenX;
                bird.y = random.nextInt(screenY - bird.height);

                bird.isDied = false;
            }

            if (Rect.intersects(bird.getColisionShape(), flight.getColisionShape())) {
                isGameOver = true;
                return;
            }
        }
    }

    public void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(backgroundPre.background, backgroundPre.x, backgroundPre.y, paint);
            canvas.drawBitmap(backgroundNex.background, backgroundNex.x, backgroundNex.y, paint);

            if(isGameOver) {
                isPlaying = false;
                canvas.drawBitmap(flight.getDead(),flight.x, flight.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                return;
            }

            for(Bird bird : birds) {
                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
            }
            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);
            for (Bullets bullets : bulletsList)
                canvas.drawBitmap(bullets.bullet, bullets.x, bullets.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;

                if (event.getX() > (screenX / 2)) {
                    flight.toShoot++;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < (screenX / 2)) {
                    flight.isGoingUp = true;
                }
                break;
        }
        return true;

    }

    public void newBullet() {
        Bullets bullets = new Bullets(getResources());
        bullets.x = flight.x + flight.width;
        bullets.y = flight.y + (flight.height / 2);
        bulletsList.add(bullets);
    }
}

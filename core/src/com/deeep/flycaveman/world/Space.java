package com.deeep.flycaveman.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.screens.GameScreen;

import java.util.Random;

/**
 * Created by Elmar on 10-2-2015.
 */
public class Space {
    Array<Star> stars;
    public int maxStars = 500;
    private Random random = new Random();
    private Texture starTex;
    Pixmap pixmap;

    public Space() {
        stars = new Array<Star>();
        for (int i = 0; i < maxStars; i++) {
            stars.add(new Star(
                    (GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth) * random.nextFloat() + GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth,
                    random.nextFloat() * GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportHeight * 4 - (GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportHeight * 2)));
        }
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        starTex = new Texture(pixmap);
    }

    public void update(float deltaT, Vector3 cameraPos) {
        float cavemanX = cameraPos.x - 16;
        float cavemanY = cameraPos.y;
        for (Star star : stars) {
            //star.x += 0.1 * deltaT;
            if (star.x < GameScreen.gameCamera.position.x - GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth) {
                star.x = cavemanX + (GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth) * random.nextFloat() + GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth;
                star.y = cavemanY + random.nextFloat() * GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportHeight * 4 - (GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportHeight * 2);
            }
            star.transparency = Math.min(Math.max(0, (star.y - 80) / 80), 1);
        }
    }

    public void draw(SpriteBatch spriteBatch) {

        for (Star star : stars) {
            spriteBatch.setColor(1, 1, 1, star.transparency); //set alpha to 1
            spriteBatch.draw(starTex, star.x, star.y, 0.1f, 0.1f);
        }
        spriteBatch.setColor(1, 1, 1, 1f); //set alpha to 1
    }

    public class Star {
        public float x, y;
        public float transparency;

        public Star() {

        }

        public Star(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}

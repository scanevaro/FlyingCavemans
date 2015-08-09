package com.deeep.flycaveman.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
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
                    (GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth) * random.nextFloat() +
                            GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth, random.nextFloat() *
                    GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportHeight * 4 -
                    (GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportHeight * 2)));
        }
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        starTex = new Texture(pixmap);
    }

    public void update(Vector3 cameraPos, Body cavemanBody) {
        float cavemanX = cameraPos.x - 16;
        float cavemanY = cameraPos.y;
        for (int i = 0; i < stars.size; i++) {
            if (stars.get(i).x < GameScreen.gameCamera.position.x - GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth) {
                stars.get(i).x = cavemanX + (GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth) * random.nextFloat() + GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth;
                stars.get(i).y = cavemanY + random.nextFloat() * GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportHeight * 4 - (GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportHeight * 2);
            }
            stars.get(i).transparency = Math.min(Math.max(0, (stars.get(i).y - Area.HALF_SPACE) / Area.HALF_SPACE), 1);
        }
        if (cavemanBody.getPosition().y > Area.HALF_SPACE) {
            float gravity = Math.min(0.5f, 1 - (.5f * (cavemanBody.getPosition().y - Area.HALF_SPACE) / (Area.MAX_SPACE)));
            cavemanBody.setGravityScale(gravity);
        } else cavemanBody.setGravityScale(1);
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int i = 0; i < stars.size; i++) {
            spriteBatch.setColor(1, 1, 1, stars.get(i).transparency); //set alpha to 1
            spriteBatch.draw(starTex, stars.get(i).x, stars.get(i).y, 0.1f, 0.1f);
        }
        spriteBatch.setColor(1, 1, 1, 1f); //set alpha to 1
    }

    public class Star {
        public float x, y;
        public float transparency;

        public Star(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
package com.deeep.flycaveman.classes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.CaveMan;

import java.util.Random;

/**
 * Created by Elmar on 10-2-2015.
 */
public class Space {
    Array<Star> stars;
    public int maxStars = 200;
    private Random random = new Random();
    private Texture starTex;
    Pixmap pixmap;

    public Space() {
        stars = new Array<Star>();
        for (int i = 0; i < maxStars; i++) {
            stars.add(new Star(random.nextFloat() * Core.BOX2D_VIRTUAL_WIDTH, random.nextFloat() * Core.BOX2D_VIRTUAL_HEIGHT));
        }
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        starTex = new Texture(pixmap);
    }

    public void update(float deltaT, Vector3 cameraPos) {
        float cavemanX = cameraPos.x-16;
        float cavemanY = cameraPos.y;
        for (Star star : stars) {
            if (star.x < cavemanX) {
                star.x = cavemanX + Core.BOX2D_VIRTUAL_WIDTH * random.nextFloat() + Core.BOX2D_VIRTUAL_WIDTH;
                star.y = cavemanY + random.nextFloat() * Core.BOX2D_VIRTUAL_HEIGHT * 4 - Core.BOX2D_VIRTUAL_HEIGHT*2;
            }
            star.transparency = Math.min(Math.max(0,(star.y-80)/80),1);
        }
    }

    public void draw(SpriteBatch spriteBatch) {

        for (Star star : stars) {
            spriteBatch.setColor(1, 1, 1, star.transparency); //set alpha to 1
            spriteBatch.draw(starTex, star.x, star.y,0.1f,0.1f);
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

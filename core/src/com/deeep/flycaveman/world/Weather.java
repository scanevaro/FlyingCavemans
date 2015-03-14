package com.deeep.flycaveman.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

import java.util.Comparator;

/**
 * Created by Elmar on 25-2-2015.
 */
public class Weather {
    private final int backgroundWidth = (int) (Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2);
    public Array<Cloud> clouds = new Array<Cloud>();
    private Sort sort = new Sort();

    public Weather() {
        Cloud cloud;
        for (int i = 0; i < 10; i++) {
            for (int b = 0; b < 10; b++) {
                cloud = new Cloud(i, b, 0);
                addCloud(cloud);
            }
        }
    }

    public void addCloud(Cloud cloud) {
        clouds.add(cloud);
        sort.sort(clouds, new Comparator<Cloud>() {
            @Override
            public int compare(Cloud o1, Cloud o2) {
                return (int) (o1.layer - o2.layer);
            }
        });
    }

    public void update(float horizon) {
        for (Cloud cloud : clouds) {
            cloud.update(horizon);
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        Color color = spriteBatch.getColor();
        for (Cloud cloud : clouds) {
            if (cloud.inScreen()) { //check if inbound
                //spriteBatch.draw(Assets.getCloud(cloud.cloudId), cloud.x, cloud.y, 15, 10);
            }
        }
        spriteBatch.setColor(color);
    }

    public class Cloud {
        public float x, y;
        private float originalX;
        public float layer = 0;
        public int cloudId = 0;

        public Cloud(float x, float y, int layer) {
            this.originalX = this.x = x;
            this.y = y;
            this.layer = layer;
        }

        public void update(float horizontalX) {
            x = originalX + horizontalX * layer;
            if (this.x + backgroundWidth <= horizontalX - backgroundWidth) {
                this.originalX += ((3) * backgroundWidth);
            }
        }

        public boolean inScreen() {
            return true;
        }
    }
}

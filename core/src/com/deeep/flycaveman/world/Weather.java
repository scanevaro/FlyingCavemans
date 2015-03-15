package com.deeep.flycaveman.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Sort;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

import java.util.Random;

/**
 * Created by Elmar on 25-2-2015.
 */
public class Weather {
    private final int backgroundWidth = (int) (Core.BOX2D_VIRTUAL_WIDTH);
    private final int backgroundHeight = (int) (Core.BOX2D_VIRTUAL_HEIGHT);
    public Array<Array<Cloud>> clouds = new Array<Array<Cloud>>();
    private Sort sort = new Sort();

    public int layers = 3;

    public Weather() {
        Random random = new Random();
        for (int i = 0; i < layers; i++) {
            clouds.add(new Array<Cloud>());
        }
            clouds.get(0).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 2, 0.5f));
            clouds.get(0).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 2, 0.5f));
            clouds.get(0).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 2, 0.5f));
            clouds.get(0).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 2, 0.5f));
            clouds.get(1).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 3, 0.4f));
            clouds.get(1).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 3, 0.4f));
            clouds.get(1).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 3, 0.4f));
            clouds.get(1).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 3, 0.4f));
            clouds.get(2).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 4, 0f));
            clouds.get(2).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 4, 0f));
            clouds.get(2).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 4, 0f));
            clouds.get(2).add(new Cloud(random.nextFloat() * (Core.BOX2D_VIRTUAL_WIDTH), random.nextFloat() * (backgroundHeight) + backgroundHeight, 4, 0f));
    }


    public void update(Vector3 horizon) {
        for (Array<Cloud> array : clouds) {
            for (Cloud cloud : array) {
                cloud.update(horizon.x, horizon.y);
            }
        }
    }

    public void draw(SpriteBatch spriteBatch, int layer) {
        if (layer >= clouds.size) return;
        for (Cloud cloud : clouds.get(layer)) {
            if (cloud.inScreen()) { //check if inbound
                spriteBatch.draw(Assets.getCloud(cloud.cloudId), cloud.x, cloud.y, 14 * (0.2f + cloud.layer), 7 * (0.2f + cloud.layer));
            }
        }
    }

    public class Cloud {
        public float x, y;
        private float originalX;
        private float originalY;
        public float layer = 0;
        public int cloudId = 0;
        private float ratio;

        public Cloud(float x, float y, int layer, float ratio) {
            this.originalX = this.x = x;
            this.originalY = this.y = x;
            this.y = y;
            this.layer = layer;
            this.ratio = ratio;
        }

        public void update(float horizontalX, float horizontalY) {
            x = originalX + horizontalX * ratio;
            y = originalY + horizontalY * ratio;
            if (this.x + backgroundWidth <= horizontalX - backgroundWidth) {
                this.originalX += ((3) * backgroundWidth);
            }
        }

        public boolean inScreen() {
            return true;
        }
    }
}

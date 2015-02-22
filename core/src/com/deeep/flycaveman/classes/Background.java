package com.deeep.flycaveman.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.Core;

/**
 * Created by Elmar on 2/18/2015.
 */
public class Background {
    private final int backgroundWidth = (int) (Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2);
    private Array<Array<Layer>> arrays = new Array<Array<Layer>>();
    private Array<Layer> layer_1 = new Array<Layer>();
    private Array<Layer> layer_2 = new Array<Layer>();
    private Array<Layer> layer_3 = new Array<Layer>();

    public Background() {
        arrays.add(layer_1);
        arrays.add(layer_2);
        arrays.add(layer_3);
    }

    public void setLayer(int nr, TextureRegion textureRegion, float ratio) {
        Array<Layer> temp = null;
        if (nr == 1) {
            temp = layer_1;
        } else if (nr == 2) {
            temp = layer_2;
        } else {
            temp = layer_3;
        }
        Layer tempLayer;
        for (int i = 0; i < 3; i++) {
            tempLayer = new Layer(textureRegion, ratio, i);
            // tempLayer.x = -Core.BOX2D_VIRTUAL_WIDTH + i * Core.BOX2D_VIRTUAL_WIDTH;
            temp.add(tempLayer);
        }
    }


    public void draw(SpriteBatch spriteBatch) {
        for (Array array : arrays) {
            for (Layer layer : (Array<Layer>) array) {
                spriteBatch.draw(layer.layerRegion, layer.getX(), 0, backgroundWidth, Core.BOX2D_VIRTUAL_HEIGHT);
            }
        }
    }

    public void update(float x) {
        for (Array array : arrays) {
            for (Layer layer : (Array<Layer>) array) {
                layer.update(x);
            }
        }
    }

    public class Layer {
        public TextureRegion layerRegion;
        public float ratio;
        public float xPos;
        private float offset = 0;
        public int index = 0;

        public Layer(TextureRegion layerRegion, float ratio, int index) {
            this.ratio = ratio;
            this.layerRegion = layerRegion;
            this.xPos = index * (backgroundWidth);
        }

        public void update(float x) {
            if (this.xPos + backgroundWidth <= x - backgroundWidth) {
                this.xPos += ((3) * backgroundWidth);
            }
        }

        public float getX() {
            return xPos;
        }
    }
}

package com.deeep.flycaveman.classes;

public class BiomeLayer {
    private int layer = 1;
    private int theme = 0;

    public float xPos = 0;
    public float yPos = 0;
    private float ratio = 0;
    private float originalX = 0;
    private float originalY = 0;

    public BiomeLayer(int layer, int index, float ratio) {
        this.originalX = index * (Biomes.backgroundWidth);
        this.originalY = 0;
        this.layer = layer;
        this.ratio = ratio;
    }

    public void update(float backgroundX, float backgroundY) {
        xPos = originalX + backgroundX * ratio;
        yPos = originalY + backgroundY * ratio;
        if (this.xPos + Biomes.backgroundWidth <= backgroundX - Biomes.backgroundWidth) {
            this.originalX += ((3) * Biomes.backgroundWidth);
            this.theme = Biomes.nextLayer[layer - 1];
            if (Biomes.nextLayer[layer - 1] % 10 != 0) {
                int oldLayer = Biomes.nextLayer[layer - 1];
                Biomes.nextLayer[layer - 1] = oldLayer - (oldLayer % 10) + 10;
            }
        }
    }

    public float getX() {
        return xPos;
    }
    public float getY() {
        return yPos;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public int getLayer() {
        return layer;
    }
}

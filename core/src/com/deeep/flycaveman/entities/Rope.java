package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.input.GameInputProcessor;

/**
 * Created by Elmar on 4/2/2015.
 */
public class Rope implements Entity {
    int totalRopePieces = 5;
    int currentPieces = 0;
    float lengthPerPiece = 0.5f;
    private Sprite rope;
    private Array<Sprite> ropeSprites;
    private Vector2 startPos;

    public Rope(float x, float y) {
        rope = new Sprite(Assets.rope);
        ropeSprites = new Array<Sprite>();
        startPos = new Vector2(x, y);
    }

    public void update(CaveMan caveMan) {
        float distance = startPos.dst(caveMan.body.getPosition());

        float dX = startPos.x - caveMan.body.getPosition().x;
        float dY = startPos.y - caveMan.body.getPosition().y;
        float angle = (float) ((float) Math.atan2(dY, dX) + Math.PI);
        if (GameInputProcessor.catapulting) {
            if (distance > (ropeSprites.size) * lengthPerPiece) {
                Sprite sprite = new Sprite(Assets.rope);
                sprite.setSize(0.5f, 0.5f);
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                ropeSprites.add(sprite);
            } else if (distance < (ropeSprites.size - 2) * lengthPerPiece) {
                ropeSprites.removeIndex(ropeSprites.size - 1);
            }
            for (int i = 0; i < ropeSprites.size; i++) {
                ropeSprites.get(i).setPosition((float) Math.cos(angle) * i * lengthPerPiece + startPos.x, (float) Math.sin(angle) * i * lengthPerPiece + startPos.y);
                ropeSprites.get(i).setRotation((float) Math.toDegrees(angle));
            }
            //add stretch
        } else {
            // drop that rope. and destretch
        }
    }


    @Override
    public void draw(Batch batch) {
        for (int i = 0; i < ropeSprites.size; i++) ropeSprites.get(i).draw(batch);
    }

    @Override
    public void update(float delta) {
    }
}

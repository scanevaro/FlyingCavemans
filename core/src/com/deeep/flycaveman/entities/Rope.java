package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Elmar on 4/2/2015.
 */
public class Rope implements Entity{
    int totalRopePieces = 5;
    int currentPieces = 0;
    float lengthPerPiece = 0.5f;


    @Override
    public void draw(Batch batch) {
        /*
        if(shouldDraw){
            if(forceLength > currentPieces * lengthPerPiece){
                if(currentPieces<totalRopePieces){
                    currentPieces++;
                }
            }else if(mousedist <((currentPieces-1) * lengthPerPiece){
                currentPieces--;
            }
        } */
    }
}

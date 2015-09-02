package com.deeep.flycaveman.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.deeep.flycaveman.net.GJAPI;

/**
 * Created by scanevaro on 21/03/2015.
 */
public class TextInput implements Input.TextInputListener {
    private GJAPI gj;
    private String username, usertoken;
    private int phase;

    public TextInput(GJAPI gj) {
        this.gj = gj;
        username = null;
        usertoken = null;
        phase = 0;
    }

    @Override
    public void input(String text) {
        if (phase == 0) {
            username = text;
            phase++;
            Gdx.input.getTextInput(this, "Enter your token", "token", "hint");
        } else if (phase == 1) {
            usertoken = text;

            gj.authenticateUser(username, usertoken, new GJAPI.callback() {
                @Override
                public void execute(String response) {
                    if (gj.responseGetSuccess(response)) {
                        Gdx.app.log("GJAPI", "Logged in succesfully!");
                    } else {
                        Gdx.app.log("GJAPI", "Failed to log in!");
                    }
                }
            });
        }
    }

    @Override
    public void canceled() {
    }
}
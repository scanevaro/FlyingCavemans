package com.deeep.flycaveman.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.deeep.flycaveman.Core;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration((int) Core.VIRTUAL_WIDTH, (int) Core.VIRTUAL_HEIGHT);
    }

    @Override
    public ApplicationListener getApplicationListener() {
        return new Core();
    }
}
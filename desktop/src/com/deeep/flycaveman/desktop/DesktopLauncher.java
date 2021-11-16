package com.deeep.flycaveman.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.deeep.flycaveman.Core;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode((int) Core.VIRTUAL_WIDTH, (int) Core.VIRTUAL_HEIGHT);
        new Lwjgl3Application(new Core(), config);
    }
}
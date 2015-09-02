package com.deeep.flycaveman.net;

// (c) Jani "JNyknn" Nykänen

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class GJAPI implements Net.HttpMethods {

    private final String httpBase = "http://gamejolt.com/api/game/v1/";

    private String key;
    private int gameId;

    private String userName,
            userToken,
            status;

    private boolean isActive;

    // Not sure if necessary
    private class classInt {
        public int value;

        classInt(int nValue) {
            value = nValue;
        }
    }

    private ArrayList<classInt> buffer;

    public interface callback {
        void execute(String response);
    }

    public GJAPI(String nKey, int nGameId) {
        key = nKey;
        gameId = nGameId;

        buffer = new ArrayList<classInt>();
    }

    public boolean isActive() {
        return isActive;
    }

    public String generateUrl(String type, String url) {
        String baseUrl = httpBase + type + "/?game_id=" + Integer.toString(gameId) + "&" + url, signature = "";

        try {
            byte[] bytesOfMessage = (baseUrl + key).getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytesOfMessage);

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; ++i) {
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
            }
            signature = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return baseUrl + "&signature=" + signature;
    }

    public void sendRequest(String type, String url, final callback callback) {
        status = "";

        String finalUrl = generateUrl(type, url);

        HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
        httpGet.setUrl(finalUrl);

        Gdx.net.sendHttpRequest(httpGet, new HttpResponseListener() {

            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                status = httpResponse.getResultAsString();
                Gdx.app.log("GJAPI", status);

                callback.execute(status);
            }

            @Override
            public void failed(Throwable t) {
                status = "Failed to send the request";
                Gdx.app.log("GJAPI", status);
            }

            @Override
            public void cancelled() {
                status = "Cancelled... for some reason";
            }
        });
    }

    public void openSession() {
        if (isActive) {
            sendRequest("sessions/open", "username=" + userName + "&user_token=" + userToken, new GJAPI.callback() {
                @Override
                public void execute(String response) {
                    Gdx.app.log("GJAPI", "Session opened!");
                }
            });
        }
    }

    public void pingSession() {
        if (isActive) {
            sendRequest("sessions/ping", "username=" + userName + "&user_token=" + userToken, new GJAPI.callback() {
                @Override
                public void execute(String response) {
                    Gdx.app.log("GJAPI", "Session pinged.");
                }
            });
        }
    }

    public void closeSession() {
        if (isActive) {
            sendRequest("sessions/close", "username=" + userName + "&user_token=" + userToken, new GJAPI.callback() {
                @Override
                public void execute(String response) {
                }
            });
        }
    }

    public boolean responseGetSuccess(String response) {
        if (response != "failed" || response != "cancelled") {
            String[] lines = response.split("\n");
            for (String line : lines) {
                if (line.split(":")[1].substring(1, line.split(":")[1].length() - 2).equals("true")) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public void authenticateUser(final String nUser, final String nToken, final callback nCallback) {
        Gdx.app.log("GJAPI", "Authenticating user...");

        sendRequest("users/auth", "username=" + nUser + "&user_token=" + nToken, new GJAPI.callback() {
            @Override
            public void execute(String response) {
                if (response != "failed" || response != "cancelled") {
                    String[] lines = response.split("\n");
                    for (String line : lines) {
                        if (line.split(":")[1].substring(1, line.split(":")[1].length() - 2).equals("true")) {
                            userName = nUser;
                            userToken = nToken;
                            isActive = true;
                            openSession();
                            Gdx.app.log("GJAPI", "Welcome, " + userName);
                        }
                    }
                }
                nCallback.execute(response);
            }
        });
    }

    public void addScore(int tableId, int score, String scoreString) {
        if (isActive) {
            sendRequest("scores/add", "username=" + userName + "&user_token=" + userToken + "&table_id=" +
                            Integer.toString(tableId) + "&score=" + scoreString + "&sort=" + Integer.toString(score),
                    new GJAPI.callback() {
                        @Override
                        public void execute(String response) {
                            Gdx.app.log("GJAPI", "Score added!");
                        }
                    });
        }
    }

    public void addTrophy(final int trophyId) {
        if (isActive) {
            boolean doSend = true;

            for (int i = 0; i < buffer.size(); i++) {
                if (buffer.get(i).value == trophyId) {
                    doSend = false;
                }
            }

            if (doSend) {
                sendRequest("trophies/add-achieved", "username=" + userName + "&user_token=" + userToken +
                        "&trophy_id=" + Integer.toString(trophyId), new GJAPI.callback() {
                    @Override
                    public void execute(String response) {
                        if (response != "failed" || response != "cancelled") {
                            String[] lines = response.split("\n");
                            for (String line : lines) {
                                if (line.split(":")[1].substring(1, line.split(":")[1].length() - 2).equals("true")) {
                                    Gdx.app.log("GJAPI", "New trophy!");
                                    buffer.add(new classInt(trophyId));
                                }
                            }
                        }
                    }
                });
            }
        }

    }
}
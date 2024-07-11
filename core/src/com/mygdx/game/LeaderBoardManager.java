package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.mygdx.game.uis.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeaderBoardManager {
    private static String url = "http://gamejame.duckdns.org:80/highscores";
    private static SetTextInterface setText;

    public static void sendRes(String name, int score) {
        String postData = "{\"player_name\": \"" + name + "\", \"score\": " + score + "}";
        Net.HttpRequest request = new Net.HttpRequest("POST");
        request.setUrl(url);
        request.setContent(postData);

        request.setHeader("Content-Type", "application/json");

        Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        };
        Gdx.net.sendHttpRequest(request, httpResponseListener);
    }

    public static void getRes(SetTextInterface setTextInterface, float x, float y, float yGap) {
        setText = setTextInterface;
        Net.HttpRequest httpRequest = new Net.HttpRequest();
        httpRequest.setMethod("GET");
        httpRequest.setUrl(url);

        Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String rawResult = httpResponse.getResultAsString();
                ArrayList<TextView> result = new ArrayList<>();
                List<Map<String, Object>> highscores = parseJson(rawResult);
                List<String> highscoresStrings = new ArrayList<>();
                for (Map<String, Object> record : highscores) {
                    String recordString = record.get("id") + "\t" +
                            record.get("player_name") + "\t" +
                            record.get("score");
                    highscoresStrings.add(recordString);
                }
                for (int i = 0; i < highscoresStrings.size(); i++) {
                    result.add(new TextView(MyGdxGame.bitmapFont, x, y - yGap * i * 2, (i + 1) + "." +  highscoresStrings.get(i).split("\t")[1]));
                    result.add(new TextView(MyGdxGame.bitmapFont, x, y - yGap * (i * 2 + 1), " " + highscoresStrings.get(i).split("\t")[2]));
                }
                setText.setText(result);
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("request failed", t.getMessage());
                getRes(setTextInterface, x, y, yGap);
            }

            @Override
            public void cancelled() {
                Gdx.app.log("cancelled", "loooooooooh");
                getRes(setTextInterface, x, y, yGap);
            }
        };
        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
    }

    private static List<Map<String, Object>> parseJson(String json) {
        List<Map<String, Object>> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            String objectString = matcher.group(1);
            Map<String, Object> map = new HashMap<>();
            Pattern innerPattern = Pattern.compile("\"(.*?)\":(\"(.*?)\"|\\d+)");

            Matcher innerMatcher = innerPattern.matcher(objectString);

            while (innerMatcher.find()) {
                String key = innerMatcher.group(1);
                String value = innerMatcher.group(3) != null ? decodeUnicode(innerMatcher.group(3)) : innerMatcher.group(2);
                map.put(key, value);
            }

            list.add(map);
        }

        return list;
    }
    private static String decodeUnicode(String unicodeStr) {
//        String decodedStr = unicodeStr;
        Pattern unicodePattern = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
        Matcher unicodeMatcher = unicodePattern.matcher(unicodeStr);
        StringBuffer sb = new StringBuffer(unicodeStr.length());
        while (unicodeMatcher.find()) {
            int charCode = Integer.parseInt(unicodeMatcher.group(1), 16);
            unicodeMatcher.appendReplacement(sb, Character.toString((char)charCode));
        }
        unicodeMatcher.appendTail(sb);
//        String decodedStr = sb.toString();
        return sb.toString();
    }
}
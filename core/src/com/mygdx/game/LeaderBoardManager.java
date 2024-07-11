package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.mygdx.game.screens.Leaderboard;
import com.mygdx.game.uis.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeaderBoardManager {
    private static String url = "http://gamejame.duckdns.org:80/highscores";
    private List<String> data = new ArrayList<>();
    private static SetTextInterface setText;

    public static void sendRes(String name, int score) {


//        String jsonInputString = "{\"player_name\": \"" + name + "\", \"score\": " + score + "}";
//
//        try {
//            URL obj = new URL(url);
//            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
//            postConnection.setRequestMethod("POST");
//            postConnection.setRequestProperty("Content-Type", "application/json; utf-8");
//            postConnection.setRequestProperty("Accept", "application/json");
//            postConnection.setDoOutput(true);
//
//            try (OutputStream os = postConnection.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            int responseCode = postConnection.getResponseCode();
////            System.out.println("POST Response Code : " + responseCode);
//
//            if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        postConnection.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//                // print result
////                System.out.println(response.toString());
////                System.out.println("Рекорд успешно добавлен.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
                    result.add(new TextView(MyGdxGame.bitmapFont, x, y - yGap * i * 2, highscoresStrings.get(i).split("\t")[1]));
                    result.add(new TextView(MyGdxGame.bitmapFont, x, y - yGap * (i * 2 + 1), highscoresStrings.get(i).split("\t")[2]));
                }
                setText.setText(result);
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("fuck", t.toString());
            }

            @Override
            public void cancelled() {
                Gdx.app.log("fuck", "t.toString()");
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
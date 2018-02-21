package lt.laurynasmalinauskas.vcs.services;

import lt.laurynasmalinauskas.vcs.beans.EventTypeBean;
import lt.laurynasmalinauskas.vcs.beans.GameStatusVariable;
import lt.laurynasmalinauskas.vcs.beans.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class GameServiceImpl implements GameServiceInterface {
    public static final String SERVER_URL = "http://192.168.1.35:8080";
    public static final String CREATE_USER_METHOD_NAME = "/create_user";
    public static final String JOIN_GAME_METHOD_NAME = "/join";
    public static final String SETUP_GAME_METHOD_NAME = "/setup";
    public static final String STATUS_GAME_METHOD_NAME = "/status";
    public static final String TURN_GAME_METHOD_NAME = "/turn";



    @Override
    public User createUser(String name, String email) {
        String url = SERVER_URL + CREATE_USER_METHOD_NAME + "?name=" + name + "&email=" + email;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);

            String resp = getResponceAsString(response.getEntity().getContent());

            return convertUser(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private User convertUser(String body) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(body);
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("name");
            String email = (String) jsonObject.get("email");
            String id = (String) jsonObject.get("id");
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setName(name);
            return user;
        }

        return null;
    }

    private String getResponceAsString(InputStream inputStream) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }



    @Override
    public GameStatusVariable joinServer(String id) {
        GameStatusVariable result = new GameStatusVariable(null, null, null, null, null);
        String url = SERVER_URL + JOIN_GAME_METHOD_NAME + "?user_id=" + id;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);

            String resp = getResponceAsString(response.getEntity().getContent());
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(resp);
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                result.setGameId((String) jsonObject.get("id"));
                result.setNextTurnForUser((String) jsonObject.get("nextTurnForUserId"));
                result.setStatus((String) jsonObject.get("status"));
                return result;
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }


    @Override
    public void sendGameMap(String gameId, String userId, String shipMapinString) {
        String url = SERVER_URL + SETUP_GAME_METHOD_NAME + "?game_id=" + gameId + "&user_id=" + userId + "&data=" + shipMapinString;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            client.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public GameStatusVariable getGameStatus(String gameId) {
        GameStatusVariable result = new GameStatusVariable(null, null, null, null, null);
        String url = SERVER_URL + STATUS_GAME_METHOD_NAME + "?game_id=" + gameId;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);

            String resp = getResponceAsString(response.getEntity().getContent());
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(resp);
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                result.setGameId((String) jsonObject.get("id"));
                result.setNextTurnForUser((String) jsonObject.get("nextTurnForUserId"));
                result.setStatus((String) jsonObject.get("status"));
                result.setWinner((String) jsonObject.get("winnerUserId"));
                JSONArray events = (JSONArray) jsonObject.get("events");
                EventTypeBean[] resultEvents = new EventTypeBean[events.size()];
                for (int i = 0; i < events.size(); i++) {
                    JSONObject temporary = (JSONObject) events.get(i);
                    JSONObject coordinate = (JSONObject) temporary.get("coordinate");
                    long rowLong = (long) coordinate.get("row");
                    int row = (int) rowLong;

                    resultEvents[i] = new EventTypeBean(new Date((Long) temporary.get("date")), (String) coordinate.get("column"), row,
                            (String) temporary.get("userId"), (boolean) temporary.get("hit"));
                }
                result.setGameEvents(resultEvents);
                return result;
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void sentMyShot(String gameId, String playerId, String shotCoordinate){
        String url = SERVER_URL + TURN_GAME_METHOD_NAME + "?game_id=" + gameId+ "&user_id="+playerId+"&data="+shotCoordinate;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            client.execute(request);


        } catch (Exception e) {
            System.out.println("Error while sending data");
            e.printStackTrace();
        }

    }

    public String getServerResponse(String url){
        String resp;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);

            resp = getResponceAsString(response.getEntity().getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resp;

    }


}

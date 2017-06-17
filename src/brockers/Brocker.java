package brockers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import request.HttpRequest;

import java.io.IOException;

/**
 * Created by chiranz on 6/12/17.
 */
public class Brocker {

    public JsonObject postRequest(String uri, String payload) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("paragraph", payload);
        CloseableHttpResponse response = new HttpRequest().postRequest(uri, jsonObject);
        HttpEntity entity = response.getEntity();
        String entityString = "";
        try {
            entityString = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(entityString).getAsJsonObject();

        return json;

    }

    public JsonArray postRequestArrayRes(String uri, String payload) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("paragraph", payload);
        CloseableHttpResponse response = new HttpRequest().postRequest(uri, jsonObject);
        HttpEntity entity = response.getEntity();
        String entityString = "";
        try {
            entityString = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonArray json = parser.parse(entityString).getAsJsonArray();

        return json;

    }

}

package request;

import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

    public class HttpRequest {

        private CloseableHttpClient httpclient;
        public HttpRequest() {
            this.httpclient = HttpClients.createDefault();
        }

        public CloseableHttpResponse putRequest(String uri, String body) {
            RequestBuilder requestBuilder = null;
            try {
                requestBuilder = RequestBuilder.put()
                        .setHeader("Content-Type", "application/json").setUri(uri)
                        .setEntity(new StringEntity(body));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return execute(requestBuilder.build());
        }

        public CloseableHttpResponse postRequest(String uri, JsonObject body) {
            RequestBuilder requestBuilder = null;
            try {
                requestBuilder = RequestBuilder.post()
                        .setHeader("Content-Type", "application/json").setUri(uri)
                        .setEntity(new StringEntity(body.toString()));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return execute(requestBuilder.build());
        }

        public CloseableHttpResponse getPlainRequest(String uri){
            HttpUriRequest request = RequestBuilder.get()
                    .setUri(uri)
                    .setHeader("Content-Type", "application/json").setUri(uri)
                    .build();
            return execute(request);

        }

        private CloseableHttpResponse execute(HttpUriRequest request) {
            try {
                return httpclient.execute(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void closeConn() {

            try {
                this.httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

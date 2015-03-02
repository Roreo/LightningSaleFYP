package com.example.rory.lightningsalefyp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rory on 02/12/2014.
 */
public class HttpHandler {

    private static String streamToString(InputStream in) throws IOException {
        StringBuilder out = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        for(String line = br.readLine(); line != null; line = br.readLine())
            out.append(line);
        br.close();
        return out.toString();
    }

    public static final String HttpGetExec (String uri) {

        String s = "no response";
        HttpURLConnection conn = null;
        int http_status = 0;

        try {
            URL url = new URL(uri);
            conn = (HttpURLConnection)url.openConnection();
            InputStream in = conn.getInputStream();
            http_status = conn.getResponseCode();

            if (http_status == 200) {
                s = streamToString(in);
            } else {
                s = "bad response";
            }
        } catch (MalformedURLException m) {
            s = "malformed URL exception";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return s;

    }

    public static final String HttpPostExec (String uri, String payload) {

        String s = "no response";
        HttpURLConnection conn = null;
        int http_status = 0;

        try {
            byte[] payloadBytes = payload.getBytes();
            URL url = new URL(uri);

            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST"); //We be doing a POST
            conn.setRequestProperty("Content-Type", "application/json"); //We are sending JSON
            conn.setFixedLengthStreamingMode(payloadBytes.length);

            OutputStream out = conn.getOutputStream();

            out.write(payloadBytes);

            InputStream in = conn.getInputStream();
            http_status = conn.getResponseCode();

            if (http_status == 200) {
                s = streamToString(in);
            } else {
                s = "bad response";
            }
        } catch (MalformedURLException m) {
            s = "malformed URL exception";
        } catch (IOException e) {
            s = "IO exception";
        } finally {
            conn.disconnect();
        }
        return s;

    }

    public static final String HttpPutExec (String uri, String payload) {

        String s = "no response";
        HttpURLConnection conn = null;
        int http_status = 0;

        try {
            byte[] payloadBytes = payload.getBytes();
            URL url = new URL(uri);


            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT"); //We be doing a PUT
            conn.setRequestProperty("Content-Type", "application/json"); //We are sending JSON
            conn.setFixedLengthStreamingMode(payloadBytes.length);

            OutputStream out = conn.getOutputStream();

            out.write(payloadBytes);

            InputStream in = conn.getInputStream();
            http_status = conn.getResponseCode();

            if (http_status == 200) {
                s = streamToString(in);
            } else {
                s = "bad response";
            }
        } catch (MalformedURLException m) {
            s = "malformed URL exception";
        } catch (IOException e) {
            s = "IO exception";
        } finally {
            conn.disconnect();
        }
        System.out.println(s);
        return s;



    }

    public static final String HttpUpdateExec (String uri) {

        String s = "no response";
        HttpURLConnection conn = null;
        int http_status = 0;

        try {
            URL url = new URL(uri);

            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("PUT"); //We be doing a DELETE

            InputStream in = conn.getInputStream();
            http_status = conn.getResponseCode();

            if (http_status == 200) {
                s = streamToString(in);
            } else {
                s = "bad response";
            }
        } catch (MalformedURLException m) {
            s = "malformed URL exception";
        } catch (IOException e) {
            s = "IO exception";
        } finally {
            conn.disconnect();
        }
        return s;

    }

}

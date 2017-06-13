package com.twitter.hbc.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by adityajoshi on 5/15/17.
 */
public class MessageTransporter implements Runnable {
    String message;

    public MessageTransporter(String message){
        this.message = message;
        this.message = "{ \"Type\": \"Twitter\"," + "\"Payload\": " + this.message + "}";
        run();
    }

    @Override
    public void run() {

        try {
            URL url = new URL("http://192.168.0.5:8080/procons/rest/format");
            HttpURLConnection client = null;
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type","application/json");
            client.setDoOutput(true);
            OutputStreamWriter outputStream  = new OutputStreamWriter(client.getOutputStream());
            outputStream.write(message);
            outputStream.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = "";
            int i = 0;
            while((line = br.readLine()) != null)
            {
                // Append server response in string
                System.out.println("Return val " + i + " " + line);
                i++;
            }
            outputStream.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

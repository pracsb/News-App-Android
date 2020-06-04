package com.example.myapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchDetailData extends AsyncTask<Void, Void, Void> {
    StringBuilder data = new StringBuilder();
    private String title;
    private String imageUrl;
    private String section;
    private String date;
    private String shareUrl;
    private String full_body;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data.append(line);
            }

            String data_string = data.toString();
            JSONObject data_json = new JSONObject(data_string);
            try {
                JSONObject content_array = data_json.getJSONObject("contents");
                title = content_array.getString("webTitle");
                //  For image url from JSON resposne
                JSONObject blocks = content_array.getJSONObject("blocks");

                JSONObject main = blocks.getJSONObject("main");

                if (main.has("elements")) {
                    JSONArray elements = main.getJSONArray("elements");
                    if (elements.length() == 0) {
                        imageUrl = "https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png";
                    } else {
                        JSONObject inside_elements = elements.getJSONObject(0);
                        JSONArray assets = inside_elements.getJSONArray("assets");
                        if (assets.length() == 0) {
                            imageUrl = "https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png";
                        } else {
                            JSONObject inside_assets = assets.getJSONObject(0);
                            imageUrl = inside_assets.getString("file");
                        }
                    }
                } else {
                    imageUrl = "https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png";
                }

//                               For section from JSON response
                section = content_array.getString("sectionName");
                date = content_array.getString("webPublicationDate");
                shareUrl = content_array.getString("webUrl");
                StringBuilder sb = new StringBuilder();
                JSONArray body = blocks.getJSONArray("body");
                for (int i = 0; i < body.length(); i++) {
                    JSONObject inside_body = body.getJSONObject(i);
                    String body_html = inside_body.getString("bodyHtml");
                    sb.append(body_html);
                }
                full_body = sb.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        DetailActivity.detail_title.setText(title);
    }
}

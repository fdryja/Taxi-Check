package com.example.taxi_check;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBox;

    public void getWeather(View view){
        DownloadTask task = new DownloadTask();
        task.execute("https://www.gdansk.pl/files/xml/wykaz-taksowek-z-licencjami.json");
//        weatherTextView.setText();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = findViewById(R.id.checkBox);
//        checkBox.setText("Numer boczny");

//        getWeather();
    }

    public void isChecked(View view){
        if(checkBox.isChecked()){
            checkBox.setText("Number boczny");
        }else{
            checkBox.setText("Numer rejestracyjny");
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1){
                    char current = (char) data;
                    json += current;
                    data = reader.read();
                }
                return json;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Błąd";
            } catch (IOException e) {
                e.printStackTrace();
                return "Błąd";

            }



        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                JSONArray array = new JSONArray(weatherInfo);
                String message = "";
                for(int i = 0; i<array.length();i++){
                    JSONObject jsonPart = array.getJSONObject(i);
                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");
                    if(!main.equals("")&&!description.equals("")){
                        message+=main+":"+description + "\n";
                    }
                }
//                weatherTextView.setText(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

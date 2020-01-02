package com.example.taxi_check;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBox;
    TextView textView;

    public void getInfo(View view){
//        DownloadTask task = new DownloadTask();
//        task.execute("https://www.gdansk.pl/files/xml/wykaz-taksowek-z-licencjami.json");
        GetInfo task = new GetInfo();
        task.execute();
    }


    public class GetInfo extends AsyncTask<Void, Void, Void>{

        String data = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url = new URL("https://www.gdansk.pl/files/xml/wykaz-taksowek-z-licencjami.json");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line != null){
                    line = bufferedReader.readLine();
                    data = data + line;
                }

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            textView.setText(this.data);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = findViewById(R.id.checkBox);
        textView = findViewById(R.id.textView);

    }

    public void isChecked(View view){
        if(checkBox.isChecked()){
            checkBox.setText("Number boczny");
        }else{
            checkBox.setText("Numer rejestracyjny");
        }
    }



//    public class DownloadTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String json = "";
//            URL url;
//            HttpURLConnection urlConnection = null;
//
//            try {
//                url = new URL("https://www.gdansk.pl/files/xml/wykaz-taksowek-z-licencjami.json");
//                urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream inputStream = urlConnection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(inputStream);
//                int data = reader.read();
//                while (data != -1){
//                    char current = (char) data;
//                    json += current;
//                    data = reader.read();
//                }
//                return json;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return "Błąd";
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "Błąd";
//
//            }
//
//
//
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                String weatherInfo = jsonObject.getString("weather");
//                JSONArray array = new JSONArray(weatherInfo);
//                String message = "";
//                for(int i = 0; i<array.length();i++){
//                    JSONObject jsonPart = array.getJSONObject(i);
//                    String main = jsonPart.getString("main");
//                    String description = jsonPart.getString("description");
//                    if(!main.equals("")&&!description.equals("")){
//                        message+=main+":"+description + "\n";
//                    }
//                }
////                weatherTextView.setText(message);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

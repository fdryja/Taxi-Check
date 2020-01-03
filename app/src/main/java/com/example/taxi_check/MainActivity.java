package com.example.taxi_check;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
    TextView textView;
    TextView numerRejestracyjnyTextView;
    TextView numerBocznyTextView;
    TextView numerLicencjiTextView;
    TextView dataLicencjiTextView;
    TextView nipTextview;
    EditText editText;
    String info = "";


    public void getInfo(View view){
        GetInfo task = new GetInfo();
        task.execute();
        info = String.valueOf(editText.getText()).toUpperCase().replaceAll("\\s","");

    }


    public class GetInfo extends AsyncTask<Void, Void, Void>{

        String data = "";
        String numerRej = "";
        String numerBoczny = "";
        String numerLicencji = "";
        String dataLicencji = "";
        String nip = "";


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



                JSONObject JA = new JSONObject(data);
                JSONArray JW = JA.getJSONArray("results");

                for (int i = 0; i<JW.length(); i++){
                    JSONObject JO = JW.getJSONObject(i);

                    numerBoczny = JO.getString("numerBoczny").toUpperCase().replaceAll("\\s","");
                    numerRej = JO.getString("numerRejestracyjny").toUpperCase().replaceAll("\\s","");



                    if (numerBoczny.equals(info) || numerRej.equals(info)) {
                        numerRej = JO.getString("numerRejestracyjny");
                        numerBoczny = JO.getString("numerBoczny");
                        numerLicencji = JO.getString("numerLicencji");
                        dataLicencji = JO.getString("dataLicencji");
                        nip = JO.getString("nip");
                        break;
                    }

                }

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            textView.setText(info);
            numerRejestracyjnyTextView.setText(numerRej);
            numerBocznyTextView.setText(numerBoczny);
            numerLicencjiTextView.setText(numerLicencji);
            dataLicencjiTextView.setText(dataLicencji);
            nipTextview.setText(nip);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        numerRejestracyjnyTextView = findViewById(R.id.numerRejestracyjnyTextView);
        numerBocznyTextView = findViewById(R.id.numerBocznyTextView);
        numerLicencjiTextView = findViewById(R.id.numerLicencjiTextView);
        dataLicencjiTextView = findViewById(R.id.dataLicencjiTextView);
        nipTextview = findViewById(R.id.nipTextView);
        editText = findViewById(R.id.editText);
    }



}

package com.example.taxi_check;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
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
    CheckBox checkBox;
    TextView textView;
    TextView numerRejestracyjnyTextView;
    TextView numerBocznyTextView;
    TextView numerLicencjiTextView;
    TextView dataLicencjiTextView;
    TextView nipTextview;
    EditText editText;

    public void getInfo(View view){
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
            textView.setText(data);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = findViewById(R.id.checkBox);
        textView = findViewById(R.id.textView);
        numerRejestracyjnyTextView = findViewById(R.id.numerRejestracyjnyTextView);
        numerBocznyTextView = findViewById(R.id.numerBocznyTextView);
        numerLicencjiTextView = findViewById(R.id.numerLicencjiTextView);
        dataLicencjiTextView = findViewById(R.id.dataLicencjiTextView);
        nipTextview = findViewById(R.id.nipTextView);
        editText = findViewById(R.id.editText);
    }

    public void isChecked(View view){
        if(checkBox.isChecked()){
            checkBox.setText("Number boczny");
            editText.setHint("Number boczny");
        }else {
            checkBox.setText("Numer rejestracyjny");
            editText.setHint("Number rejestracyjny");
        }
    }

}

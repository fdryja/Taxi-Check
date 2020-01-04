package com.example.taxi_check;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    TextView textView;
    TextView numerRejestracyjnyTextView;
    TextView numerBocznyTextView;
    TextView numerLicencjiTextView;
    TextView dataLicencjiTextView;
    TextView nipTextview;
    EditText editText;
    String info = "";
    Button kopiujNipButton;
    Button button;
    Button wyszukajNipButton;
    TextView nrejTextView;
    TextView nboczTextView;
    TextView nlicTextView;
    TextView dlicTextView;
    TextView nTextView;
    String copyNip = "";
    WebView webView;
    Button goBackButton;

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
        int foundOne=0;


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
                        copyNip = nip;
                        foundOne++;
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
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


            if(foundOne==1){

            textView.setText(info);
            numerRejestracyjnyTextView.setText(numerRej);
            numerBocznyTextView.setText(numerBoczny);
            numerLicencjiTextView.setText(numerLicencji);
            dataLicencjiTextView.setText(dataLicencji);
            nipTextview.setText(nip);
            nrejTextView.setVisibility(View.VISIBLE);
            nboczTextView.setVisibility(View.VISIBLE);
            nlicTextView.setVisibility(View.VISIBLE);
            dlicTextView.setVisibility(View.VISIBLE);
            nTextView.setVisibility(View.VISIBLE);
            kopiujNipButton.setVisibility(View.VISIBLE);
            wyszukajNipButton.setVisibility(View.VISIBLE);
            foundOne=0;
            inputMethodManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            } else{
                Toast toast = Toast.makeText(getApplicationContext(),"Nie znaleziono takiego numeru",Toast.LENGTH_LONG);
                toast.show();
            }

        }
    }

    public void copyNip(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copied", copyNip);
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(getApplicationContext(),"Skopiowano do schowka",Toast.LENGTH_SHORT);
        toast.show();
    }
    public void loadNip(View view){
        wyszukajNipButton.setVisibility(View.INVISIBLE);
        kopiujNipButton.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://wyszukiwarkaregon.stat.gov.pl/appBIR/index.aspx");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setVisibility(View.VISIBLE);
        goBackButton.setVisibility(View.VISIBLE);
    }
    public void goBack(View view){
        webView.setVisibility(View.INVISIBLE);
        goBackButton.setVisibility(View.INVISIBLE);
        wyszukajNipButton.setVisibility(View.VISIBLE);
        kopiujNipButton.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
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
        nrejTextView = findViewById(R.id.nrejTextView);
        nboczTextView = findViewById(R.id.nboczTextView);
        nlicTextView = findViewById(R.id.nlicTextView);
        dlicTextView = findViewById(R.id.dlicTextView);
        nTextView = findViewById(R.id.nTextView);
        webView = findViewById(R.id.webView);
        wyszukajNipButton = findViewById(R.id.wyszukajNipButton);
        kopiujNipButton = findViewById(R.id.kopiujNipButton);
        button = findViewById(R.id.button);
        goBackButton = findViewById(R.id.goBackButton);

        editText.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_DONE){
                    getInfo(null);
                    return true;
                }
                return false;
            }


        });

    }

    @Override
    public void onBackPressed() {
        if(webView.getVisibility() == View.VISIBLE){
            goBack(null);
        }else{
            super.onBackPressed();
        }

    }
}

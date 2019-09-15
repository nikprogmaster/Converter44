package com.example.converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }/*
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.landscape);*/


    public void onClick(View view) {
        EditText editText = findViewById(R.id.from);

                Runnable runnable = new Runnable() {
                    public void run() {
                        Message msg = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        // Создание URL
                        URL url = null;

                        HttpsURLConnection myConnection;
                        try {
                            url = new URL("https://www.currencyconverterapi.com/docs");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        // Создание соединения
                        try {
                            myConnection =
                                    (HttpsURLConnection) url.openConnection();
                            myConnection.setRequestProperty("Accept", "api/v7/convert?q=USD_PHP,PHP_USD&compact=ultra&apiKey=c9026c5440228b06a63e\"");
                            myConnection.setRequestMethod("POST");
                            String myData = "https://free.currconv.com/api/v7/convert?q=USD_PHP,PHP_USD&compact=ultra&apiKey=c9026c5440228b06a63e";
                            myConnection.setDoOutput(true);
                            myConnection.getOutputStream().write(myData.getBytes());

                            myConnection.setRequestMethod("GET");
                            InputStream responseBody = myConnection.getInputStream();
                            InputStreamReader responseBodyReader =
                                    new InputStreamReader(responseBody, "UTF-8");
                            JsonReader jsonReader = new JsonReader(responseBodyReader);
                            System.out.println(jsonReader.toString());
                            System.out.println("До сюда дошли");
                            jsonReader.beginObject(); // Start processing the JSON object

                            while (jsonReader.hasNext()) { // Loop through all keys
                                String key = jsonReader.nextName(); // Fetch the next key
                                if (key.equals("USD_PHP")) { // Check if desired key
                                    // Fetch the value as a String
                                    String value = jsonReader.nextString();
                                    System.out.println("Выводим значение"+value);
                                    // ...
                                    bundle.putString("Key", value);
                                    msg.setData(bundle);
                                    handler.sendMessage(msg);
                                    break; // Break out of the loop
                                } else {
                                    jsonReader.skipValue(); // Skip values of other keys
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    Handler handler = new Handler() {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            Bundle bundle = msg.getData();
                            String date = bundle.getString("Key");
                            EditText vivod = findViewById(R.id.editText2);
                            vivod.setText(date);
                        }
                    };

                };
                Thread thread = new Thread(runnable);
                thread.start();
            }

}
/*

editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        Message msg = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        // Создание URL
                        URL url = null;

                        HttpsURLConnection myConnection;
                        try {
                            url = new URL("https://free.currconv.com");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        // Создание соединения
                        try {
                            myConnection =
                                    (HttpsURLConnection) url.openConnection();

                            myConnection.setRequestMethod("POST");
                            String myData = "/api/v7/convert?q=USD_PHP,PHP_USD&compact=ultra&apiKey=c9026c5440228b06a63e";
                            myConnection.setDoOutput(true);
                            myConnection.getOutputStream().write(myData.getBytes());

                            myConnection.setRequestMethod("GET");
                            InputStream responseBody = myConnection.getInputStream();
                            InputStreamReader responseBodyReader =
                                    new InputStreamReader(responseBody, "UTF-8");
                            JsonReader jsonReader = new JsonReader(responseBodyReader);
                            jsonReader.beginObject(); // Start processing the JSON object
                            String value = jsonReader.nextString();
                            bundle.putString("Key", value);
                            msg.setData(bundle);
                            handler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
        */

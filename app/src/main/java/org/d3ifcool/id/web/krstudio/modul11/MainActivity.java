package org.d3ifcool.id.web.krstudio.modul11;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Bundle;

import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        final Switch switchButton = (Switch) findViewById(R.id.switchButton);
        SharedPreferences sharedPreferences = getSharedPreferences("org.d3ifcool.id.web.krstudio.modul11",MODE_PRIVATE); //ganti sesuai package name switchButton.setChecked(sharedPreferences.getBoolean("Na meOfThingsToSave", false));

        switchButton.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() { @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b){
                SharedPreferences.Editor editor = getSharedPreferences("org.d3ifcool.id.web.krstudio.modul11", MODE_PRIVATE).edit();//ganti sesuai package name editor.putBoolean("NameOfThingsToSave", false); editor.apply();
                switchButton.setChecked(true);
                new Background_get().execute("update?api_key=B35UBCELBRFCS83L&field1=1"); //ganti sesuai WriteAPIKey ThingSpeak Toast.makeText(MainActivity.this, "On!", Toast.LENGTH_LONG).show();
                new CountDownTimer(15000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        switchButton.setEnabled(false);
                    }

                    public void onFinish() {
                        switchButton.setEnabled(true);
                    }
                }.start();
            }else{
                SharedPreferences.Editor editor = getSharedPreferences("org.d3ifcool.id.web.krstudio.modul11", MODE_PRIVATE).edit();//ganti sesuai package name editor.putBoolean("NameOfThingToSave", false); editor.apply();
                new Background_get().execute("update?api_key=89XB0TRG5WAOAZI1&field1=0"); //ganti sesuai WriteAPIKey ThingSpeak Toast.makeText(MainActivity.this, "Off!", Toast.LENGTH_LONG).show();
            }
        }
        });
    }

    private class Background_get extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) { try {
            /* Change the IP to the IP you set in the arduino sketch
             */
            URL url = new URL("https://api.thingspeak.com/" + params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                result.append(inputLine).append("\n");

            in.close(); connection.disconnect();

            Log.e("AkhAl", result.toString());
            return result.toString();

        } catch (IOException e) { e.printStackTrace();

        }
            return null;
        }

    }
}
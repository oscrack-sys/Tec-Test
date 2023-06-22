package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsResult;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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


    String data = "";
    ProgressDialog pd;
    String resultado = "";
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        toast = Toast.makeText(this, "Email invalido ", Toast.LENGTH_LONG);


        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            EditText email = findViewById(R.id.editTextTextEmailAddress);
            EditText tel = findViewById(R.id.tel);
            EditText name = findViewById(R.id.editText);
            if (tel.getText().length() < 10 ){
                toast = Toast.makeText(this, "Telefono invalido", Toast.LENGTH_LONG);
                toast.show();
            }else{
                if(isValidEmail(email.getText())){
                    new JsonTask().execute("https://jsonplaceholder.typicode.com/users");
                    email.setText("");
                    tel.setText("");
                    name.setText("");
                }else{
                    toast.show();
                    System.out.println(tel.getText().length());

                }
            }


        });
    }

    public void adialog(String msg){
        LayoutInflater inflater= LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.scroll, null);

        TextView textview=(TextView)view.findViewById(R.id.textmsg);
        textview.setText("Your really long message.");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Respuesta:");
        alertDialog.setPositiveButton("OK", (dialog, id) -> {
            dialog.cancel();
        });

        alertDialog.setMessage(msg);
        alertDialog.setView(view);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Cargando...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            System.out.println(result);
            adialog(result);


        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}

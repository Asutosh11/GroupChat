package com.example.asu.groupchat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.List;


/**
 * Created by asu on 21-05-2015.
 */
public class SetUsername extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
    String username;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_username);

        // Sets the App icon in the ActionBar
        ActionBar ab =getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.mipmap.ic_launcher);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        ButtonClick();


    }

    void ButtonClick(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = editText.getText().toString();


                SharedPreferences sharedPref = getSharedPreferences
                        ("userInfo", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("Username", username);

                editor.apply();


                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

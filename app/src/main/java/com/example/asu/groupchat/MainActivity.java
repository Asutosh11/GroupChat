package com.example.asu.groupchat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText edittext;
    private String theText;
    private ListView listView;
    private SimpleAdapter adapter;
    private String[] messages_in_view;
    private String[] savedUsername;
    private HashMap<String, String> hm;
    private List<HashMap<String, String>> aList;
    private String username;
    private int noOfRowsInParseDB;
    private DateAndTimeClass DateAndTimeObject;
    private String DateTime;
    private String[] DisplayDateTime;
    private Button LoadMessages;


   // Related to the Service 3. There are 3 comments related to the service
    MyService buckysService;
    boolean isBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Checks if internet connection is there or not
        // If no internet is there, the app quits
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // 'true' means the phone has internet, 'false' means it has no internet
        Boolean isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent == false) {
            Intent i = new Intent(getApplicationContext(), NoInternetConnectivity.class);
            startActivity(i);
        }


        // Checks if the name of the app user is saved in the SharedPreference variable or not.
        // If yes, start this activity otherwise redirect to SetUsername Activity

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if (sharedPref.contains("Username")) {
            username = sharedPref.getString("Username", "");
        } else {
            Intent i = new Intent(getApplicationContext(), SetUsername.class);
            startActivity(i);
        }



        Parse.initialize(this, "xE4lQgZxPZACbDkELXkDlRt4ZqjiD9z7XgYFjOe3",
                "KLq67yFpoutQtQIuZN5FlYSC2hbdu8spR2aW2VK8");


        // Related to the Service 2
        Intent in = new Intent(this, MyService.class);
        bindService(in, buckysConnection, Context.BIND_AUTO_CREATE);


        DateAndTimeObject = new DateAndTimeClass();
        DateTime = DateAndTimeObject.DateTime();

        // Sets the App icon in the ActionBar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.mipmap.ic_launcher);


        button = (Button) findViewById(R.id.button);

        edittext = (EditText) findViewById(R.id.edittext);

        LoadMessages = (Button)findViewById(R.id.button3);

        //All ListView things along with some other things start here


        messages_in_view = new String[1000];

        savedUsername = new String[1000];

        DisplayDateTime = new String[1000];


        SaveMessageToParseCloudDB();

        // DisplayMessages();


    }

    public void loadMessagesButton(View view){
        DisplayMessages();
        LoadMessages.setText("Load New Messages");
    }


    public void DisplayMessages() {

        noOfRowsInParseDB = buckysService.NoOfRowsInDB();

        DisplayDateTime = buckysService.getDateTimeFromParseCloudDB();

        messages_in_view = buckysService.getMessagesFromParseCloudDB();

        savedUsername = buckysService.getUsernameFromParseCloudDB();

        aList = new ArrayList<HashMap<String, String>>();

        for (int j = noOfRowsInParseDB; j >= 0; j--) {
            hm = new HashMap<String, String>();
            hm.put("userView", "" + savedUsername[j]);
            hm.put("msgView", "" + messages_in_view[j]);
            hm.put("DateTimeView", "" + DisplayDateTime[j]);

            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"userView", "msgView", "DateTimeView"};

        // Ids of views in listview_layout
        int[] to = {R.id.userView, R.id.msgView, R.id.DateTimeView};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_view_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = (ListView) findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        button.setBackgroundResource(R.drawable.ic_action_send_now);

    }


    public void SaveMessageToParseCloudDB() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                theText = edittext.getText().toString();

                if(theText.length() != 0) {
                    button.setBackgroundResource(R.drawable.loading);
                    edittext.setText("");
                    ParseObject testObject = new ParseObject("Obb1");
                    testObject.put("Message", theText);
                    testObject.put("Username", username);
                    testObject.put("DateTime", DateTime);

                    testObject.saveInBackground();

                    // After a new message has been saved, this loads the ListView again with updated data
                    DisplayMessages();
                }
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


    // Related to the Service 1
    private ServiceConnection buckysConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyLocalBinder binder = (MyService.MyLocalBinder) service;
            buckysService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;


        }
    };
}

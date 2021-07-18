package com.kagwisoftwares.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;

    private List<BaseMessage> messages;

    public String url = "http://10.0.0.6:5000/chatbot";
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private String messageFromBot;
    private String senderMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText messageText = (EditText) findViewById(R.id.message_text);

        messages = new ArrayList<>();

        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        mMessageAdapter = new MessageListAdapter(this, messages);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        Button sendMessage = (Button) findViewById(R.id.button_send);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senderMessage = messageText.getText().toString();
                messages.add(new BaseMessage(senderMessage, "Charles", 1));
                refreshList();
                new MyAsyncTask().execute();
                messageText.setText("");
            }
        });
    }

    private void refreshList() {
        mMessageAdapter.notifyDataSetChanged();
        mMessageRecycler.scrollToPosition(messages.size() - 1);
    }
    
    class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // Runs on UI thread- Any code you wants
            // to execute before web service call. Put it here.
            // Eg show progress dialog
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                RequestBody msg = new FormBody.Builder().add("message", senderMessage).build();
                Request request = new Request.Builder().url(url).post(msg).build();
                Response response = client.newCall(request).execute();
                JSONObject jsonObj = new JSONObject(response.body().string());
                messageFromBot = jsonObj.get("message").toString();
                response.body().close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return messageFromBot;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String resp) {
            Log.d("BOT RESPONDED", resp);
            messages.add(new BaseMessage(resp, "Bot", 0));
            refreshList();
        }

    }
}
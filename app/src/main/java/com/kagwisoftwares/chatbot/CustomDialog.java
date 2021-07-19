package com.kagwisoftwares.chatbot;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Activity c;
    private Dialog d;
    private Button cancel, ok;
    private EditText ip, port;

    private String url;

    public CustomDialog(Activity a, String url) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        ip = (EditText) findViewById(R.id.ip);
        port = (EditText) findViewById(R.id.port);

        ok = (Button) findViewById(R.id.ok);
        cancel = (Button) findViewById(R.id.cancel);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String ipAddr = ip.getText().toString();
        String portAddr = port.getText().toString();

        switch (v.getId()) {
            case R.id.ok:
                if (portAddr.equals("")) {
                    url = "http://" + ipAddr + "/chatbot";
                } else {
                    url = "http://" + ipAddr + ":" + portAddr + "/chatbot";
                }
                Toast.makeText(getContext(), "Connected to :" + url, Toast.LENGTH_SHORT).show();
                MainActivity.url = url;
                break;
            case R.id.cancel:
                dismiss();
                c.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}


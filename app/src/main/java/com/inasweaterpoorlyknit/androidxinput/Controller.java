package com.inasweaterpoorlyknit.androidxinput;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Controller extends AppCompatActivity {

    private Button RTriggerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        RTriggerButton = (Button) findViewById(R.id.right_trigger_button);
        RTriggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "R", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

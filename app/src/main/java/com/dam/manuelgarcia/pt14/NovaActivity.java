package com.dam.manuelgarcia.pt14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovaActivity extends AppCompatActivity {

    String nomAct = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova);
        setTitle("Afegeix intent");

    }

    public void AddIntent(View view) {

        EditText editText = findViewById(R.id.edtEntraNomAct);

        if (!editText.getText().toString().isEmpty()) {

            nomAct = editText.getText().toString();
            finish();

        } else Toast.makeText(this, "Activity buida, afegeix un nom", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void finish() {

        if (!nomAct.equals("")) {
            Intent intent = new Intent();
            intent.putExtra("nomAct", nomAct);
            setResult(RESULT_OK, intent);
        } else {
            Log.d("test", "passa finish ");
            //back botó , torna com a canceled
            Intent intent = new Intent();
            intent.putExtra("nomAct", "");
            setResult(RESULT_CANCELED, intent);
        }
        Log.d("test", "passa ");

        super.finish();
    }

    public void Tornar(View view) {

        super.finish();

    }
}

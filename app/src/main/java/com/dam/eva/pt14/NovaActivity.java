package com.dam.eva.pt14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovaActivity extends AppCompatActivity {

    String nomAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova);
        setTitle("Afegeix intent");

    }

    public void AddIntent(View view) {

      EditText editText=  (EditText) findViewById(R.id.edtEntraNomAct);

      if (!editText.getText().toString().isEmpty()) {

          nomAct=editText.getText().toString();
          finish();

      } else Toast.makeText(this, "Activity buida", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void finish() {

        Intent intent=new Intent();
        intent.putExtra("nomAct",nomAct);
        setResult(RESULT_OK,intent);

        super.finish();
    }

    public void Tornar(View view) {


        super.finish();

    }
}

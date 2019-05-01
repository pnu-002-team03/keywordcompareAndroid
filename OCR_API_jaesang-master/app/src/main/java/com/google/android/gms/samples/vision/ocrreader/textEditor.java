package com.google.android.gms.samples.vision.ocrreader;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.samples.vision.ocrreader.R;

public class textEditor extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);
        editText=findViewById(R.id.editText);
        SharedPreferences sharedPreferences = getSharedPreferences("first", MODE_PRIVATE);

        String savedString = sharedPreferences.getString("sampleString", "");

        editText.setText(savedString);

    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_menu, menu);

        return true;

    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.save:


                AlertDialog.Builder builder = new AlertDialog.Builder(textEditor.this);

                builder.setTitle("SAVE");

                builder.setMessage("저장하시겠습니까?");

                builder.setIcon(android.R.drawable.ic_dialog_alert);


                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPreferences = getSharedPreferences("first", MODE_PRIVATE);

                        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();


                        // 데이터를 기록한다.

                        sharedPreferencesEditor.putString("sampleString", editText.getText().toString());

                        sharedPreferencesEditor.apply();

                        Toast.makeText(textEditor.this, editText.getText() + "이(가) 저장되었습니다.", Toast.LENGTH_LONG).show();

                    }

                });

                builder.setNegativeButton("아니오", null);

                builder.create().show();

                return true;


        }

        return super.onOptionsItemSelected(item);

    }
}

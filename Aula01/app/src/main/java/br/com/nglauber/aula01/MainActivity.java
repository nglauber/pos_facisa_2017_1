package br.com.nglauber.aula01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_texto);
        textView = (TextView) findViewById(R.id.text_mensagem);
        findViewById(R.id.button_mensagem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NGVL", "Clicou no toast!");
                textView.setText(editText.getText());
                Toast.makeText(MainActivity.this, editText.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
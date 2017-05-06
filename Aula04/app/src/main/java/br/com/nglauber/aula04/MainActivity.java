package br.com.nglauber.aula04;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText)findViewById(R.id.editTexto);
        final EditText editText2 = (EditText)findViewById(R.id.editTexto2);
        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(MainActivity.this,
                            editText.getText().toString()+"\n"+editText2.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        findViewById(R.id.btnDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirDialog();
            }
        });

        findViewById(R.id.btnListDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirListDialog();
            }
        });
    }

    int position;

    private void exibirListDialog() {
        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        position = which;
                        Toast.makeText(MainActivity.this, "Selecionou "+ which, Toast.LENGTH_SHORT).show();
                    }
                };

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setSingleChoiceItems(R.array.opcoes, position, listener)
                .setPositiveButton("OK", null)
                .create();
        dialog.show();
    }

    private void exibirDialog() {
        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String message  = "";
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                message = "Oh Yes!";
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                message = "Oh No!";
                                break;
                            case DialogInterface.BUTTON_NEUTRAL:
                                message = "Quem sabe?";
                                break;
                        }
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                };

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja realmente excluir?")
                .setPositiveButton("Sim", listener)
                .setNeutralButton("Cancelar", listener)
                .setNegativeButton("Não", listener)
                .create();
        dialog.show();
    }

}

package br.com.nglauber.aula02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edtGasolina;
    EditText edtEtanol;
    TextView txtResultado;

    int resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtGasolina = (EditText) findViewById(R.id.edit_gasolina);
        edtEtanol = (EditText) findViewById(R.id.edit_etanol);
        txtResultado = (TextView) findViewById(R.id.text_resultado);
        findViewById(R.id.button_calcular).setOnClickListener(onClick);
        if (savedInstanceState != null){
            resultado = savedInstanceState.getInt("calculo");
            if (resultado != 0) {
                exibirResultado();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("calculo", resultado);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            float gas = Float.parseFloat(edtGasolina.getText().toString());
            float etanol = Float.parseFloat(edtEtanol.getText().toString());
            if (etanol < gas * .7f){
                resultado = R.string.texto_etanol;
            } else {
                resultado = R.string.texto_gasolina;
            }
            exibirResultado();
        }
    };

    private void exibirResultado(){
        String opcao = getString(resultado);
        txtResultado.setText(getString(R.string.texto_resultado, opcao));
    }
}

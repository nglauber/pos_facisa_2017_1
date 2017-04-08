package br.com.nglauber.aula02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_gasolina) EditText edtGasolina;
    @BindView(R.id.edit_etanol) EditText edtEtanol;
    @BindView(R.id.text_resultado) TextView txtResultado;

    int resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

    @OnClick(R.id.button_calcular)
    public void onCalcularClick() {
        float gas = Float.parseFloat(edtGasolina.getText().toString());
        float etanol = Float.parseFloat(edtEtanol.getText().toString());
        if (etanol < gas * .7f){
            resultado = R.string.texto_etanol;
        } else {
            resultado = R.string.texto_gasolina;
        }
        exibirResultado();
    }

    private void exibirResultado(){
        String opcao = getString(resultado);
        txtResultado.setText(getString(R.string.texto_resultado, opcao));
    }
}

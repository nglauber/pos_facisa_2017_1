package br.com.nglauber.aula03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalheActivity extends AppCompatActivity {

    @BindView(R.id.detalhe_edit_nome)
    EditText edtNome;
    @BindView(R.id.detalhe_rg_social)
    RadioGroup rgSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.detalhe_button_salvar)
    void onSalvarClick(){
        Pessoa p = new Pessoa();
        p.nome = edtNome.getText().toString();
        switch (rgSocial.getCheckedRadioButtonId()){
            case R.id.detalhe_radio_fb:
                p.redesocial = Pessoa.RS_FACEBOOK;
                break;
            case R.id.detalhe_radio_tw:
                p.redesocial = Pessoa.RS_TWITTER;
                break;
            case R.id.detalhe_radio_gplus:
                p.redesocial = Pessoa.RS_GPLUS;
                break;
        }
        Intent it = new Intent();
        it.putExtra("pessoa", p);
        setResult(RESULT_OK, it);
        finish();
    }
}

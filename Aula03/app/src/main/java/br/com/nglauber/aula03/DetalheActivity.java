package br.com.nglauber.aula03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.parceler.Parcels;

import br.com.nglauber.aula03.persistence.PessoaDb;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalheActivity extends AppCompatActivity {

    public static final String EXTRA_PESSOA = "pessoa";
    public static final String EXTRA_ID = "id";

    @BindView(R.id.detalhe_edit_nome)
    EditText edtNome;
    @BindView(R.id.detalhe_rg_social)
    RadioGroup rgSocial;

    Pessoa pessoa;
    int id;
    PessoaDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        ButterKnife.bind(this);

        db = new PessoaDb(this);

        pessoa = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_PESSOA));
        id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (pessoa == null) {
            pessoa = new Pessoa();
        } else {
            preencherCampos();
        }
    }

    private void preencherCampos() {
        edtNome.setText(pessoa.nome);
        switch (pessoa.redesocial){
            case Pessoa.RS_FACEBOOK:
                rgSocial.check(R.id.detalhe_radio_fb);
                break;
            case Pessoa.RS_GPLUS:
                rgSocial.check(R.id.detalhe_radio_gplus);
                break;
            case Pessoa.RS_TWITTER:
                rgSocial.check(R.id.detalhe_radio_tw);
                break;
        }
    }

    @OnClick(R.id.detalhe_button_salvar)
    void onSalvarClick(){
        pessoa.nome = edtNome.getText().toString();
        switch (rgSocial.getCheckedRadioButtonId()){
            case R.id.detalhe_radio_fb:
                pessoa.redesocial = Pessoa.RS_FACEBOOK;
                break;
            case R.id.detalhe_radio_tw:
                pessoa.redesocial = Pessoa.RS_TWITTER;
                break;
            case R.id.detalhe_radio_gplus:
                pessoa.redesocial = Pessoa.RS_GPLUS;
                break;
        }
        Intent it = new Intent();
        it.putExtra(EXTRA_PESSOA, Parcels.wrap(pessoa));
        it.putExtra(EXTRA_ID, id);
        setResult(RESULT_OK, it);
        db.salvar(pessoa);
        finish();
    }
}

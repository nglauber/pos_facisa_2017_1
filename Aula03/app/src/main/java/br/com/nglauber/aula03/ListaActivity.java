package br.com.nglauber.aula03;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListaActivity extends AppCompatActivity {

    private static final int REQ_CADASTRO = 0;
    public static final String EXTRA_PESSOAS = "pessoas";

    private ArrayList<Pessoa> pessoas;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    PessoaRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            pessoas = Parcels.unwrap(savedInstanceState.getParcelable(EXTRA_PESSOAS));
        } else {
            pessoas = new ArrayList<>();
        }

        adapter = new PessoaRecycleAdapter(pessoas, new PessoaRecycleAdapter.AoClicarNaPessoa() {
            @Override
            public void pessoaFoiClicada(Pessoa pessoa, int id) {
                Intent it = new Intent(ListaActivity.this, DetalheActivity.class);
                it.putExtra(DetalheActivity.EXTRA_PESSOA, Parcels.wrap(pessoa));
                it.putExtra(DetalheActivity.EXTRA_ID, id);
                startActivityForResult(it, REQ_CADASTRO);
            }
        });
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        configuraSwipe();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_PESSOAS, Parcels.wrap(pessoas));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CADASTRO) {
            if (resultCode == RESULT_OK) {
                Pessoa p = Parcels.unwrap(data.getParcelableExtra(DetalheActivity.EXTRA_PESSOA));
                int id = data.getIntExtra(DetalheActivity.EXTRA_ID, -1);
                if (id == -1) {
                    pessoas.add(p);
                } else {
                    pessoas.set(id, p);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick(R.id.lista_fab_add)
    void onClick(){
        Intent it = new Intent(this, DetalheActivity.class);
        startActivityForResult(it, REQ_CADASTRO);
    }

    private void configuraSwipe() {
        ItemTouchHelper.SimpleCallback swipe =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        final int position = viewHolder.getAdapterPosition();
                        excluirPessoa(position);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipe);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void excluirPessoa(final int position) {

        final Pessoa pessoaExcluida = pessoas.remove(position);
        adapter.notifyItemRemoved(position);

        Snackbar.make(recyclerView, R.string.msg_pessoa_excluida, Snackbar.LENGTH_LONG)
                .setAction(R.string.button_desfazer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pessoas.add(position, pessoaExcluida);
                        adapter.notifyItemInserted(position);
                    }
                })
                .show();
    }
}

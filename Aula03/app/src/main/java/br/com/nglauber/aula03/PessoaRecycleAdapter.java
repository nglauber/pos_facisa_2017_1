package br.com.nglauber.aula03;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PessoaRecycleAdapter extends
        RecyclerView.Adapter<PessoaRecycleAdapter.VH> {

    List<Pessoa> mPessoas;
    AoClicarNaPessoa mListener;

    public PessoaRecycleAdapter(List<Pessoa> mensagens,
                                AoClicarNaPessoa listener) {
        mPessoas = mensagens;
        mListener = listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent,
                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pessoa, parent, false);

        final VH vh = new VH(v);
        vh.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            int pos = vh.getAdapterPosition();
                            Pessoa pessoa = mPessoas.get(pos);
                            mListener.pessoaFoiClicada(pessoa, pos);
                        }
                    }
                });
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int pos) {
        Pessoa pessoa = mPessoas.get(pos);
        int image;
        switch (pessoa.redesocial){
            case Pessoa.RS_FACEBOOK:
                image = R.drawable.logo_face;
                break;
            case Pessoa.RS_GPLUS:
                image = R.drawable.logo_gplus;
                break;
            default:
                image = R.drawable.logo_twitter;
                break;
        }
        holder.imgLogo.setImageResource(image);
        holder.textNome.setText(pessoa.nome);
    }

    @Override
    public int getItemCount() {
        return mPessoas != null ? mPessoas.size() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.img_logo)
        public ImageView imgLogo;
        @BindView(R.id.txt_nome)
        public TextView textNome;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface AoClicarNaPessoa {
        void pessoaFoiClicada(Pessoa pessoa, int id);
    }
}
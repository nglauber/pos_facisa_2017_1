package br.com.nglauber.aula03;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;

public class PessoaAdapter extends ArrayAdapter<Pessoa> {
    public PessoaAdapter(
            @NonNull Context context,
            @NonNull List<Pessoa> pessoas) {
        super(context, 0, pessoas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pessoa pessoa = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_pessoa, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imageView = ButterKnife.findById(convertView, R.id.img_logo);
            viewHolder.textView = ButterKnife.findById(convertView, R.id.txt_nome);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

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
        viewHolder.imageView.setImageResource(image);
        viewHolder.textView.setText(pessoa.nome);

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}

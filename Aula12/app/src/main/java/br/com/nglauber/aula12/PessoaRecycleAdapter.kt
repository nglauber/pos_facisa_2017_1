package br.com.nglauber.aula12

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_pessoa.view.*

class PessoaRecycleAdapter(var pessoas: MutableList<Pessoa>,
                           var listener: PessoaRecycleAdapter.AoClicarNaPessoa?) : RecyclerView.Adapter<PessoaRecycleAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pessoa, parent, false)

        val vh = VH(v)
        vh.itemView.setOnClickListener {
            if (listener != null) {
                val pos = vh.adapterPosition
                val pessoa = pessoas[pos]
                listener?.pessoaFoiClicada(pessoa, pos)
            }
        }
        return vh
    }

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val pessoa = pessoas[pos]
        val image: Int
        when (pessoa.redesocial) {
            Pessoa.RS_FACEBOOK -> image = R.drawable.logo_face
            Pessoa.RS_GPLUS -> image = R.drawable.logo_gplus
            else -> image = R.drawable.logo_twitter
        }
        holder.itemView.imgLogo.setImageResource(image)
        holder.itemView.txtNome.text = pessoa.nome
    }

    override fun getItemCount(): Int {
        return pessoas.size
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface AoClicarNaPessoa {
        fun pessoaFoiClicada(pessoa: Pessoa, id: Int)
    }
}
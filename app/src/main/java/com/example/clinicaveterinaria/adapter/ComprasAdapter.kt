package com.example.clinicaveterinaria.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.clinicaveterinaria.databinding.ItemListaComprasBinding
import com.example.clinicaveterinaria.model.Compras

class ComprasAdapter(): Adapter<ComprasAdapter.ComprasViewHolder>(){

    private var listaCompras: List<Compras> = emptyList()
    fun adicionarLista(lista:List<Compras>){
        this.listaCompras = lista
        notifyDataSetChanged()
    }

    inner class ComprasViewHolder(private val binding: ItemListaComprasBinding) : ViewHolder(binding.root) {
        fun bind(compras: Compras) {
            binding.textTipoCompras.text = compras.tipo
            binding.textValorCompras.text = "R$ ${compras.valor}"
            binding.textDataCompras.text = "Data : " + compras.data
            binding.textValidadeCompras.text = "Validade : " + compras.validade
        }
    }

    //efetua a criação dos itens de lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComprasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComprasViewHolder(
            ItemListaComprasBinding.inflate(layoutInflater, parent, false)
        )
    }

    //efetua a vinculação dos dados a lista do recycler veiw
    override fun onBindViewHolder(holder: ComprasViewHolder, position: Int) {
        holder.bind(listaCompras[position])
    }

    //retorna quantidade de itens da lista
    override fun getItemCount(): Int {
        return listaCompras.size
    }

}
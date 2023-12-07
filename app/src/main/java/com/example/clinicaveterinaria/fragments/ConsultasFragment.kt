package com.example.clinicaveterinaria.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clinicaveterinaria.LoginActivity
import com.example.clinicaveterinaria.R
import com.example.clinicaveterinaria.adapter.ComprasAdapter
import com.example.clinicaveterinaria.database.ComprasDAO
import com.example.clinicaveterinaria.databinding.FragmentConsultasBinding
import com.example.clinicaveterinaria.model.Compras

class ConsultasFragment : Fragment() {

    private lateinit var binding: FragmentConsultasBinding
    private var listaCompras = emptyList<Compras>()
    private var comprasAdapter: ComprasAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConsultasBinding.inflate(layoutInflater)
        val view = binding.root
        //recyclerView
        comprasAdapter = ComprasAdapter()
        binding.recyclerCompras.adapter = comprasAdapter
        binding.recyclerCompras.layoutManager = LinearLayoutManager(
            view.context
        )

        return view
    }

    private fun atualizarListaCompras() {
        val comprasDao = ComprasDAO(requireContext())
        val codigoRecebido = LoginActivity.codigoGlobal
        //listaCompras = comprasDao.listar()
        listaCompras = comprasDao.listarPorCdg(codigoRecebido)
        comprasAdapter?.adicionarLista(listaCompras)
    }

    override fun onStart() {
        super.onStart()
        atualizarListaCompras()
    }
}
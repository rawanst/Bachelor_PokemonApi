package com.app.papi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.app.papi.Common.ItemOffsetDecoration
import com.app.papi.Retrofit.IPokemonList
import com.app.papi.Retrofit.RetrofitClient
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import androidx.recyclerview.widget.RecyclerView
import com.app.papi.Common.Common
import com.pokemon.Adapter.PokemonListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PokemonList : Fragment() {

    internal var compositeDisposable = CompositeDisposable()
    internal lateinit var iPokemonList:IPokemonList
    internal lateinit var recycler_view:RecyclerView

    init{
        var retrofit : Retrofit? = RetrofitClient.instance
        if (retrofit != null) {
            iPokemonList = retrofit.create(IPokemonList::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView:View = inflater.inflate(R.layout.fragment_pokemon_list, container, false)
        recycler_view = itemView.findViewById(R.id.pokemon_recyclerview) as RecyclerView
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(activity, 1)
        val itemDecoration = ItemOffsetDecoration(activity!!, R.dimen.spacing)
        recycler_view.addItemDecoration(itemDecoration)
        fetchData()

        return itemView
    }

    private fun fetchData() {
        compositeDisposable.add(iPokemonList.listPokemon
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ pokemonDex ->
                Common.pokemonList = pokemonDex.pokemon!!
                val adapter = PokemonListAdapter(activity!!,Common.pokemonList)
                recycler_view.adapter = adapter

            }
        )
    }

}
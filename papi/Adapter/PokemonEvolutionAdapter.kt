package com.app.papi.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.app.papi.Common.Common
import com.app.papi.R
import com.app.papi.model.Evolution
import com.google.android.material.chip.Chip

class PokemonEvolutionAdapter(internal var context: Context, var evolutionList:List<Evolution>):
RecyclerView.Adapter<PokemonEvolutionAdapter.MyViewHolder>(){
    init {
        if(evolutionList == null)
            evolutionList =ArrayList()
    }
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        internal lateinit var chip:Chip
        init {
            chip = itemView.findViewById(R.id.chip) as Chip
            chip.setOnClickListener {
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Common.KEY_NUM_EVOLUTION).putExtra("num",evolutionList[adapterPosition].num))

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chip.chipText = evolutionList?.get(position)?.name
        holder.chip.setBackgroundColor(Common.getColorByType(Common.findPokemonByNum(evolutionList!![position].num!!)!!.type!![0]))
    }

    override fun getItemCount(): Int {
        return evolutionList!!.size
    }
}
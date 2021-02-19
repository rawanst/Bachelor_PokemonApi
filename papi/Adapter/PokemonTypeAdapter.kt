package com.app.papi.Adapter

import android.content.Context
import android.graphics.Color
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.app.papi.Common.Common
import com.app.papi.Interface.IItemClickListener
import com.app.papi.R
import com.google.android.material.chip.Chip


class PokemonTypeAdapter(internal var context: Context, internal var typeList:List<String>) :
RecyclerView.Adapter<PokemonTypeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chip.chipText = typeList[position]
        holder.chip.setBackgroundColor(Common.getColorByType(typeList[position]))

    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    inner class MyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView) {
        internal lateinit var chip: Chip
        internal var iItemClickListener:IItemClickListener?=null

        fun setItemClickListener(iItemClickListener: IItemClickListener)
        {
            this.iItemClickListener = iItemClickListener
        }
        init{
            chip = itemView.findViewById(R.id.chip) as Chip
            chip.setOnClickListener { Toast.makeText(context, "Clicked ",Toast.LENGTH_SHORT).show() }
        }
    }
}
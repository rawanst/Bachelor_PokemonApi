package com.app.papi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.papi.Common.Common
import com.app.papi.model.Pokemon

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        //Create BroadCast handle
        val showDetail = object:BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    if(intent.action!!.toString() == Common.KEY_ENABLE_HOME) {
                        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                        supportActionBar!!.setDisplayShowHomeEnabled(true)

                        //Replace Frangment
                        val detailFragment :PokemonDetail = PokemonDetail.getInstance()
                        val position:Int = intent.getIntExtra("position",-1)
                        val bundle = Bundle()
                        bundle.putInt("position",position)
                        detailFragment.arguments = bundle

                        val fragmentTransaction:FragmentTransaction = supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.list_pokemon_fragment,detailFragment)
                        fragmentTransaction.addToBackStack("detail")
                        fragmentTransaction.commit()

                        //Set Pokemon Name for Toolbar
                        val pokemon: Pokemon = Common.pokemonList[position]
                        val toolbar = findViewById<Toolbar>(R.id.toolbar)
                        toolbar.title = pokemon.name
                    }
                }
            }

        }

        val showEvolution = object:BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    if(intent.action!!.toString() == Common.KEY_NUM_EVOLUTION) {

                        //Replace Frangment
                        val detailFragment :PokemonDetail = PokemonDetail.getInstance()
                        val bundle = Bundle()
                        val num: String? = intent.getStringExtra("num")
                        bundle.putString("num",num)
                        detailFragment.arguments = bundle

                        val fragmentTransaction:FragmentTransaction = supportFragmentManager.beginTransaction()
                        fragmentTransaction.remove(detailFragment) //Remove current
                        fragmentTransaction.replace(R.id.list_pokemon_fragment,detailFragment)
                        fragmentTransaction.addToBackStack("detail")
                        fragmentTransaction.commit()

                        //Set Pokemon Name for Toolbar
                        val pokemon: Pokemon? = Common.findPokemonByNum(num)
                        val toolbar = findViewById<Toolbar>(R.id.toolbar)
                        toolbar.title = pokemon!!.name
                    }
                }
            }

        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Pokemon"
        setSupportActionBar(toolbar)

        //Register Broasdcast
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showEvolution, IntentFilter(Common.KEY_NUM_EVOLUTION))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId)
        {
            android.R.id.home -> {
                val toolbar = findViewById<Toolbar>(R.id.toolbar)
                toolbar.title = "Pokemon List"

                //Clear all fragment in stack whith name 'detail'
                supportFragmentManager.popBackStack("detail",FragmentManager.POP_BACK_STACK_INCLUSIVE)

                supportActionBar!!.setDisplayShowHomeEnabled(false)
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)

            }
        }
        return true
    }
}
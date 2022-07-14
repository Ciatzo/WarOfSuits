package com.cianjansen.warofsuits.ui.summary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cianjansen.warofsuits.databinding.ActivitySummaryBinding
import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.ui.views.TurnView

/**
 * Provides a summary of all the turns that happened in the previous game
 */
class SummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryBinding

    companion object {
        private const val EXTRA_TURN_LIST = "EXTRA_TURN_LIST"

        /**
         * Creates intent to SummaryActivity
         * @param turnList the list of turns that took place during the game, to be shown in this
         * summary activity
         */
        fun newIntent(
            context: Context,
            turnList: ArrayList<TurnSummary>
        ): Intent {
            val intent = Intent(context, SummaryActivity::class.java)
            intent.putParcelableArrayListExtra(EXTRA_TURN_LIST, turnList)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val turnList = intent.getParcelableArrayListExtra<TurnSummary>(EXTRA_TURN_LIST)

        turnList?.let {
            binding.rvTurns.adapter = TurnAdapter(it)
            binding.rvTurns.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * RecyclerView adapter for showing a summary of all turns that took place during the game
     */
    class TurnAdapter(private val turnList: ArrayList<TurnSummary>) :
        RecyclerView.Adapter<TurnAdapter.TurnViewHolder>() {
        override fun getItemCount(): Int {
            return turnList.size
        }

        /**
         * Creates the viewHolder for TurnSummary items with the custom TurnView class
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnViewHolder {
            val itemView = TurnView(parent.context)
            itemView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return TurnViewHolder(itemView)
        }

        /**
         * Binds the TurnSummary item to the TurnView. ArrayList index + 1 used for showing turn
         * number
         */
        override fun onBindViewHolder(holder: TurnViewHolder, position: Int) {
            holder.v.showTurn(turnList[position], position + 1)
        }

        /**
         * TurnViewHolder class for binding TurnSummary items
         */
        inner class TurnViewHolder(val v: TurnView) : RecyclerView.ViewHolder(v)
    }
}

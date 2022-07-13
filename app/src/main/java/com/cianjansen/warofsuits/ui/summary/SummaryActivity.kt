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
import com.cianjansen.warofsuits.ui.TurnView


class SummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryBinding

    companion object {
        private const val EXTRA_TURN_LIST = "EXTRA_TURN_LIST"

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

    class TurnAdapter(private val turnList: ArrayList<TurnSummary>) :
        RecyclerView.Adapter<TurnAdapter.ViewHolder>() {
        override fun getItemCount(): Int {
            return turnList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val itemView = TurnView(parent.context)
            itemView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.getCustomView().showTurn(turnList[position], position + 1)
        }

        inner class ViewHolder(private val v: TurnView) : RecyclerView.ViewHolder(v) {

            fun getCustomView(): TurnView {
                return v
            }
        }
    }
}

package com.cianjansen.warofsuits.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.PlayingCardView
import com.cianjansen.warofsuits.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity(), GameContract.View {
    private lateinit var binding: ActivityGameBinding

    private lateinit var presenter: GameContract.Presenter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setPresenter(presenter: GameContract.Presenter) {
        this.presenter = presenter
    }
}
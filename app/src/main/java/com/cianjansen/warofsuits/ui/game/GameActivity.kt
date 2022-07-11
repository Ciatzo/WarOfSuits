package com.cianjansen.warofsuits.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.databinding.ActivityGameBinding
import com.cianjansen.warofsuits.model.PlayingCard

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
        setPresenter(GamePresenter(this))

        binding.pcvYours.showCard(PlayingCard(PlayingCard.Suit.HEARTS, PlayingCard.Rank.KING))
        binding.pcvOpponent.showCard(PlayingCard(PlayingCard.Suit.CLUBS, PlayingCard.Rank.FIVE))

        binding.btDrawCardYours.setOnClickListener{ presenter.drawCards() }
    }

    override fun setPresenter(presenter: GameContract.Presenter) {
        this.presenter = presenter
    }

    override fun showCard(card: PlayingCard, yours: Boolean) {
        if (yours) {
            binding.pcvYours.showCard(card)
        } else {
            binding.pcvOpponent.showCard(card)
        }
    }

    override fun showScore(score: Int) {
        binding.tvDiscardYours.text = score.toString()
    }
}
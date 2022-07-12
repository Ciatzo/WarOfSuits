package com.cianjansen.warofsuits.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.R
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

    override fun hideCards(yours: Boolean) {
        val translationX = if (yours) {
            binding.pcvYours.width.toFloat()
        } else {
            -binding.pcvYours.width.toFloat()
        }

        val translationYYours = if (yours) {
            binding.pcvYours.height.toFloat()
        } else {
            -2 * binding.pcvYours.height.toFloat()
        }

        val translationYOpponent = if (yours) {
            2 * binding.pcvYours.height.toFloat()
        } else {
            -binding.pcvYours.height.toFloat()
        }

        val yourAnim = binding.pcvYours.animate()
            .translationY(translationYYours)
            .translationX(translationX)
            .alpha(0.0f)
        yourAnim.duration = 1000
        yourAnim.startDelay = 800

        yourAnim.withEndAction {
            val endAnim = binding.pcvYours.animate()
                .translationY(0F)
                .translationX(0F)
            endAnim.duration = 0
            endAnim.startDelay = 0

            binding.btDrawCardYours.isEnabled = true
            binding.btDrawCardOpponent.isEnabled = true
        }

        val opponentAnim = binding.pcvOpponent.animate()
            .translationY(translationYOpponent)
            .translationX(translationX)
            .alpha(0.0f)
        opponentAnim.duration = 1000
        opponentAnim.startDelay = 800

        opponentAnim.withEndAction {
            val endAnim = binding.pcvOpponent.animate()
                .translationY(0F)
                .translationX(0F)
            endAnim.duration = 0
            endAnim.startDelay = 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPresenter(GamePresenter(this))
        presenter.onViewCreated()

        binding.btDrawCardYours.setOnClickListener {
            presenter.drawCard(true)
            binding.btDrawCardYours.isEnabled = false
        }

        binding.btDrawCardOpponent.setOnClickListener {
            presenter.drawCard(false)
            binding.btDrawCardOpponent.isEnabled = false
        }
    }

    override fun setPresenter(presenter: GameContract.Presenter) {
        this.presenter = presenter
    }

    override fun showCard(card: PlayingCard?, yours: Boolean) {
        if (yours) {
            binding.pcvYours.showCard(card)
            binding.pcvYours.alpha = 1.0f
        } else {

            binding.pcvOpponent.showCard(card)
            binding.pcvOpponent.alpha = 1.0f
        }
    }

    override fun showScore(yourScore: Int, opponentScore: Int) {
        binding.tvDiscardYours.text = yourScore.toString()
        binding.tvDiscardOpponent.text = opponentScore.toString()
    }

    override fun showSuitOrder(suitOrder: String) {
        val suitOrderString = getString(R.string.suit_order) + "\n" + suitOrder
        binding.tvSuitOrderOpponent.text = suitOrderString
        binding.tvSuitOrderYours.text = suitOrderString
    }
}
package com.cianjansen.warofsuits.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ITurnBinding
import com.cianjansen.warofsuits.model.TurnSummary

/**
 * Custom view class for showing a summary of what happened in a certain turn
 */
class TurnView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding:ITurnBinding

    init {
        init(attrs)
    }

    /**
     * Renders a turn
     * @param summary the TurnSummary object to render
     * @param turnCount which turn number in the game this turn happened on
     */
    fun showTurn(summary: TurnSummary, turnCount: Int) {
        val yourCard = summary.first.toString()
        val opponentCard = summary.second.toString()
        val yours = summary.yours

        setLayoutText(yourCard, opponentCard, yours, turnCount.toString())
    }

    /**
     * Initializes the TurnView based on styleable attributes provided in the xml layout
     */
    private fun init(attrs: AttributeSet?) {
        binding = ITurnBinding.inflate(LayoutInflater.from(context), this, true)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TurnView)

        try {
            val yourCard = ta.getString(R.styleable.TurnView_yourCard) ?: ""
            val opponentCard = ta.getString(R.styleable.TurnView_opponentCard) ?: ""
            val turn = ta.getString(R.styleable.TurnView_turn) ?: ""
            val yours = ta.getBoolean(R.styleable.TurnView_yours, true)

            setLayoutText(yourCard, opponentCard, yours, turn)
        } finally {
            ta.recycle()
        }
    }

    /**
     * Sets the views of the layout to the right values
     * @param yourCard the card the "you" player played
     * @param opponentCard the card the "opponent" player played
     * @param yours whether the "you" player won the turn
     * @param turn the turn count of this turn
     */
    private fun setLayoutText(
        yourCard: String,
        opponentCard: String,
        yours: Boolean,
        turn: String
    ) {
        binding.tvOpponentCard.text =
            context.getString(R.string.turn_view_opponent, opponentCard)
        binding.tvYourCard.text =
            context.getString(R.string.turn_view_your, yourCard)
        binding.tvTitle.text =
            context.getString(R.string.turn_view_turn, turn)

        if (yours) {
            binding.ivYourResult.setImageDrawable(context.getDrawable(R.drawable.done_48px))
            binding.ivYourResult.setBackgroundColor(context.getColor(R.color.green_victory))

            binding.ivOpponentResult.setImageDrawable(
                context.getDrawable(R.drawable.close_48px)
            )
            binding.ivOpponentResult.setBackgroundColor(context.getColor(R.color.red_defeat))
        } else {
            binding.ivOpponentResult.setImageDrawable(context.getDrawable(R.drawable.done_48px))
            binding.ivOpponentResult.setBackgroundColor(context.getColor(R.color.green_victory))

            binding.ivYourResult.setImageDrawable(
                context.getDrawable(R.drawable.close_48px)
            )
            binding.ivYourResult.setBackgroundColor(context.getColor(R.color.red_defeat))
        }
    }
}

package com.cianjansen.warofsuits

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cianjansen.warofsuits.databinding.IPlayingCardBinding
import com.cianjansen.warofsuits.model.PlayingCard

class PlayingCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: IPlayingCardBinding

    init {
        init(attrs)
    }

    fun showCard(playingCard: PlayingCard) {
        val cardText = playingCard.rank.toString() + "\n" + playingCard.suit.toString()

        binding.tvTopLeft.text = cardText
        binding.tvBottomRight.text = cardText
    }

    private fun init(attrs: AttributeSet?) {
        binding = IPlayingCardBinding.inflate(LayoutInflater.from(context), this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.PlayingCardView)

        try {
            val suit = ta.getString(R.styleable.PlayingCardView_suit)
            val rank = ta.getString(R.styleable.PlayingCardView_rank)

            val cardText = rank + "\n" + suit

            binding.tvTopLeft.text = cardText
            binding.tvBottomRight.text = cardText
        } finally {
            ta.recycle()
        }
    }
}
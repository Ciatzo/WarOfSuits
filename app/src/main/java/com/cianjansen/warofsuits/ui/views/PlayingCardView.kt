package com.cianjansen.warofsuits.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.IPlayingCardBinding
import com.cianjansen.warofsuits.model.PlayingCard

/**
 * Custom view for showing PlayingCard objects
 */
class PlayingCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: IPlayingCardBinding

    init {
        init(attrs)
    }

    /**
     * Shows a playing card
     * @param playingCard the playing card to be shown. If null, the card is hidden
     */
    fun showCard(playingCard: PlayingCard?) {
        playingCard?.let {
            val cardText = it.rank.toString() +
                    "\n" +
                    it.suit.toString()

            binding.tvTopLeft.text = cardText
            binding.tvBottomRight.text = cardText

            binding.ivBackground.visibility = VISIBLE
            binding.tvTopLeft.visibility = VISIBLE
            binding.tvBottomRight.visibility = VISIBLE
        } ?: run {
            binding.ivBackground.visibility = GONE
            binding.tvTopLeft.visibility = GONE
            binding.tvBottomRight.visibility = GONE
        }
    }

    /**
     * Initializes the PlayingCardView based on the styleable attributes provided in the xml
     * layout file. If either of the attributes (suit and rank) is missing, the card is hidden
     */
    private fun init(attrs: AttributeSet?) {
        binding = IPlayingCardBinding.inflate(LayoutInflater.from(context), this)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PlayingCardView)

        try {
            val suit = ta.getString(R.styleable.PlayingCardView_suit)
            val rank = ta.getString(R.styleable.PlayingCardView_rank)

            if (suit.isNullOrEmpty() || rank.isNullOrEmpty()) {
                binding.ivBackground.visibility = GONE
                binding.tvTopLeft.visibility = GONE
                binding.tvBottomRight.visibility = GONE
            } else {
                val cardText = rank + "\n" + suit

                binding.tvTopLeft.text = cardText
                binding.tvBottomRight.text = cardText
            }
        } finally {
            ta.recycle()
        }
    }
}

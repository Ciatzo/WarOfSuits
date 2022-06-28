package com.cianjansen.warofsuits

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cianjansen.warofsuits.databinding.IPlayingCardBinding

class PlayingCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: IPlayingCardBinding

    private var rank: String? = null

    private var suit: String? = null


    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        binding = IPlayingCardBinding.inflate(LayoutInflater.from(context), this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.PlayingCardView)

        try {
            val suit = suit ?: ta.getString(R.styleable.PlayingCardView_suit)
            val rank = rank ?: ta.getString(R.styleable.PlayingCardView_rank)
            val color = ta.getColor(R.styleable.PlayingCardView_cardColor, 0)

            val cardText = rank + "\n" + suit

            binding.tvTopLeft.text = cardText
            binding.tvBottomRight.text = cardText
            binding.root.setBackgroundColor(color)
        } finally {
            ta.recycle()
        }
    }
}
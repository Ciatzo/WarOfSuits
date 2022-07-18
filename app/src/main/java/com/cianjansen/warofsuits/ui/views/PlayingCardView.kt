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

    companion object {
        private const val ANIMATION_CARD_GROW_SCALE = 1.3f

        private const val ANIMATION_END_ALPHA = 0f

        private const val ANIMATION_HIGHLIGHT_DURATION = 250L

        private const val ANIMATION_MOVE_DURATION = 500L

        private const val ANIMATION_RESET_DURATION = 0L

        private const val ANIMATION_RESET_FACTOR = 0f

        private const val ANIMATION_RESET_SCALE = 1f

        private const val ANIMATION_X_MOVE_FACTOR = 2
    }

    init {
        init(attrs)
    }

    /**
     * Shows an animation on this PlayingCardView that moves the card to the discardPile of
     * the winning player. The animation depends on which player won the turn (discard pile on right
     * in case of "you" player, on left for "opponent", and on whether the card being animated is
     * said winner ( Y translation is further for the card that lost)
     * @param yours whether the "you" player won this turn
     * @param thisCard whether this card won the turn
     * @param onEnd lambda function performed onAnimationEnd
     */
    fun moveToDiscardPile(yours: Boolean, thisCard: Boolean, onEnd: () -> Unit) {
        val translationX = if (yours) {
            binding.root.width.toFloat()
        } else {
            -binding.root.width.toFloat()
        }

        val translationY = when {
            yours && thisCard -> binding.root.height.toFloat()
            yours && !thisCard -> ANIMATION_X_MOVE_FACTOR * binding.root.height.toFloat()
            !yours && thisCard -> -binding.root.height.toFloat()
            else -> -ANIMATION_X_MOVE_FACTOR * binding.root.height.toFloat()

        }

        val animation = binding.root.animate()
            .translationY(translationY)
            .translationX(translationX)
            .alpha(ANIMATION_END_ALPHA)
        animation.duration = ANIMATION_MOVE_DURATION

        // Card is animated back to starting position invisibly after animation ends
        animation.withEndAction {
            val endAnim = binding.root.animate()
                .translationY(ANIMATION_RESET_FACTOR)
                .translationX(ANIMATION_RESET_FACTOR)
            endAnim.duration = ANIMATION_RESET_DURATION
            endAnim.startDelay = ANIMATION_RESET_DURATION

            onEnd()
        }
    }

    /**
     * Shows a playing card
     * @param playingCard the playing card to be shown. If null, the card is hidden
     */
    fun showCard(playingCard: PlayingCard) {
        val cardText = playingCard.rank.toString() +
                "\n" +
                playingCard.suit.toString()

        binding.tvTopLeft.text = cardText
        binding.tvBottomRight.text = cardText

        binding.ivBackground.visibility = VISIBLE
        binding.tvTopLeft.visibility = VISIBLE
        binding.tvBottomRight.visibility = VISIBLE

    }

    /**
     * Hides the PlayingCardView
     */
    fun showNoCard() {
        binding.ivBackground.visibility = GONE
        binding.tvTopLeft.visibility = GONE
        binding.tvBottomRight.visibility = GONE
    }

    fun showWinAnimation(endListener: () -> Unit) {
        val winAnim = binding.root.animate()
        winAnim
            .scaleX(ANIMATION_CARD_GROW_SCALE)
            .scaleY(ANIMATION_CARD_GROW_SCALE)
            .duration = ANIMATION_HIGHLIGHT_DURATION

        winAnim.withEndAction {
            val resetScaleAnim = binding.root.animate()

            resetScaleAnim
                .scaleX(ANIMATION_RESET_SCALE)
                .scaleY(ANIMATION_RESET_SCALE)
                .duration = ANIMATION_HIGHLIGHT_DURATION

            resetScaleAnim.withEndAction(endListener)
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

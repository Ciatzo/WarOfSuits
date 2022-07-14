package com.cianjansen.warofsuits.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ActivityGameBinding
import com.cianjansen.warofsuits.model.PlayingCard
import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.ui.views.TwoOptionDialog
import com.cianjansen.warofsuits.ui.victory.VictoryActivity

/**
 * The view class for the game, handles user input and card animations
 */
class GameActivity : AppCompatActivity(), GameContract.View {
    private lateinit var binding: ActivityGameBinding

    private lateinit var presenter: GameContract.Presenter

    /**
     * Defines constants to be used for animation and a newIntent function
     */
    companion object {
        private const val ANIMATION_END_ALPHA = 0f

        private const val ANIMATION_HIGHLIGHT_DURATION = 800L / 3

        private const val ANIMATION_GROW_SCALE = 1.3f

        private const val ANIMATION_MOVE_DURATION = 1000L / 2

        private const val ANIMATION_RESET_DURATION = 0L

        private const val ANIMATION_RESET_FACTOR = 0f

        private const val ANIMATION_RESET_SCALE = 1f

        private const val ANIMATION_START_ALPHA = 1f

        private const val ANIMATION_X_MOVE_FACTOR = 2

        /**
         * Creates intent to this activity
         * @return intent to gameActivity
         */
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }

    /**
     * Shows which player won by highlighting their card with an expanding "pop-up" animation
     */
    override fun showWinner(yours: Boolean) {
        val winAnim = if (yours) {
            binding.pcvYours.animate()
        } else {
            binding.pcvOpponent.animate()
        }

        winAnim
            .scaleX(ANIMATION_GROW_SCALE)
            .scaleY(ANIMATION_GROW_SCALE)
            .duration = ANIMATION_HIGHLIGHT_DURATION

        // withEndAction used for non-blocking delay between animation steps
        winAnim.withEndAction {
            val resetScaleAnim = if (yours) {
                binding.pcvYours.animate()
            } else {
                binding.pcvOpponent.animate()
            }

            resetScaleAnim
                .scaleX(ANIMATION_RESET_SCALE)
                .scaleY(ANIMATION_RESET_SCALE)
                .duration = ANIMATION_HIGHLIGHT_DURATION

            resetScaleAnim.withEndAction {
                hideCards(yours)
            }
        }
    }

    /**
     * Prevents accidental exits by presenting the user with a dialog
     */
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.game_activity_quit_title))
            .setMessage(getString(R.string.game_activity_quit))
            .setPositiveButton(getString(R.string.game_activity_quit_yes)) { _, _ -> finish() }
            .setNegativeButton(getString(R.string.game_activity_quit_no), null)
            .show()
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

        binding.btForfeitYours.setOnClickListener { showForfeitDialog(true) }
        binding.btForfeitOpponent.setOnClickListener { showForfeitDialog(false) }

        binding.btRestartYours.setOnClickListener { showRestartDialog(true) }
        binding.btRestartOpponent.setOnClickListener { showRestartDialog(false) }
    }

    override fun setPresenter(presenter: GameContract.Presenter) {
        this.presenter = presenter
    }

    /**
     * Shows the playing card drawn by the player on screen
     * @param card the playing card to be shown
     * @param yours whether the card was drawn by the "you" player or the "opponent" player
     */
    override fun showCard(card: PlayingCard?, yours: Boolean) {
        if (yours) {
            binding.pcvYours.showCard(card)
            binding.pcvYours.alpha = ANIMATION_START_ALPHA

            if (card == null) {
                binding.btDrawCardYours.isEnabled = true
            }
        } else {
            binding.pcvOpponent.showCard(card)
            binding.pcvOpponent.alpha = ANIMATION_START_ALPHA

            if (card == null) {
                binding.btDrawCardOpponent.isEnabled = true
            }
        }
    }

    /**
     * Shows the current score for both players
     * @param yourScore the score of the "you" player
     * @param opponentScore the score of the "opponent" player
     */
    override fun showScore(yourScore: Int, opponentScore: Int) {
        binding.tvDiscardYours.text = yourScore.toString()
        binding.tvDiscardOpponent.text = opponentScore.toString()
    }

    /**
     * Shows the current "suit order" being used in the game. The suit order is used to determine
     * which card wins in a case of two cards with the same rank. The suit order is a parameter of
     * the Deck being used, and is determined at the start of each game randomly
     */
    override fun showSuitOrder(suitOrder: String) {
        val suitOrderString = getString(R.string.suit_order) + "\n" + suitOrder
        binding.tvSuitOrderOpponent.text = suitOrderString
        binding.tvSuitOrderYours.text = suitOrderString
    }

    /**
     * Starts the victory activity to highlight winning player and final score
     * @param yourScore the score of the "you" player. OpponentScore not provided as the size of
     * the deck is known, and OpponentScore will be said size - yourScore. In case of a forfeit by
     * a player, the cards in both draw piles are put in the other players discard pile
     * @param turnList the list of all turns that happened over the course of the game, used to
     * provide the players with a summary
     */
    override fun startVictoryActivity(yourScore: Int, turnList: ArrayList<TurnSummary>) {
        startActivity(VictoryActivity.newIntent(this, yourScore, turnList))
        finish()
    }

    /**
     * Function for playing the animation that hides the cards. Cards are animated flying towards
     * the discard pile of the player that won the turn
     * @param yours whether the "you" player won the turn. False in case the "opponent" player won
     */
    private fun hideCards(yours: Boolean) {
        /*
         Both cards follow the same X translation, from the center towards the discard pile of the
          player that won the turn
         */
        val translationX = if (yours) {
            binding.pcvYours.width.toFloat()
        } else {
            -binding.pcvYours.width.toFloat()
        }

        /*
        The Y translation is different for the two cards. The card of the losing player has to fly
        further to reach the winning player's discard pile
         */
        val yTranslationYourCard = if (yours) {
            binding.pcvYours.height.toFloat()
        } else {
            -ANIMATION_X_MOVE_FACTOR * binding.pcvYours.height.toFloat()
        }

        val yTranslationOpponentCard = if (yours) {
            ANIMATION_X_MOVE_FACTOR * binding.pcvYours.height.toFloat()
        } else {
            -binding.pcvYours.height.toFloat()
        }

        val yourCardAnimation = binding.pcvYours.animate()
            .translationY(yTranslationYourCard)
            .translationX(translationX)
            .alpha(ANIMATION_END_ALPHA)
        yourCardAnimation.duration = ANIMATION_MOVE_DURATION

        // Cards are animated back to their starting position invisibly after animation ends
        yourCardAnimation.withEndAction {
            val endAnim = binding.pcvYours.animate()
                .translationY(ANIMATION_RESET_FACTOR)
                .translationX(ANIMATION_RESET_FACTOR)
            endAnim.duration = ANIMATION_RESET_DURATION
            endAnim.startDelay = ANIMATION_RESET_DURATION

            /*
            Card draw buttons get re-enabled at the end of the card animation, preventing players
             from drawing while a turn is still in progress
             */
            binding.btDrawCardYours.isEnabled = true
            binding.btDrawCardOpponent.isEnabled = true
        }

        val opponentCardAnimation = binding.pcvOpponent.animate()
            .translationY(yTranslationOpponentCard)
            .translationX(translationX)
            .alpha(ANIMATION_END_ALPHA)
        opponentCardAnimation.duration = ANIMATION_MOVE_DURATION

        opponentCardAnimation.withEndAction {
            val endAnim = binding.pcvOpponent.animate()
                .translationY(ANIMATION_RESET_FACTOR)
                .translationX(ANIMATION_RESET_FACTOR)
            endAnim.duration = ANIMATION_RESET_DURATION
            endAnim.startDelay = ANIMATION_RESET_DURATION
        }
    }

    /**
     * Show a dialog confirming whether the player wants to forfeit the game
     * @param yours used to determine which player is trying to forfeit. Used both to determine
     * which player wins, and whether to flip the dialog for the "opponent" player
     */
    private fun showForfeitDialog(yours: Boolean) {
        val onPositive = {
            presenter.onGameForfeited(yours)
        }

        TwoOptionDialog(
            this,
            !yours,
            onPositive,
            getString(R.string.game_activity_forfeit),
            getString(R.string.game_activity_forfeit_yes),
            getString(R.string.game_activity_forfeit_no)
        ).show()
    }

    /**
     * Show a dialog confirming whether the player wants to restart the game
     * @param yours used to determine which player is trying to restart. Used to determine
     * whether to flip the dialog for the "opponent" player
     */
    private fun showRestartDialog(yours: Boolean) {
        val onPositive = {
            presenter.onGameRestarted()
        }

        TwoOptionDialog(
            this,
            !yours,
            onPositive,
            getString(R.string.game_activity_restart),
            getString(R.string.game_activity_restart_yes),
            getString(R.string.game_activity_restart_no)
        ).show()
    }
}

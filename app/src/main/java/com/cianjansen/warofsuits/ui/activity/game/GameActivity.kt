package com.cianjansen.warofsuits.ui.activity.game

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ActivityGameBinding
import com.cianjansen.warofsuits.model.PlayingCard
import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.ui.activity.victory.VictoryActivity
import com.cianjansen.warofsuits.ui.dialog.TwoOptionDialog


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
        private const val ANIMATION_BOUNCE_DELAY = 3000L

        private const val ANIMATION_BOUNCE_GROW_SCALE = 1.1f

        private const val ANIMATION_BOUNCE_TIME = 1500L

        private const val ANIMATION_END_ALPHA = 0f

        private const val ANIMATION_MOVE_DURATION = 500L

        private const val ANIMATION_RESET_SCALE = 1f

        private const val ANIMATION_START_ALPHA = 1f

        /**
         * Creates intent to this activity
         * @return intent to gameActivity
         */
        fun newIntent(context: Context): Intent {
            return Intent(context, GameActivity::class.java)
        }
    }

    override fun hideOpponentCard() {
        binding.btDrawCardOpponent.isEnabled = true
        binding.pcvOpponent.showNoCard()
    }

    override fun hideYourCard() {
        binding.btDrawCardYours.isEnabled = true
        binding.pcvYours.showNoCard()
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
            hideBanner()
        }

        binding.btDrawCardOpponent.setOnClickListener {
            presenter.drawCard(false)
            binding.btDrawCardOpponent.isEnabled = false
            hideBanner()
        }

        binding.btForfeitYours.setOnClickListener { showForfeitDialog(true) }
        binding.btForfeitOpponent.setOnClickListener { showForfeitDialog(false) }

        binding.btRestartYours.setOnClickListener { showRestartDialog(true) }
        binding.btRestartOpponent.setOnClickListener { showRestartDialog(false) }

        showBounceAnimation(binding.tvTutorialBanner)
    }

    override fun setPresenter(presenter: GameContract.Presenter) {
        this.presenter = presenter
    }

    /**
     * Shows the playing card drawn by the "opponent" player on screen
     * @param card the playing card to be shown
     */
    override fun showOpponentCard(card: PlayingCard) {
        binding.pcvOpponent.showCard(card)
        binding.pcvOpponent.alpha = ANIMATION_START_ALPHA
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
     * Shows which player won by highlighting their card with an expanding "pop-up" animation
     */
    override fun showWinner(yours: Boolean) {
        if (yours) {
            binding.pcvYours.showWinAnimation { hideCards(yours) }
        } else {
            binding.pcvOpponent.showWinAnimation { hideCards(yours) }
        }
    }

    /**
     * Shows the playing card drawn by the "you" player on screen
     * @param card the playing card to be shown
     */
    override fun showYourCard(card: PlayingCard) {
        binding.pcvYours.showCard(card)
        binding.pcvYours.alpha = ANIMATION_START_ALPHA
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
     * Hides the tutorial banner
     */
    private fun hideBanner() {
        binding.tvTutorialBanner
            .animate()
            .alpha(ANIMATION_END_ALPHA)
            .duration = ANIMATION_MOVE_DURATION

        val bannerAnimation = binding.ivBanner
            .animate()
            .alpha(ANIMATION_END_ALPHA)
        bannerAnimation.duration = ANIMATION_MOVE_DURATION

        bannerAnimation.withEndAction {
            binding.ivBanner.visibility = View.GONE
            binding.tvTutorialBanner.visibility = View.GONE
        }
    }

    /**
     * Function for playing the animation that hides the cards. Cards are animated flying towards
     * the discard pile of the player that won the turn
     * @param yours whether the "you" player won the turn. False in case the "opponent" player won
     */
    private fun hideCards(yours: Boolean) {
        binding.pcvYours.moveToDiscardPile(yours, yours) {
            binding.btDrawCardYours.isEnabled = true
            binding.btDrawCardOpponent.isEnabled = true
        }

        binding.pcvOpponent.moveToDiscardPile(yours, !yours) {}
    }

    private fun showBounceAnimation(view: View) {
        val xObjectAnimator = ObjectAnimator.ofFloat(
            view,
            "scaleX",
            ANIMATION_RESET_SCALE,
            ANIMATION_BOUNCE_GROW_SCALE,
            ANIMATION_RESET_SCALE
        )
        xObjectAnimator.repeatCount = Animation.INFINITE

        val yObjectAnimator = ObjectAnimator.ofFloat(
            view,
            "scaleY",
            ANIMATION_RESET_SCALE,
            ANIMATION_BOUNCE_GROW_SCALE,
            ANIMATION_RESET_SCALE
        )
        yObjectAnimator.repeatCount = Animation.INFINITE

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(xObjectAnimator, yObjectAnimator)
        animatorSet.duration = ANIMATION_BOUNCE_TIME
        animatorSet.startDelay = ANIMATION_BOUNCE_DELAY
        animatorSet.start()
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

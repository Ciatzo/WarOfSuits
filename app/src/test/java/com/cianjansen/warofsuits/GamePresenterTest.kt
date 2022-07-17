package com.cianjansen.warofsuits

import com.cianjansen.warofsuits.ui.activity.game.GameActivity
import com.cianjansen.warofsuits.ui.activity.game.GamePresenter
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull


@RunWith(MockitoJUnitRunner::class)
class GamePresenterTest {
    @Mock
    private lateinit var mockGameActivity: GameActivity

    @Test
    fun showCardTest() {
        mockGameActivity = mock(GameActivity::class.java)
        val presenter = GamePresenter(mockGameActivity)

        presenter.drawCard(true)

        verify(mockGameActivity, times(1)).showCard(any(), eq(true))
        verify(mockGameActivity, times(1)).showCard(null, false)
    }

    /**
     * Tests that both players drawing a card leads to the score being updated
     */
    @Test
    fun afterTurnTest() {
        mockGameActivity = mock(GameActivity::class.java)
        val presenter = GamePresenter(mockGameActivity)

        presenter.drawCard(true)
        presenter.drawCard(false)

        verify(mockGameActivity, times(1)).showScore(any(), any())
        verify(mockGameActivity, times(1)).showWinner(any())
    }

    /**
     * Test that the scores are reported as 0-0 when no full turn has been completed yet
     */
    @Test
    fun showScoreNoTurnTest() {
        mockGameActivity = mock(GameActivity::class.java)
        val presenter = GamePresenter(mockGameActivity)

        presenter.drawCard(true)

        verify(mockGameActivity, times(1)).showScore(0, 0)
    }

    /**
     * Test that the scores are reported as 0-0 when the game gets reset
     */
    @Test
    fun showScoreGameReset() {
        mockGameActivity = mock(GameActivity::class.java)
        val presenter = GamePresenter(mockGameActivity)

        presenter.onGameRestarted()

        verify(mockGameActivity, times(1)).showScore(0, 0)
    }

    /**
     * Tests that forfeiting the game start the victoryActivity
     */
    @Test
    fun forfeitRoutineTest() {
        mockGameActivity = mock(GameActivity::class.java)
        val presenter = GamePresenter(mockGameActivity)

        presenter.onGameForfeited(true)

        verify(mockGameActivity, times(1))
            .startVictoryActivity(anyOrNull(), anyOrNull())
    }

    /**
     * Verify that the game ends only after all 26 turns (half the deck for each player) has been
     * drawn
     */
    @Test
    fun endOfGameTest() {
        mockGameActivity = mock(GameActivity::class.java)
        val presenter = GamePresenter(mockGameActivity)

        repeat(25) {
            presenter.drawCard(true)
            presenter.drawCard(false)
        }

        verify(mockGameActivity, times(0))
            .startVictoryActivity(anyOrNull(), anyOrNull())

        presenter.drawCard(true)
        presenter.drawCard(false)

        verify(mockGameActivity, times(1))
            .startVictoryActivity(anyOrNull(), anyOrNull())
    }

    /**
     * Verify that suit order is shown when a game is initialized
     */
    @Test
    fun viewCreatedSuitOrderTest() {
        mockGameActivity = mock(GameActivity::class.java)
        val presenter = GamePresenter(mockGameActivity)
        presenter.onViewCreated()

        verify(mockGameActivity, times(1))
            .showSuitOrder(anyOrNull())
    }

}
package com.cianjansen.warofsuits

import com.cianjansen.warofsuits.model.PlayingCard
import com.cianjansen.warofsuits.model.TurnSummary
import com.cianjansen.warofsuits.ui.activity.game.GameActivity
import com.cianjansen.warofsuits.ui.activity.game.GamePresenter
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.capture


@RunWith(MockitoJUnitRunner::class)
class GamePresenterTest {
    @Mock
    private lateinit var mockGameActivity: GameActivity

    @Captor
    private lateinit var listCaptor: ArgumentCaptor<ArrayList<TurnSummary>>

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun correctFinalScoreTest() {
        mockGameActivity = mock(GameActivity::class.java)
        val presenter = GamePresenter(mockGameActivity)

        repeat(26) {
            presenter.drawCard(true)
            presenter.drawCard(false)
        }

        val intCaptor = ArgumentCaptor.forClass(Int::class.java)
        verify(mockGameActivity).startVictoryActivity(
            capture(intCaptor),
            capture(listCaptor)
        )

        val turnList = listCaptor.value
        val yourScore = intCaptor.value


        assertEquals(turnList.filter { it.yours  }.size * 2, yourScore)
    }

    @Test
    fun showCardTest() {
        mockGameActivity = mock(GameActivity::class.java)
        val presenter = GamePresenter(mockGameActivity)

        presenter.drawCard(true)

        verify(mockGameActivity, times(1)).showYourCard(anyOrNull())
        verify(mockGameActivity, times(1)).hideOpponentCard()
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

        verify(mockGameActivity, times(2)).showScore(anyOrNull(), anyOrNull())
        verify(mockGameActivity, times(1)).showWinner(anyOrNull())
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
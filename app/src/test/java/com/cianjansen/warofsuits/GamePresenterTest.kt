package com.cianjansen.warofsuits

import com.cianjansen.warofsuits.ui.game.GameActivity
import com.cianjansen.warofsuits.ui.game.GamePresenter
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GamePresenterTest {
    @Mock
    private val mockGameActivity: GameActivity = mock(GameActivity::class.java)

    @Test
    fun drawCardTest() {
        val presenter = GamePresenter(mockGameActivity)

        presenter.drawCard(true)

        verify(mockGameActivity, times(1)).showCard(any(), eq(true))
    }

}
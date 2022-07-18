package com.cianjansen.warofsuits.ui.activity.main

/**
 * The presenter for the main activity. Handles user input delegation
 */
class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    override fun onStartClick() {
        view.startGame()
    }
}

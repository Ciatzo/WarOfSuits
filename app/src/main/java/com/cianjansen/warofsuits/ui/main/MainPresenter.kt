package com.cianjansen.warofsuits.ui.main

/**
 * The presenter for the main activity. Handles user input delegation
 */
class MainPresenter(private var view: MainContract.View) : MainContract.Presenter {
    override fun onStartClick() {
        view.startGame()
    }
}

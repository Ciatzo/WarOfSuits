package com.cianjansen.warofsuits.main


class MainPresenter(private var view: MainContract.View) : MainContract.Presenter {

    override fun onStartClick() {
        view.startGame()
    }

    override fun onViewCreated() {
        view.showWelcome()
    }
}
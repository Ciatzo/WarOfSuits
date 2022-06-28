package com.cianjansen.warofsuits


class MainPresenter(private var view: MainContract.View) : MainContract.Presenter {

    override fun onViewCreated() {
        view.showWelcome()
    }
}
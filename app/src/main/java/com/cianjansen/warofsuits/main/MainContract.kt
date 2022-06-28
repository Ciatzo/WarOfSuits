package com.cianjansen.warofsuits.main

import com.cianjansen.warofsuits.base.BasePresenter
import com.cianjansen.warofsuits.base.BaseView

class MainContract {
    interface Presenter : BasePresenter {
        fun onStartClick()

        fun onViewCreated()
    }

    interface View : BaseView<Presenter> {
        fun showWelcome()

        fun startGame()
    }
}
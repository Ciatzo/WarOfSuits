package com.cianjansen.warofsuits.ui.main

import com.cianjansen.warofsuits.ui.base.BasePresenter
import com.cianjansen.warofsuits.ui.base.BaseView

class MainContract {
    interface Presenter : BasePresenter {
        fun onStartClick()
    }

    interface View : BaseView<Presenter> {
        fun startGame()
    }
}

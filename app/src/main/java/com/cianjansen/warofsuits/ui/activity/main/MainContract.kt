package com.cianjansen.warofsuits.ui.activity.main

import com.cianjansen.warofsuits.ui.activity.base.BasePresenter
import com.cianjansen.warofsuits.ui.activity.base.BaseView

class MainContract {
    interface Presenter : BasePresenter {
        fun onStartClick()
    }

    interface View : BaseView<Presenter> {
        fun startGame()
    }
}

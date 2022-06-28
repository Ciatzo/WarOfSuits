package com.cianjansen.warofsuits

import com.cianjansen.warofsuits.base.BasePresenter
import com.cianjansen.warofsuits.base.BaseView

class MainContract {
    interface Presenter : BasePresenter {
        fun onViewCreated()
    }

    interface View : BaseView<Presenter> {
        fun showWelcome()
    }
}
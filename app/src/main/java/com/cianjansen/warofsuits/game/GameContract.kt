package com.cianjansen.warofsuits.game

import com.cianjansen.warofsuits.base.BasePresenter
import com.cianjansen.warofsuits.base.BaseView

class GameContract {
    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {

    }
}
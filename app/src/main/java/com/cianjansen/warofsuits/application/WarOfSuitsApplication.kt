package com.cianjansen.warofsuits.application

import android.app.Application
import com.cianjansen.warofsuits.dagger.DaggerApplicationComponent

class WarOfSuitsApplication : Application() {

    val wosComponent = DaggerApplicationComponent.create()
}

package com.cianjansen.warofsuits.dagger

import com.cianjansen.warofsuits.ui.game.GameActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun inject(target: GameActivity)
}
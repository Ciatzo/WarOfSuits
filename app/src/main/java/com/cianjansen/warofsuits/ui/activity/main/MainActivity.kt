package com.cianjansen.warofsuits.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.databinding.ActivityMainBinding
import com.cianjansen.warofsuits.ui.activity.game.GameActivity

/**
 * The main activity view. Welcomes players and allows starting a new game
 */
class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setPresenter(MainPresenter(this))

        binding.btStartGame.setOnClickListener {
            presenter.onStartClick()
        }
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun startGame() {
        startActivity(GameActivity.newIntent(this))
    }
}

package com.cianjansen.warofsuits.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ActivityMainBinding
import com.cianjansen.warofsuits.ui.game.GameActivity
import com.cianjansen.warofsuits.ui.victory.VictoryActivity
import com.google.android.material.snackbar.Snackbar

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
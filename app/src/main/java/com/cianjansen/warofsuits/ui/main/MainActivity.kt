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
        presenter.onViewCreated()

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.btStartGame.setOnClickListener {
            presenter.onStartClick()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun showWelcome() {
        Snackbar.make(binding.root, "Welcome ", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun startGame() {
        startActivity(VictoryActivity.newIntent(this, 26))
    }
}
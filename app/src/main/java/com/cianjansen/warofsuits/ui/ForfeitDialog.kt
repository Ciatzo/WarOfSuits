package com.cianjansen.warofsuits.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.cianjansen.warofsuits.R
import com.cianjansen.warofsuits.databinding.ActivityGameBinding
import com.cianjansen.warofsuits.databinding.DForfeitDialogBinding

class ForfeitDialog(
    context: Context,
    private val rotate: Boolean,
    private val onPositive: () -> Unit
) : Dialog(context) {
    private lateinit var binding: DForfeitDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DForfeitDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (rotate) {
            binding.clOuter.rotation = 180F
        }

        binding.tvYes.setOnClickListener {
            onPositive()
        }

        binding.tvNo.setOnClickListener {
            dismiss()
        }
    }

}
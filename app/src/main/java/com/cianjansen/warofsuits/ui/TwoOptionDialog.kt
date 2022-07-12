package com.cianjansen.warofsuits.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.cianjansen.warofsuits.databinding.DTwoOptionDialogBinding

class TwoOptionDialog(
    context: Context,
    private val rotate: Boolean,
    private val onPositive: () -> Unit,
    private val title: String,
    private val yesText: String,
    private val noText: String
) : Dialog(context) {
    private lateinit var binding: DTwoOptionDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DTwoOptionDialogBinding.inflate(layoutInflater)
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

        binding.tvNo.text = noText
        binding.tvTitle.text = title
        binding.tvYes.text = yesText
    }
}
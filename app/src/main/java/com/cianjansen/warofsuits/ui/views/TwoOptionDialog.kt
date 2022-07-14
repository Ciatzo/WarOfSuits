package com.cianjansen.warofsuits.ui.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.cianjansen.warofsuits.databinding.DTwoOptionDialogBinding

/**
 * Shows an AlertDialog with two options to choose from and a title
 * @param rotate determines whether the dialog should be rotated 180 degrees (to be readable for the
 * "opponent" player
 * @param onPositive lambda function passed to be executed if the user selects the positive option
 * @param title the title of the dialog
 * @param yesText the text to be shown on the positive button
 * @param noText the text to be shown on the negative button
 */
class TwoOptionDialog(
    context: Context,
    private val rotate: Boolean,
    private val onPositive: () -> Unit,
    private val title: String,
    private val yesText: String,
    private val noText: String
) : Dialog(context) {
    private lateinit var binding: DTwoOptionDialogBinding

    companion object {
        private const val FULL_TURN_DEGREES = 180F
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DTwoOptionDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (rotate) {
            binding.clOuter.rotation = FULL_TURN_DEGREES
        }

        // If the user selects the positive option, the passed lambda function is invoked
        binding.tvYes.setOnClickListener {
            dismiss()
            onPositive()
        }

        // If the user selects the negative option, the dialog is simply dismissed
        binding.tvNo.setOnClickListener {
            dismiss()
        }

        binding.tvNo.text = noText
        binding.tvTitle.text = title
        binding.tvYes.text = yesText
    }
}

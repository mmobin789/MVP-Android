package com.example.myapplication.trending.ui

import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.example.myapplication.databinding.UiErrorBinding

class ErrorView(private val uiErrorBinding: UiErrorBinding) {

    private lateinit var onRetryClick: () -> Unit

    init {
        uiErrorBinding.btnRetry.setOnClickListener {
            onRetryClick()
        }

        uiErrorBinding.ivL.repeatCount = LottieDrawable.INFINITE
    }


    private fun show() {
        uiErrorBinding.root.visibility = View.VISIBLE
        uiErrorBinding.ivL.playAnimation()

    }

    fun hide() {
        uiErrorBinding.root.visibility = View.GONE
        uiErrorBinding.ivL.pauseAnimation()
    }

    fun showMessage(message: String? = null) {
        if (!message.isNullOrBlank())
            uiErrorBinding.tvError.text = message
        show()
    }

    fun onRetryClick(onRetryClick: () -> Unit) {
        this.onRetryClick = onRetryClick
    }
}
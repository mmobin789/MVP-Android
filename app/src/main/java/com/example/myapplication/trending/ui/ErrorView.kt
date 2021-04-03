package com.example.myapplication.trending.ui

import android.app.AlertDialog
import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.example.myapplication.R
import com.example.myapplication.databinding.UiErrorBinding

class ErrorView(private val uiErrorBinding: UiErrorBinding) {

    private lateinit var mOnRetryClick: () -> Unit

    private val mContext = uiErrorBinding.root.context


    private val mNoInternetDialog by lazy {
        AlertDialog.Builder(mContext).setMessage(R.string.no_internet).setPositiveButton(
            android.R.string.ok
        ) { dialog, _ ->

            dialog.dismiss()
        }
            .create()

    }

    private fun setRetryButtonEnabled(enabled: Boolean) {
        uiErrorBinding.btnRetry.isEnabled = enabled

    }


    init {
        uiErrorBinding.btnRetry.setOnClickListener {
            mOnRetryClick()
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
        uiErrorBinding.tvError.text = if (!message.isNullOrBlank()) {
            message
        } else {
            mContext.getString(R.string.an_alien_is_probably_blocking_your_signal)
        }
        show()

    }

    fun showNoInternetDialog() {
        setRetryButtonEnabled(false)
        uiErrorBinding.tvError.setText(R.string.no_internet)
        if (!mNoInternetDialog.isShowing) {
            mNoInternetDialog.show()
        }
    }

    fun dismissNoInternetDialog() {
        setRetryButtonEnabled(true)
        uiErrorBinding.tvError.setText(R.string.internet_available)
        if (mNoInternetDialog.isShowing) {
            mNoInternetDialog.dismiss()
        }
    }


    fun onRetryClick(onRetryClick: () -> Unit) {
        mOnRetryClick = onRetryClick
    }
}
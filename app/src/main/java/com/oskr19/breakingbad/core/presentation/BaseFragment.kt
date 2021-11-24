package com.oskr19.breakingbad.core.presentation

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.oskr19.breakingbad.R

open class BaseFragment: Fragment(), StatusEvent {

    protected fun setTitle(title: String) {
        (requireActivity() as AppCompatActivity).apply {
            supportActionBar?.title = title
        }
    }

    protected fun setSubTitle(subtitle: String) {
        (requireActivity() as AppCompatActivity).apply {
            supportActionBar?.subtitle = subtitle
        }
    }

    protected var mDialog: Dialog? = null
    protected var mLoading: Dialog? = null

    protected fun showLoading(){
        if(mLoading?.isShowing == true) {
            mDialog?.dismiss()
        }
        mLoading = Dialog(requireContext())
        mLoading?.setContentView(R.layout.loading)
        mLoading?.setCancelable(false)
        mLoading?.show()
    }

    protected fun hideLoading(){
        if(mLoading?.isShowing == true) {
            mLoading?.hide()
        }
    }

    protected fun observeStatus(event: Event?) {
        event?.let {
            mLoading?.dismiss()
            hideLoading()
            when {
                it.isLoading() -> {
                    showLoading()
                }
                it.isFinished() -> {
                    hideLoading()
                }
                it.isDisconnected() -> {
                    onDisconnected()
                }
                else -> { onError(it) }
            }
        }
    }

    override fun onLoading() {
        showLoading()
    }

    override fun onFinished() {
        hideLoading()
    }

    override fun onError(event: Event?) {
        mDialog = DialogWindow.dialogOnError(requireContext())
        mDialog?.show()
    }

    override fun onDisconnected() {
        mDialog?.dismiss()
        mDialog = DialogWindow.dialogDisconnected(requireContext())
        mDialog?.show()
    }
}
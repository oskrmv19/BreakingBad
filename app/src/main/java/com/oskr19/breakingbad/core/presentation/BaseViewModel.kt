package com.oskr19.breakingbad.core.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oskr19.breakingbad.core.ErrorMessages.MSG_GENERIC_ERROR
import com.oskr19.breakingbad.core.domain.Failure
import timber.log.Timber

open class BaseViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val _status = MutableLiveData<Event>()
    val status: LiveData<Event> get() = _status

    fun handleFailure(failure: Throwable) {
        when (failure) {
            is Failure.NoConnection -> {
                postEventDisconnected()
            }
            is Failure -> {
                Timber.e(failure)
                postEventError(failure)
            }
            else -> {
                Timber.e(failure)
                postEventError(Failure.ServerError(MSG_GENERIC_ERROR))
            }
        }
    }

    protected fun setCustomEvent(event: Event){
        _status.postValue(event)
    }

    protected fun postEventLoading(){
        _status.postValue(Event.LOADING)
    }

    protected fun postEventFinished(){
        _status.postValue(Event.FINISHED)
    }

    protected fun postEventDisconnected(){
        _status.postValue(Event.DISCONNECTED)
    }

    protected fun postEventError(failure: Failure) {
        _status.postValue(Event.ERROR(failure))
    }
}
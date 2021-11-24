package com.oskr19.breakingbad.core.presentation

interface DialogAction {
    fun onPositive()
}

interface NegativeAction : DialogAction {
    fun onNegative()
}

interface FullActions : NegativeAction
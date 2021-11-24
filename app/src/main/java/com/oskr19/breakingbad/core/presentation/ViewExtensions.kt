package com.oskr19.breakingbad.core.presentation

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.oskr19.breakingbad.R

fun ImageView.loadImage(uri: String?) {
    Glide.with(context)
        .load(uri)
        .transform(GranularRoundedCorners(15f, 15f, 15f, 15f))
        .placeholder(getProgressDrawable(context))
        .error(R.drawable.ic_user)
        .into(this)
}

private fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        setColorSchemeColors(Color.parseColor("#FBB045"))
        start()
    }
}
package com.github.adamr22.notes_app.adapters

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("android:visibility")
fun inflatePhotoIfVisible(view: ImageView, photoUri: Uri?) {
    if (photoUri == null) view.visibility = View.GONE

    photoUri?.let {
        view.visibility = View.VISIBLE
        Picasso.get().load(photoUri).into(view)
    }
}

@BindingAdapter("android:text")
fun showTextIfVisible(textView: TextView, text: String?) {
    text?.let {
        textView.text = it
    }
}
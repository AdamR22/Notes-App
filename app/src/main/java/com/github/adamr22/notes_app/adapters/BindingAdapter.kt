package com.github.adamr22.notes_app.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:text")
fun showTextIfVisible(textView: TextView, text: String?) {
    text?.let {
        textView.text = it
    }
}
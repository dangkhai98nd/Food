package com.test.food.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("helperTextId")
fun TextInputLayout.setHelperTextId(textId : Int?) {
    if (textId == null) {
        helperText = ""
        return
    }
    helperText = context.getString(textId)
}

@BindingAdapter("srcId")
fun ImageView.setSrcId(srcId : Int) {
    setImageResource(srcId)
}

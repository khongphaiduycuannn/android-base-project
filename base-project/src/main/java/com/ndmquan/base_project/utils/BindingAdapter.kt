package com.ndmquan.base_project.utils


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ndmquan.base_project.widgets.callback.OnPreventDoubleClickListener
import java.io.File
import java.nio.ByteBuffer

@BindingAdapter(
    value = ["url", "uri", "file", "drawableRes", "drawable", "bitmap", "byteArray", "byteBuffer"],
    requireAll = false
)
fun ImageView.loadImage(
    url: String? = null,
    uri: Uri? = null,
    file: File? = null,
    drawableRes: Int? = null,
    drawable: Drawable? = null,
    bitmap: Bitmap? = null,
    byteArray: ByteArray? = null,
    byteBuffer: ByteBuffer? = null,
) {
    when {
        url != null -> Glide.with(context).load(url).into(this)
        uri != null -> Glide.with(context).load(uri).into(this)
        file != null -> Glide.with(context).load(file).into(this)
        drawableRes != null -> Glide.with(context).load(drawableRes).into(this)
        drawable != null -> Glide.with(context).load(drawable).into(this)
        bitmap != null -> Glide.with(context).load(bitmap).into(this)
        byteArray != null -> Glide.with(context).load(byteArray).into(this)
        byteBuffer != null -> Glide.with(context).load(byteBuffer).into(this)
    }
}

@BindingAdapter("bind:onPreventDoubleClick")
fun setOnPreventDoubleClickListener(view: View, onClickAction: OnClickListener?) {
    onClickAction?.let {
        view.setOnClickListener(OnPreventDoubleClickListener {
            onClickAction.onClick(view)
        })
    }
}
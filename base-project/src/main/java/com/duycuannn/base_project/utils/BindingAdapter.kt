package com.duycuannn.base_project.utils


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.duycuannn.base_project.utils.extensions.setPreventDoubleClick
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

        drawableRes != null -> Glide.with(context).load(drawableRes)
            .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(this)

        drawable != null -> Glide.with(context).load(drawable).into(this)

        bitmap != null -> Glide.with(context).load(bitmap).into(this)

        byteArray != null -> Glide.with(context).load(byteArray).into(this)

        byteBuffer != null -> Glide.with(context).load(byteBuffer).into(this)
    }
}

@BindingAdapter(
    value = ["stringRes", "fontRes", "spannedText", "underline"],
    requireAll = false
)
fun TextView.loadText(
    stringRes: Int? = null,
    fontRes: Int? = null,
    spanned: Spanned? = null,
    underline: Boolean? = null,
) {
    if (stringRes != null) {
        text = context.getString(stringRes)
    }

    if (fontRes != null) {
        typeface = ResourcesCompat.getFont(context, fontRes)
    }

    if (spanned != null) {
        text = spanned
    }

    if (underline == true) {
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            UnderlineSpan(),
            0,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannableString
    }
}

@BindingAdapter("onPreventDoubleClick")
fun setOnPreventDoubleClickListener(view: View, onClickAction: OnClickListener?) {
    onClickAction?.let {
        view.setPreventDoubleClick {
            onClickAction.onClick(view)
        }
    }
}
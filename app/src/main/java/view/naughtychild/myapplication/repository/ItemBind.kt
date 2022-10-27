package view.naughtychild.myapplication.repository

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import okhttp3.HttpUrl.Companion.toHttpUrl
import view.naughtychild.myapplication.R

private const val TAG = "ItemBind"

object ItemBind {

    @JvmStatic
    @BindingAdapter(value = ["android:imgUrl"])
    fun setUserPhoto(
        iView: ImageView,
        imageUrl: String?
    ) {
        Log.d(TAG, "setUserPhoto:$imageUrl ")
        if (imageUrl.isNullOrEmpty()) {
            Picasso.get().load(R.mipmap.ic_launcher)
                .into(iView)
        } else {
            Picasso.get().load(imageUrl)
                .into(iView)
        }
    }
}
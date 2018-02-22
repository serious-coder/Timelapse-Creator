package io.github.sdsstudios.timelapsecreator

import android.content.res.Resources

/**
 * Created by sethsch1 on 21/02/18.
 */
object DisplayUtils {
    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}
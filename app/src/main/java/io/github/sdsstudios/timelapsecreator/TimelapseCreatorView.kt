package io.github.sdsstudios.timelapsecreator

import android.net.Uri

/**
 * Created by Seth on 19/02/18.
 */

interface TimelapseCreatorView {
    var directory: String
    var timelapseName: String
    var framesPerSecond: Int
    val uriList: List<Uri>

    fun anyErrors(): Boolean
    fun changeProgressBar()
    fun showSnackbarMessage(stringId: Int)
}
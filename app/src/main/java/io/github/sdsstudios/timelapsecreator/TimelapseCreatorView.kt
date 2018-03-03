package io.github.sdsstudios.timelapsecreator

import android.net.Uri

/**
 * Created by Seth on 19/02/18.
 */

interface TimelapseCreatorView {
    val timelapseName: String
    val framesPerSecond: Int
    val startNumber: Int
    val frameCount: Int
    val imageName: String
    val privateDir: String
    var inputUri: Uri?
    var outputUri: Uri?

    fun anyErrors(): Boolean
    fun changeProgressBar()
    fun showSnackbarMessage(stringId: Int)
}
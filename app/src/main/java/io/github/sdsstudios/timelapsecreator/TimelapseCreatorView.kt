package io.github.sdsstudios.timelapsecreator

/**
 * Created by Seth on 19/02/18.
 */

interface TimelapseCreatorView {
    var imageDirectory: String
    var outputDirectory: String
    val timelapseName: String
    val framesPerSecond: Int
    val startNumber: Int
    val frameCount: Int

    fun anyErrors(): Boolean
    fun changeProgressBar()
    fun showSnackbarMessage(stringId: Int)
}
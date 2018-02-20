package io.github.sdsstudios.timelapsecreator

/**
 * Created by sethsch1 on 19/02/18.
 */

interface TimelapseCreatorView {
    var directory: String
    var fileName: String
    var frameCount: Int
    var framesPerSecond: Int
    var startNumber: Int

    val anyErrors: Boolean

    fun changeProgressBar()
    fun showMessage(stringId: Int)
}
package io.github.sdsstudios.timelapsecreator

/**
 * Created by sethsch1 on 19/02/18.
 */

interface TimelapseCreatorView {
    var directory: String
    var timelapseName: String
    var framesPerSecond: Int

    fun anyErrors(): Boolean
    fun changeProgressBar()
    fun showMessage(stringId: Int)
}
package io.github.sdsstudios.timelapsecreator

/**
 * Created by Seth on 19/02/18.
 */

interface TimelapseCreatorView {
    var directory: String
    var timelapseName: String
    var framesPerSecond: Int
    var imageType: ImageType

    fun anyErrors(): Boolean
    fun changeProgressBar()
    fun showMessage(stringId: Int)
}
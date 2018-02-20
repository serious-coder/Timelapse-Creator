package io.github.sdsstudios.timelapsecreator

import android.os.Bundle

/**
 * Created by sethsch1 on 19/02/18.
 */

class TimelapseManager(private val mView: TimelapseCreatorView) {

    companion object {
        private const val KEY_DIRECTORY = "directory"
        private const val KEY_TIMELAPSE_NAME = "timelapse_name"
        private const val KEY_FRAMES_PER_SECOND = "fps"
    }

    fun createTimelapse() {
        if (!mView.anyErrors()) {

        }
    }

    fun restoreState(bundle: Bundle) {
        if (bundle.getString(KEY_DIRECTORY) != null) {
            mView.directory = bundle.getString(KEY_DIRECTORY)
        }

        mView.timelapseName = bundle.getString(KEY_TIMELAPSE_NAME)
        mView.framesPerSecond = bundle.getInt(KEY_FRAMES_PER_SECOND)
    }

    fun saveState(bundle: Bundle): Bundle {

        bundle.putString(KEY_DIRECTORY, mView.directory)
        bundle.putString(KEY_TIMELAPSE_NAME, mView.timelapseName)
        bundle.putInt(KEY_FRAMES_PER_SECOND, mView.framesPerSecond)

        return bundle
    }

    fun chooseDirectory() {
        mView.openDocumentsUI()
    }
}
package io.github.sdsstudios.timelapsecreator

import android.os.Bundle

/**
 * Created by sethsch1 on 19/02/18.
 */

class TimelapseManager(private val mView: TimelapseCreatorView) {

    companion object {
        private const val KEY_DIRECTORY = "directory"
        private const val KEY_FILE_NAME = "file_name"
        private const val KEY_FRAME_COUNT = "frame_count"
        private const val KEY_FRAMES_PER_SECOND = "fps"
        private const val KEY_START_NUMBER = "start_number"
    }

    fun createTimelapse() {
        if (!mView.anyErrors) {

        }
    }

    fun restoreState(bundle: Bundle) {
        if (bundle.getString(KEY_DIRECTORY) != null) {
            mView.directory = bundle.getString(KEY_DIRECTORY)
        }

        mView.fileName = bundle.getString(KEY_FILE_NAME)
        mView.frameCount = bundle.getInt(KEY_FRAME_COUNT)
        mView.framesPerSecond = bundle.getInt(KEY_FRAMES_PER_SECOND)
        mView.startNumber = bundle.getInt(KEY_START_NUMBER)
    }

    fun saveState(bundle: Bundle): Bundle {

        bundle.putString(KEY_DIRECTORY, mView.directory)
        bundle.putString(KEY_FILE_NAME, mView.fileName)
        bundle.putInt(KEY_FRAME_COUNT, mView.frameCount)
        bundle.putInt(KEY_FRAMES_PER_SECOND, mView.framesPerSecond)
        bundle.putInt(KEY_START_NUMBER, mView.startNumber)

        return bundle
    }
}
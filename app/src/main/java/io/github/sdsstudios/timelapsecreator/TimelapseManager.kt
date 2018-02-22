package io.github.sdsstudios.timelapsecreator

/**
 * Created by Seth on 19/02/18.
 */

class TimelapseManager(private val mView: TimelapseCreatorView) {

    fun createTimelapse() {
        if (mView.anyErrors()) {
            mView.showSnackbarMessage(R.string.errors_need_solving)
            return
        }

        if (mView.uriList.isEmpty()) {
            mView.showSnackbarMessage(R.string.error_must_select_images)
            return
        }
    }
}
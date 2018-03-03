package io.github.sdsstudios.timelapsecreator

import android.net.Uri
import java.io.File

/**
 * Created by Seth on 19/02/18.
 */

class TimelapseManager(private val mView: TimelapseCreatorView) {

    private val TEMP_DIR by lazy { "${mView.privateDir}/TEMP" }

    fun createTimelapse() {
        if (mView.anyErrors()) {
            mView.showSnackbarMessage(R.string.errors_need_solving)
            return
        }

        copyFilesToTempFolder(mView.inputUri!!)
    }

    private fun copyFilesToTempFolder(uri: Uri) {
        createTempFolder()

        val dirPath = FileUtils.getDirPath(uri)
    }

    private fun createTempFolder() {
        File(TEMP_DIR).mkdirs()
    }

    private fun createImageName(number: Int): String {
        return mView.imageName.replace(
                mView.getStringRes(R.string.num_placeholder),
                number.toString()
        )
    }
}
package io.github.sdsstudios.timelapsecreator

import android.net.Uri
import android.os.Environment

/**
 * Created by Seth on 24/02/18.
 */

object FileUtils {
    fun getFilePath(uri: Uri): String {
        val path = uri.path.removePrefix("/tree/")

        val splitPath = path.split(':')
        val prefix = splitPath[0]

        return if (prefix == "primary") {
            val extStoragePath = Environment.getExternalStorageDirectory().path
            "$extStoragePath/${splitPath[1]}"
        } else {
            val mntPath = "/mnt/media_rw"
            "$mntPath/$prefix/${splitPath[1]}"
        }
    }
}
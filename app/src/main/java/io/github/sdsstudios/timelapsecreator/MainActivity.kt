package io.github.sdsstudios.timelapsecreator

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity(), TimelapseCreatorView {

    companion object {
        private const val REQUEST_CODE_SELECT_IMAGE_DIRECTORY = 34
        private const val REQUEST_CODE_SELECT_OUTPUT_DIRECTORY = 56
    }

    override var imageDirectory: String
        get() = textViewImageDirectory.text.toString()
        set(value) {
            textViewImageDirectory.text = value
        }

    override var outputDirectory: String
        get() = textViewOutputDirectory.text.toString()
        set(value) {
            textViewOutputDirectory.text = value
        }

    override val timelapseName: String
        get() = editTextTimelapseName.text.toString()

    override val framesPerSecond: Int
        get() = editTextFPS.text.toString().toIntOrZero()

    override val startNumber: Int
        get() = editTextStartNumber.text.toString().toIntOrZero()

    override val frameCount: Int
        get() = editTextTimelapseName.text.toString().toIntOrZero()

    private val mTimelapseManager = TimelapseManager(this)

    private val mTextInputLayoutList by lazy {
        arrayOf(
                textInputLayoutFPS,
                textInputLayoutTimelapseName,
                textInputLayoutFrameCount,
                textInputLayoutStartNumber,
                textInputLayoutImageName
        )
    }

    private val TEMP_DIR by lazy { "$filesDir/TEMP" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonChooseImageDirectory.setOnClickListener {
            openDocumentsUI(REQUEST_CODE_SELECT_IMAGE_DIRECTORY)
        }

        buttonChooseOutputDirectory.setOnClickListener {
            openDocumentsUI(REQUEST_CODE_SELECT_OUTPUT_DIRECTORY)
        }

        buttonCreate.setOnClickListener { mTimelapseManager.createTimelapse() }

        mTextInputLayoutList.forEach {
            it.checkForErrors()
            it.onTextChanged { it.checkForErrors() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                REQUEST_CODE_SELECT_IMAGE_DIRECTORY -> {
                    imageDirectory = data!!.data.path
                    copyFilesToTempFolder(data.data)
                }
                REQUEST_CODE_SELECT_OUTPUT_DIRECTORY -> {
                    outputDirectory = data!!.data.path
                }
            }
        }
    }

    override fun anyErrors(): Boolean {
        if (textViewImageDirectory.text.isBlank() || textViewOutputDirectory.text.isBlank()) {
            return true
        }

        return mTextInputLayoutList.any { it.error != null }
    }

    override fun changeProgressBar() {

    }

    override fun showSnackbarMessage(stringId: Int) {
        Snackbar.make(
                constraintLayout,
                stringId,
                Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun String.toIntOrZero(): Int {
        try {
            return this.toInt()

        } catch (e: NumberFormatException) {

            return 0
        }
    }

    private fun TextInputLayout.checkForErrors() {
        val text = editText!!.text

        if (text.isNullOrBlank()) {
            error = getString(R.string.error_cant_be_empty)

            return
        }

        val inputTypeIsInt = editText!!.inputType == InputType.TYPE_CLASS_NUMBER

        if (inputTypeIsInt && text.toString().toInt() == 0) {
            error = getString(R.string.error_cant_equal_zero)
            return
        }

        error = null
    }

    private fun TextInputLayout.onTextChanged(onTextChanged: () -> Unit) {
        editText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged()
            }
        })
    }

    private fun copyFilesToTempFolder(uri: Uri) {
        createTempFolder()
    }

    private fun createTempFolder(){
        File(TEMP_DIR).mkdirs()
    }

    private fun openDocumentsUI(requestCode: Int) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, requestCode)
    }
}

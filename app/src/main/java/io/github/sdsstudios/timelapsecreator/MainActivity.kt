package io.github.sdsstudios.timelapsecreator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.coroutines.experimental.buildSequence



class MainActivity : AppCompatActivity(), TimelapseCreatorView {

    companion object {
        private const val CHOOSE_DIR_REQUEST_CODE = 123

        private const val KEY_DIRECTORY = "directory"
        private const val KEY_TIMELAPSE_NAME = "timelapse_name"
        private const val KEY_FRAMES_PER_SECOND = "fps"
        private const val KEY_IMAGES = "images"
    }

    override var directory: String = ""

    override var timelapseName: String
        get() = editTextTimelapseName.text.toString()
        set(value) {
            editTextTimelapseName.setText(value)
        }

    override var framesPerSecond: Int
        get() = editTextFPS.text.toString().toIntOrZero()
        set(value) {
            if (value != 0) editTextFPS.setText(value.toString())
        }

    private val mImageAdapter by lazy {
        ImageAdapter(applicationContext, ArrayList())
    }

    private val mTimelapseManager = io.github.sdsstudios.timelapsecreator.TimelapseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        textInputLayoutFPS.checkForErrors()
        textInputLayoutTimelapseName.checkForErrors()

        textInputLayoutFPS.onTextChanged { textInputLayoutFPS.checkForErrors() }
        textInputLayoutTimelapseName.onTextChanged { textInputLayoutTimelapseName.checkForErrors() }

        buttonChoosePhotos.setOnClickListener { chooseDirectory() }
        buttonCreate.setOnClickListener { mTimelapseManager.createTimelapse() }

        recyclerViewImages.layoutManager = GridLayoutManager(this, 2)
        recyclerViewImages.adapter = mImageAdapter
    }

    override fun onSaveInstanceState(outState: Bundle?) {

        outState!!.putString(KEY_DIRECTORY, directory)
        outState.putString(KEY_TIMELAPSE_NAME, timelapseName)
        outState.putInt(KEY_FRAMES_PER_SECOND, framesPerSecond)
        outState.putStringArray(KEY_IMAGES, mImageAdapter.uriList.map { it.toString() }.toTypedArray())

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        directory = savedInstanceState!!.getString(KEY_DIRECTORY)
        timelapseName = savedInstanceState.getString(KEY_TIMELAPSE_NAME)
        framesPerSecond = savedInstanceState.getInt(KEY_FRAMES_PER_SECOND)

        mImageAdapter.uriList = savedInstanceState.getStringArray(KEY_IMAGES)
                .map { Uri.parse(it) }.toMutableList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_DIR_REQUEST_CODE && resultCode == RESULT_OK) {
            val uri = data!!.clipData

            if (uri == null) {
                Snackbar.make(
                        constraintLayout,
                        R.string.error_must_select_more_than_one,
                        Snackbar.LENGTH_SHORT
                ).show()

            } else {
                val uriList = buildSequence {
                    for (i in 0 until uri.itemCount) {
                        yield(uri.getItemAt(i).uri)
                    }
                }.toList()

                invalidateImageAdapter(uriList)
            }
        }
    }

    override fun changeProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(stringId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun anyErrors(): Boolean {
        return textInputLayoutTimelapseName.error != null || textInputLayoutFPS.error != null
    }

    private fun String.toIntOrZero(): Int {
        try {
            return this.toInt()

        } catch (e: NumberFormatException) {

            return 0
        }
    }

    private fun TextInputLayout.checkForErrors() {
        if (editText!!.text.isEmpty()) {
            error = getString(R.string.error_cant_be_empty)

            return
        }

        val inputTypeIsInt = editText!!.inputType == InputType.TYPE_CLASS_NUMBER

        if (inputTypeIsInt && editText!!.text.toString().toInt() == 0) {
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

    private fun chooseDirectory() {
        openDocumentsUI()
    }

    private fun openDocumentsUI() {
        val intent = Intent()

        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                CHOOSE_DIR_REQUEST_CODE
        )
    }

    private fun invalidateImageAdapter(uriList: List<Uri>) {
        mImageAdapter.uriList = uriList.toMutableList()
        mImageAdapter.notifyDataSetChanged()
    }
}

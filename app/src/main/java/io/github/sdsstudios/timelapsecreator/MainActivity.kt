package io.github.sdsstudios.timelapsecreator

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TimelapseCreatorView {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonChooseImageDirectory.setOnClickListener {

        }

        buttonChooseOutputDirectory.setOnClickListener {

        }

        buttonCreate.setOnClickListener { mTimelapseManager.createTimelapse() }

        mTextInputLayoutList.forEach {
            it.checkForErrors()
            it.onTextChanged { it.checkForErrors() }
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
}

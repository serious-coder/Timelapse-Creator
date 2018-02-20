package io.github.sdsstudios.timelapsecreator

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TimelapseCreatorView {

    override var directory: String
        get() = textViewDirectory.text.toString()
        set(value) {
            textViewDirectory.text = value
        }

    override var fileName: String
        get() = editTextFilename.text.toString()
        set(value) {
            editTextFilename.setText(value)
        }

    override var frameCount: Int
        get() = editTextFilename.text.toString().toIntOrZero()
        set(value) {
            if (value != 0) editTextFrameCount.setText(value.toString())
        }

    override var framesPerSecond: Int
        get() = editTextFPS.text.toString().toIntOrZero()
        set(value) {
            if (value != 0) editTextFPS.setText(value.toString())
        }

    override var startNumber: Int
        get() = editTextStartNumber.text.toString().toIntOrZero()
        set(value) {
            if (value != 0) editTextStartNumber.setText(value.toString())
        }

    override val anyErrors: Boolean
        get() = mTextInputLayoutArray.any { it.error != null }

    private val mTextInputLayoutArray by lazy {
        arrayOf(
                textInputLayoutFileName,
                textInputLayoutFPS,
                textInputLayoutFrameCount,
                textInputLayoutStartNumber
        )
    }

    private val mTimelapseManager = TimelapseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mTextInputLayoutArray.forEach { it.checkForErrors() }

        mTextInputLayoutArray.forEach { textInputLayout ->
            textInputLayout.onTextChanged {
                textInputLayout.checkForErrors()
            }
        }

        buttonCreate.setOnClickListener { mTimelapseManager.createTimelapse() }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(mTimelapseManager.saveState(outState!!))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        mTimelapseManager.restoreState(savedInstanceState!!)
    }

    override fun changeProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(stringId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
}

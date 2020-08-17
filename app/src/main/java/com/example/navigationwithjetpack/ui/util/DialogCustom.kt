package com.example.navigationwithjetpack.ui.util


import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import com.example.navigationwithjetpack.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

open class DialogCustom(
    private val activity: LayoutInflater,
    private val title: String
) {
    protected open var alertDialogView: View =
        activity.inflate(R.layout.alert_dialog, null).apply {
            findViewById<TextView>(R.id.textViewCadastro).text = title
        }

    protected open val builder = AlertDialog.Builder(activity.context).apply {
        setView(alertDialogView)
    }

    private val nameInputEditText by lazy { alertDialogView.findViewById(R.id.nameDivida) as TextInputEditText }
    private val nameInputLayout by lazy { alertDialogView.findViewById(R.id.layoutNameDivida) as TextInputLayout }
    private val valueInputEditText by lazy { alertDialogView.findViewById(R.id.valueDivida) as TextInputEditText }
    private val valueInputLayout by lazy { alertDialogView.findViewById(R.id.layoutValueDivida) as TextInputLayout }


    private var currentDialog: AlertDialog? = null
    var onDialogConfirmationListener: OnDialogConfirmationListener? = null
    private var nameTextWatcher: TextWatcher? = null


    var name: String
        get() = nameInputEditText.text?.toString() ?: ""
        set(value) = nameInputEditText.setText(value)

    var value: String
        get() = valueInputEditText.text?.toString() ?: ""
        set(value) = valueInputEditText.setText(value)


    fun showDialog() {
        if (currentDialog == null) currentDialog = builder.create()
        currentDialog?.show()
    }

    fun setNameTextChangedListener(newNameTextWatcher: TextWatcher?) {
        nameTextWatcher?.let {
            nameInputEditText.removeTextChangedListener(it)
        }

        newNameTextWatcher?.let {
            nameInputEditText.addTextChangedListener(it)
            nameTextWatcher = it
        }
    }

    fun setValueTextChangedListener(newNameTextWatcher: TextWatcher?) {
        nameTextWatcher?.let {
            valueInputEditText.removeTextChangedListener(it)
        }

        newNameTextWatcher?.let {
            valueInputEditText.addTextChangedListener(it)
            nameTextWatcher = it
        }
    }

    fun checkIfDataIsValid(): Boolean {
        return name.isNotEmpty() && !nameInputLayout.isErrorEnabled && value.isNotEmpty() && !valueInputLayout.isErrorEnabled
    }

    fun setSaveAndCancelButton() {
        builder.apply {
            setPositiveButton("Salvar") { dialog, _ ->
                val name: String = nameInputEditText.text.toString()
                val value: Double = valueInputEditText.text.toString().toDouble()
                onDialogConfirmationListener?.onConfirmation(
                    name,
                    value
                )
                dialog.dismiss()
            }
            setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        }
    }



    fun setPositiveButtonEnabled(isEnabled: Boolean) {
        currentDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = isEnabled
    }

    fun setNameError(hasError: Boolean?) {
        hasError?.let {
            nameInputLayout.isErrorEnabled = it
            if (it) nameInputLayout.error = "O nome da dívida não pode ser vazia"
        }
    }

    fun setValueError(hasError: Boolean?) {
        hasError?.let {
            valueInputLayout.isErrorEnabled = it
            if (it) valueInputLayout.error = "O valor da dívida não pode ser vazia"
        }
    }

    interface OnDialogConfirmationListener {
        fun onConfirmation(name: String, value: Double)
    }
}
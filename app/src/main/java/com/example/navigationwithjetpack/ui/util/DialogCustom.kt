package com.example.navigationwithjetpack.ui.util


import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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


    fun setSaveAndCancelButton() {
        builder.apply {
            setPositiveButton("Salvar") { dialog, _ ->
                val name = nameInputEditText.text.toString()
                val value = valueInputEditText.text.toString().toDouble()
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
//            if (it) nameInputLayout.error = ge(R.string.nome_invalido)
        }
    }

    interface OnDialogConfirmationListener {
        fun onConfirmation(name: String, value: Double)
    }
}
package com.nicoqueijo.dragaddremoveexample

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CurrencyRow(context: Context?, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    val scrollableArea: ConstraintLayout
    val currencyFlag: ImageView
    val currencyCode: TextView
    val currencyName: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.row_currency, this)
        scrollableArea = findViewById(R.id.scrollable_area)
        currencyFlag = findViewById(R.id.currency_flag)
        currencyCode = findViewById(R.id.currency_code)
        currencyName = findViewById(R.id.currency_name)
    }

    fun initRow(currency: Currency) {
        currencyFlag.setImageResource(currency.currencyFlag)
        currencyCode.text = currency.currencyCode
        currencyName.text = currency.currencyName
    }
}
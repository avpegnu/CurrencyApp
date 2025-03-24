package com.example.currencyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val tvSymbolFrom = findViewById<TextView>(R.id.tvSymbolFrom)
        val tvSymbolTo = findViewById<TextView>(R.id.tvSymbolTo)
        val tvRate = findViewById<TextView>(R.id.tvRate)

        val currencies = mapOf(
            "USD" to Pair("$", 1.0),
            "EUR" to Pair("€", 0.94),
            "GBP" to Pair("£", 0.78),
            "JPY" to Pair("¥", 166.8),
            "VND" to Pair("₫", 25400.0)
        )

        val currencyList = currencies.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        val convertCurrency = {
            val amount = edtAmount.text.toString().toDoubleOrNull() ?: 0.0
            val fromCurrency = spinnerFrom.selectedItem.toString()
            val toCurrency = spinnerTo.selectedItem.toString()
            val fromRate = currencies[fromCurrency]?.second ?: 1.0
            val toRate = currencies[toCurrency]?.second ?: 1.0
            val result = amount * (toRate / fromRate)

            tvResult.text = String.format("%.2f", result)
            tvSymbolFrom.text = currencies[fromCurrency]?.first
            tvSymbolTo.text = currencies[toCurrency]?.first
            tvRate.text = "1 $fromCurrency = ${String.format("%.2f", toRate / fromRate)} $toCurrency"
        }

        edtAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { convertCurrency() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerFrom.onItemSelectedListener = listener
        spinnerTo.onItemSelectedListener = listener
    }
}

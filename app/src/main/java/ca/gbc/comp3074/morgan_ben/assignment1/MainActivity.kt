package ca.gbc.comp3074.morgan_ben.assignment1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // connect kotlen vars to controls in layout
        val hoursInput = findViewById<EditText>(R.id.hoursInput)
        val hourlyRateInput = findViewById<EditText>(R.id.hourlyRateInput)
        val taxRateInput = findViewById<EditText>(R.id.taxRateInput)
        val calculateButton = findViewById<Button>(R.id.calculateButton)

        val payText = findViewById<TextView>(R.id.payText)
        val overtimePayText = findViewById<TextView>(R.id.overtimePayText)
        val totalPayText = findViewById<TextView>(R.id.totalPayText)
        val taxText = findViewById<TextView>(R.id.taxText)

        calculateButton.setOnClickListener {
            val hours = hoursInput.text.toString().toDoubleOrNull()
            val hourlyRate = hourlyRateInput.text.toString().toDoubleOrNull()
            val taxRate = taxRateInput.text.toString().toDoubleOrNull()

            // Stop calculating if an input isn't valid
            if (hours == null || !hours.isFinite() || hours < 0) {
                hoursInput.error = getString(R.string.hours_error)
                return@setOnClickListener
            }

            if (hourlyRate == null || !hourlyRate.isFinite() || hourlyRate < 0) {
                hourlyRateInput.error = getString(R.string.hourly_rate_error)
                return@setOnClickListener
            }

            if (taxRate == null || !taxRate.isFinite() || taxRate < 0 || taxRate > 100) {
                taxRateInput.error = getString(R.string.tax_rate_error)
                return@setOnClickListener
            }

            val pay: Double
            val overtimePay: Double

            if (hours <= 40) {
                pay = hours * hourlyRate
                overtimePay = 0.0
            } else {
                pay = 40 * hourlyRate
                overtimePay = (hours - 40) * hourlyRate * 1.5
            }

            val totalPay = pay + overtimePay

            // calculate tax only on regular pay
            val tax = pay * (taxRate / 100)

            payText.text = getString(R.string.pay_result, pay)
            overtimePayText.text = getString(R.string.overtime_result, overtimePay)
            totalPayText.text = getString(R.string.total_result, totalPay)
            taxText.text = getString(R.string.tax_result, tax)
        }
    }
}
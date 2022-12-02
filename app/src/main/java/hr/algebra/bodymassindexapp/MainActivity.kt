package hr.algebra.bodymassindexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import hr.algebra.bodymassindexapp.databinding.ActivityMainBinding
import hr.algebra.bodymassindexapp.utils.hideKeyboard
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var validationFields: List<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initValidation()
        setUpListeners()
    }

    private fun initValidation() {
        validationFields = listOf(
            binding.etWeight,
            binding.etHeight
        )
    }

    private fun setUpListeners() {
        binding.btnCalculate.setOnClickListener{
            if (formValid()){
                val bmi: Double? = calculate(
                    binding.etWeight.text.toString().toDouble(),
                    binding.etHeight.text.toString().toDouble()
                )
                handleLayout(bmi)
                hideKeyboard()
            }
        }

        binding.btnReset.setOnClickListener {
            resetForm()
        }
    }

    private fun resetForm() {
        binding.tvBmi.text = ""
        binding.ivBmi.setImageDrawable(null)
        binding.etWeight.text.clear()
        binding.etHeight.text.clear()
    }

    private fun formValid(): Boolean {
        validationFields.forEach {
            if (it.text.toString().isBlank()){
                it.error = getString(R.string.PleaseInsert)
                it.requestFocus()
                return false
            }
        }
        return true
    }

    private fun calculate(weight: Double, height: Double): Double? =
        if (weight <= 0 || height <= 0){
            null
        } else {
            val heightFinal = if(height > 3) height / 100 else height
            weight / heightFinal.pow(2)
        }

    private fun handleLayout(bmi: Double?) {
        when {
            bmi == null -> {
                binding.tvBmi.text = ""
                binding.ivBmi.setImageDrawable(null)
            }
            bmi <= 20 -> {
                binding.tvBmi.text = getString(R.string.UnderWeight)
                binding.ivBmi.setImageResource(R.drawable.sad)
            }
            bmi <= 25 -> {
                binding.tvBmi.text = getString(R.string.Normal)
                binding.ivBmi.setImageResource(R.drawable.happy)
            }
            else -> {
                binding.tvBmi.text = getString(R.string.OverWeight)
                binding.ivBmi.setImageResource(R.drawable.sad)
            }
        }
    }
}
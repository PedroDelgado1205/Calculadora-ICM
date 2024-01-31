package com.example.imcypesoideal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.findViewTreeViewModelStoreOwner

class MainActivity : AppCompatActivity() {

    private lateinit var app: TextView
    private lateinit var peso: TextView
    private lateinit var estatura: TextView
    private lateinit var resultado: TextView
    private lateinit var logo: ImageView
    private lateinit var valorkilos: EditText
    private lateinit var valormetros: EditText
    private lateinit var resultadoimc: EditText
    private lateinit var femenino: CheckBox
    private lateinit var masculino: CheckBox
    private lateinit var imc: Button
    private lateinit var pesoideal: Button
    private lateinit var borrar: Button
    var pi: Double = 0.0
    var piup: Double = 0.0
    var pidown: Double = 0.0
    var f1: Double = 2.25
    var f2: Double = 45.0
    var m1: Double = 2.7
    var m2: Double = 47.75
    var bandera: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        app = findViewById(R.id.tvTitulo)
        peso = findViewById(R.id.peso)
        estatura = findViewById(R.id.estatura)
        resultado = findViewById(R.id.tvResulta)
        logo = findViewById(R.id.logo)
        valorkilos = findViewById(R.id.pesovalor)
        valormetros = findViewById(R.id.estaturavalor)
        resultadoimc = findViewById(R.id.etResulta)
        femenino = findViewById(R.id.fem)
        masculino = findViewById(R.id.mas)
        imc = findViewById(R.id.buttomimc)
        pesoideal = findViewById(R.id.buttonideal)
        borrar = findViewById(R.id.buttonborrar)
    }

    fun imc(view: View) {
        if (valorkilos.text.toString().trim().isEmpty()) {
            valorkilos.requestFocus()

            Toast.makeText(this, "Favor de ingresar el peso.", Toast.LENGTH_LONG).show()

        }else if (valormetros.text.toString().trim().isEmpty()){
            valormetros.requestFocus()

            Toast.makeText(this, "Favor de ingresar la estatura.", Toast.LENGTH_LONG).show()
        }else{

            val kg: Float = valorkilos.text.toString().toFloatOrNull() ?: 0.0f
            val m: Float = valormetros.text.toString().toFloatOrNull() ?: 0.0f
            val bmi = (kg / (m * m))

            val categoria = when {
                bmi < 18.0 -> "Bajo Peso"
                bmi < 24.9 -> "Peso Normal"
                bmi < 29.9 -> "Sobre Peso"
                bmi < 34.9 -> "Obesidad"
                else -> "Muy Obeso"
            }

            val mensaje = "Tu IMC es: $bmi \n $categoria"
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
            resultadoimc.setText(bmi.toString())

        }
    }

    fun borrar(view: View) {
        valorkilos.setText("")
        valorkilos.requestFocus()
        valormetros.setText("")
        resultadoimc.setText("")
        pi = 0.0
        piup = 0.0
        pidown = 0.0
        femenino.isEnabled = true
        masculino.isEnabled = true
        femenino.isChecked = false
        masculino.isChecked = false
        bandera = true
    }

    fun pesoideal (view: View){
        val kg: Float = valorkilos.text.toString().toFloatOrNull() ?: 0.0f
        val m: Float = valormetros.text.toString().toFloatOrNull() ?: 0.0f

        if (valorkilos.text.toString().trim().isEmpty()) {
            valorkilos.requestFocus()

            Toast.makeText(this, "Favor de ingresar el peso.", Toast.LENGTH_LONG).show()
            bandera = false

        }else if (valormetros.text.toString().trim().isEmpty()){
            valormetros.requestFocus()

            Toast.makeText(this, "Favor de ingresar la estatura.", Toast.LENGTH_LONG).show()
            bandera = false

        }else if (masculino.isChecked == false && femenino.isChecked == false){
            Toast.makeText(this, "Favor de selecionar un genero.", Toast.LENGTH_LONG).show()
            bandera = false
        }
        if (femenino.isChecked) {
            masculino.isEnabled = false
            pi = ((((100 * m) - 152.4) / 2.54) * f1) + f2
        } else if (masculino.isChecked) {
            femenino.isEnabled = false
            pi = ((((100 * m) - 152.4) / 2.54) * m1) + m2
        }

        if (bandera){
            piup = (pi * 0.10) + pi
            Toast.makeText(this, "Resultado de peso ideal: $pi \n", Toast.LENGTH_LONG).show()
            resultadoimc.setText(pi.toString())
            pidown = (pi - (pi * 0.10))

            if (kg  > piup){
                Toast.makeText(this, "Estas arriba de tu peso ideal.", Toast.LENGTH_LONG).show()
            }else if (kg <= piup && kg >= pidown){
                Toast.makeText(this, "Estas en tu peso ideal.", Toast.LENGTH_LONG).show()
            }else if (kg < pidown){
                Toast.makeText(this, "Estas abajo de tu peso ideal.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun onCheckboxClickedfem(view: View){
        femenino.isChecked = true
        masculino.isChecked =  false
        Toast.makeText(this, "Femenino.", Toast.LENGTH_LONG).show()
    }

    fun onCheckboxClickedmas(view: View){
        masculino.isChecked =  true
        femenino.isChecked = false
        Toast.makeText(this, "Masculino.", Toast.LENGTH_LONG).show()
    }

}
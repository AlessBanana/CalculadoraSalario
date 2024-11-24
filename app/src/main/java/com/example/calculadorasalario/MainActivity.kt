package com.example.calculadorasalario

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.os.Build.VERSION_CODES.R


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular elementos de la vista
        val etSalarioBruto = findViewById<EditText>(R.id.etSalarioBruto)
        val etNumPagas = findViewById<EditText>(R.id.etNumPagas)
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val spGrupoProfesional = findViewById<Spinner>(R.id.spGrupoProfesional)
        val spGradoDiscapacidad = findViewById<Spinner>(R.id.spGradoDiscapacidad)
        val spEstadoCivil = findViewById<Spinner>(R.id.spEstadoCivil)
        val etNumHijos = findViewById<EditText>(R.id.etNumHijos)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)


        // Configurar el listener del botón
        btnCalcular.setOnClickListener {
            //Leer valores ingresados
            val salarioBruto = etSalarioBruto.text.toString().toDoubleOrNull() ?: 0.0
            val numPagas = etNumPagas.text.toString().toIntOrNull() ?: 0
            val edad = etEdad.text.toString().toIntOrNull() ?: 0
            val numHijos = etNumHijos.text.toString().toIntOrNull() ?: 0
            val grupoProfesional = spGrupoProfesional.selectedItem.toString()
            val gradoDiscapacidad = spGradoDiscapacidad.selectedItem.toString()
            val estadoCivil = spEstadoCivil.selectedItem.toString()

            // Validar el valor
            if (salarioBruto == null || salarioBruto <= 0) {
                Toast.makeText(this, "Por favor, ingrese un salario válido.", Toast.LENGTH_SHORT).show()
            } else {
                // Ir a la siguiente actividad (Resultados)
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("SALARIO_BRUTO", salarioBruto)
                intent.putExtra("NUM_PAGAS", numPagas)
                intent.putExtra("EDAD", edad)
                intent.putExtra("GRUPO_PROFESIONAL", grupoProfesional)
                intent.putExtra("GRADO_DISCAPACIDAD", gradoDiscapacidad)
                intent.putExtra("ESTADO_CIVIL", estadoCivil)
                intent.putExtra("NUM_HIJOS", numHijos)
            }
            startActivity(intent)
        }
    }

}


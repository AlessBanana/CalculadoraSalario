package com.example.calculadorasalario

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Vincular elementos de la vista
        val tvSalarioBruto = findViewById<TextView>(R.id.tvSalarioBruto)
        val tvSalarioNeto = findViewById<TextView>(R.id.tvSalarioNeto)
        val tvRetencionIrpf = findViewById<TextView>(R.id.tvRetencionIRPF)
        val tvDeducciones = findViewById<TextView>(R.id.tvDeducciones)

        // Obtener los datos del intent
        val salarioBruto = intent.getDoubleExtra("SALARIO_BRUTO", 0.0)
        intent.getIntExtra("NUM_PAGAS", 12)
        val edad = intent.getIntExtra("EDAD", 0)
        val grupoProfesional = intent.getStringExtra("GRUPO_PROFESIONAL") ?: "Sin especificar"
        val gradoDiscapacidad = intent.getStringExtra("GRADO_DISCAPACIDAD") ?: "Ninguna"
        val estadoCivil = intent.getStringExtra("ESTADO_CIVIL") ?: "Soltero/a"
        val numHijos = intent.getIntExtra("NUM_HIJOS", 0)

        // Calcular el salario neto y las retenciones
        val retencionIRPF = calcularRetencion(salarioBruto, edad, estadoCivil, numHijos)
        val deducciones = calcularDeducciones(grupoProfesional, gradoDiscapacidad)
        val salarioNeto = salarioBruto - retencionIRPF - deducciones

        // Mostrar los resultados en los TextView
        tvSalarioBruto.text = "Salario Bruto: $${"%.2f".format(salarioBruto)}"
        tvSalarioNeto.text = "Salario Neto: $${"%.2f".format(salarioNeto)}"
        tvRetencionIrpf.text = "Retención IRPF: $${"%.2f".format(retencionIRPF)}"
        tvDeducciones.text = "Deducciones: $${"%.2f".format(deducciones)}"
    }

    // Función para calcular la retención de IRPF
    private fun calcularRetencion(salarioBruto: Double, edad: Int, estadoCivil: String, numHijos: Int): Double {
        var tasaBase = when {
            salarioBruto <= 12000 -> 0.08
            salarioBruto <= 20000 -> 0.15
            salarioBruto <= 35000 -> 0.20
            else -> 0.30
        }

        // Ajustes según la edad
        if (edad > 65) tasaBase -= 0.02

        // Ajustes según estado civil
        if (estadoCivil == "Casado/a") tasaBase -= 0.03

        // Ajustes por hijos
        tasaBase -= (numHijos * 0.01)

        // Asegurar que la tasa no sea negativa
        if (tasaBase < 0) tasaBase = 0.0

        return salarioBruto * tasaBase
    }

    // Función para calcular deducciones adicionales
    private fun calcularDeducciones(grupoProfesional: String, gradoDiscapacidad: String): Double {
        var deducciones = 0.0

        // Deducciones según el grupo profesional
        deducciones += when (grupoProfesional) {
            "Directivo" -> 500.0
            "Técnico" -> 300.0
            "Administrativo" -> 200.0
            else -> 100.0
        }

        // Ajustes por grado de discapacidad
        deducciones += when (gradoDiscapacidad) {
            "Ninguna" -> 0.0
            "Moderada" -> 100.0
            "Severa" -> 300.0
            else -> 0.0
        }

        return deducciones
    }
}
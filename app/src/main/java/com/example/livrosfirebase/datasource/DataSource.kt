package com.example.livrosfirebase.datasource

import com.google.firebase.firestore.FirebaseFirestore

class DataSource {

    private val db = FirebaseFirestore.getInstance()

    fun salvarLivros(Titulo: String, Autor: String, Genero: String, onSuccess : () -> Unit, onFailure : (Any) -> Unit ){

        val livrosMap = hashMapOf(
            "Titulo" to Titulo,
            "Autor" to Autor,
            "Genero" to Genero)

        db.collection( "Livros")
            .document(Titulo)
            .set(livrosMap)
            .addOnSuccessListener {
                onSuccess()
            }

            .addOnFailureListener {
                    erro -> onFailure( erro )
            }
    }

    fun listarLivros(onResult: (List<Map<String, Any>>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Livros")
            .get()
            .addOnSuccessListener { result ->
                val livros = result.mapNotNull { it.data }
                onResult(livros)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun deletarLivros(titulo: String){
        db.collection("Livros").document(titulo).delete()


    }
}

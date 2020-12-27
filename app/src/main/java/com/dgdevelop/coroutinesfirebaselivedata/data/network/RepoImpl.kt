package com.dgdevelop.coroutinesfirebaselivedata.data.network

import com.dgdevelop.coroutinesfirebaselivedata.vo.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RepoImpl: IRepo {

    /* Antes sin usar Flow*/

    //Las suspend fun sirven para detener el flujo del programa hasta que se cumpla
    //todas las operaciones de la funcion
    /*override suspend fun getVersionCodeRepo(): Flow<Resource<Int>> {
        //Firebase

        //El metodo await lo proporciona la dependencia de corutinas play service
        //el await espera hasta que se cumpla este hilo de ejecución para proceguir
        //con las siguientes lineas de codigo
        val  resultData = FirebaseFirestore.getInstance()
            .collection("params")
            .document("app")
            .get().await()

        val versionCode = resultData.getLong("version")

        return Resource.Succes(versionCode!!.toInt())
    }*/

    /* Usando Flow*/

    /* CallbackFlow sirver para llamadas asincronas como peticiones a una API mientras que la lambda
    * flow sirve para realizar tareas no asincornas pero que requiren que se espere a que se termine
    * la ejecucion */
    @ExperimentalCoroutinesApi
    override suspend fun getVersionCodeRepo(): Flow<Resource<Int>> = callbackFlow {
        val eventDocument = FirebaseFirestore.getInstance()
            .collection("params")
            .document("app")

        val subscription = eventDocument.addSnapshotListener{documentSnapshot, firebaseFirestoreException->
            if(documentSnapshot!!.exists()){
                val versionCode = documentSnapshot.getLong("version")!!.toInt()
                offer(Resource.Succes(versionCode)) /* buffer que siempre estara escuchando este valor */
            }else{
                channel.close(firebaseFirestoreException?.cause)
            }
        }
        /* Cierra el flujo cuando ya no se este utilizando mas o cuando se haya cambiado de actividad*/
        awaitClose { subscription.remove() }
    }
}
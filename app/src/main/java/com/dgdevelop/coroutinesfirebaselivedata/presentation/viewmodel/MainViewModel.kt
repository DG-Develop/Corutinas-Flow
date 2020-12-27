package com.dgdevelop.coroutinesfirebaselivedata.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dgdevelop.coroutinesfirebaselivedata.domain.IUseCase
import com.dgdevelop.coroutinesfirebaselivedata.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class MainViewModel(useCase: IUseCase): ViewModel() {

    /* Antes de FLow*/

    //Cuando se instancia liveData obtiene sus dos valores cuando esta activo(onActive)
    //o cuando esta inactivo(onInactive)
    //Los dispatchers nos sirven para decirle en que contexto ejecutamos nuestra operacion
    //y nos sirven para ejecutar el cofdigo en segundo plano(hilo aparte)
    //-IO = Operaciones cuando vamos a buscar información en el servidor o cuando leemos
    //      un archivo grande(por ejemplo que puede lograr a congelar la UI)
    //-Main = Se usa cuando hacemos una operación rápida en la vista
    /*val fetchVerionCode = liveData(Dispatchers.IO){
        //emit es lo mismo que decir como un mutableLiveData.postvalue()
        emit(Resource.Loading())
        try {
            val version = useCase.getVersionCode()
            emit(version)
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }*/

    //El método onCleared funciona como si fuera el método onDestroy de un activity
    /*En esta onCleared nos sirve que al momento de que se destruye el model o fragment
      nos sirve para cancelar jobs o cancelar tareas de corutinas para que no esten
      siempre a la escuche de valores y no generen mucha memoria a largo plazo

    override fun onCleared() {
        super.onCleared()
    }*/


    /* Con Flow */
    val fetchVerionCode = liveData(Dispatchers.IO){
        //emit es lo mismo que decir como un mutableLiveData.postvalue()
        emit(Resource.Loading())
        try {
           useCase.getVersionCode().collect { version->
               emit(version)
           }
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}
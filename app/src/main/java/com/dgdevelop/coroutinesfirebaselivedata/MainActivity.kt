package com.dgdevelop.coroutinesfirebaselivedata

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dgdevelop.coroutinesfirebaselivedata.base.BaseActivity
import com.dgdevelop.coroutinesfirebaselivedata.data.network.RepoImpl
import com.dgdevelop.coroutinesfirebaselivedata.domain.UseCaseImpl
import com.dgdevelop.coroutinesfirebaselivedata.presentation.viewmodel.MainViewModel
import com.dgdevelop.coroutinesfirebaselivedata.presentation.viewmodel.MainViewModelFactory
import com.dgdevelop.coroutinesfirebaselivedata.vo.Resource
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    //lazy sirve como un lateinit var pero la diferencia radica en que el lateinitvar
    //se tiene que declarar enseguida para saber el valor que va a tomar
    //en cambio lazy es invocado cuando realmente se necesita
    private val viewModel by lazy{ViewModelProvider(this, MainViewModelFactory(UseCaseImpl(RepoImpl()))).get(MainViewModel::class.java)}

    override fun getViewID(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
    }

    private fun observeData(){
        //El observer tiene dos parametros uno es el LifecycleOwner y dependiendo si es
        //un activity solo pones this y en dado caso de que sea un Fragment se pone
        //viewLifecycleOwner ya que el fragment esta contenido en el activity
        viewModel.fetchVerionCode.observe(this, Observer { result ->
            when(result){
                is Resource.Loading ->{
                    showProgress()
                }
                is Resource.Succes ->{
                    //tv_version.text = result.data.toString()
                    val actualVersion = result.data
                    /*if(appIsOutDated(actualVersion)){
                        showUpdateProgress()
                    }*/
                    tv_version.text = actualVersion.toString()
                    hideProgress()
                }
                is Resource.Failure ->{
                    Toast.makeText(this, "Ocurrio un error ${result.exception.message}", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR:", "${result.exception.message}")
                    hideProgress()
                }
            }
        })
    }

    private fun appIsOutDated(actualVersion: Int): Boolean{
        val currentVersion = BuildConfig.VERSION_CODE
        return currentVersion < actualVersion
    }
}

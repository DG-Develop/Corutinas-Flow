package com.dgdevelop.coroutinesfirebaselivedata.data.network

import com.dgdevelop.coroutinesfirebaselivedata.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IRepo {
    suspend fun getVersionCodeRepo(): Flow<Resource<Int>>
}
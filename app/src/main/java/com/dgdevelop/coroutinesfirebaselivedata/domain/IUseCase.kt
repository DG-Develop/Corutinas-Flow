package com.dgdevelop.coroutinesfirebaselivedata.domain

import com.dgdevelop.coroutinesfirebaselivedata.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IUseCase {
    suspend fun getVersionCode(): Flow<Resource<Int>>
}
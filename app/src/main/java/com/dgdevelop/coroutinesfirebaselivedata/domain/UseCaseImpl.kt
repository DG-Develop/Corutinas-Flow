package com.dgdevelop.coroutinesfirebaselivedata.domain

import com.dgdevelop.coroutinesfirebaselivedata.data.network.IRepo
import com.dgdevelop.coroutinesfirebaselivedata.vo.Resource
import kotlinx.coroutines.flow.Flow

class UseCaseImpl(private val repo: IRepo): IUseCase {
    override suspend fun getVersionCode(): Flow<Resource<Int>> = repo.getVersionCodeRepo()
}
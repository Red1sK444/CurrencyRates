package com.r3d1r4ph.currencyrates.data.currency

import com.r3d1r4ph.currencyrates.data.currency.local.CurrencyDao
import com.r3d1r4ph.currencyrates.data.currency.local.RelevanceDao
import com.r3d1r4ph.currencyrates.data.currency.network.CurrencyService
import com.r3d1r4ph.currencyrates.data.currency.network.GeneralDto
import com.r3d1r4ph.currencyrates.domain.Currency
import com.r3d1r4ph.currencyrates.utils.exceptions.EmptyResponseException
import com.r3d1r4ph.currencyrates.utils.safeApiCall
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService,
    private val currencyDao: CurrencyDao,
    private val relevanceDao: RelevanceDao
) : CurrencyRepository {

    override fun getGeneralInfo() =
        relevanceDao.getGeneralInfo().filterNotNull().map { it.toDomain() }

    override suspend fun loadCurrencyList(): Result<Boolean> {
        return safeApiCall(
            apiCall = {
                currencyService.getCurrencyList()
            },
            onSuccess = { response ->
                when (val dto = response.body()) {
                    null -> Result.failure(EmptyResponseException())
                    else -> {
                        relevanceDao.deleteRelevance()
                        saveData(dto)
                        Result.success(false)
                    }
                }
            }
        )
    }

    private suspend fun saveData(dto: GeneralDto) {
        val insertedId = relevanceDao.insertRelevance(dto.toRelevanceEntity())
        currencyDao.insertAll(dto.toCurrencyEntityList(insertedId))
    }

    override suspend fun getCurrencyById(id: String): Currency =
        currencyDao.getCurrencyById(id).toDomain()
}
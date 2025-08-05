package com.esraeker.inspiredaily.data.repository

import android.util.Log
import com.esraeker.inspiredaily.data.local.QuoteDao
import com.esraeker.inspiredaily.data.model.QuoteEntity
import com.esraeker.inspiredaily.data.remote.ApiClient
import kotlinx.coroutines.flow.Flow

class QuoteRepository(private val quoteDao: QuoteDao) {  //QuoteDao üzerinden veriye ulaşacak

    suspend fun insertAllQuotes(quote: List<QuoteEntity>) {
        quoteDao.insertAllQuotes(quote)
    }

    fun getAllQuotes(): Flow<List<QuoteEntity>> {
        return quoteDao.getAllQuotes()
    }

    suspend fun testApiFetch() {
        val quotes = ApiClient.quoteApiService.getQuotes()
        val entities = quotes.map {
            QuoteEntity(id = it.id, text = it.text, author = it.author)
        }
        insertAllQuotes(entities)
        quotes.forEach {
            Log.d("API_TEST","API'den gelen: ${it.id} - ${it.text}")
        }
    }

}

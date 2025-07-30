package com.esraeker.inspiredaily.data.repository

import com.esraeker.inspiredaily.data.local.QuoteDao
import com.esraeker.inspiredaily.data.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

class QuoteRepository(private val quoteDao: QuoteDao) {  //QuoteDao üzerinden veriye ulaşacak

    suspend fun insertQuote(quote: QuoteEntity) {
        quoteDao.insertQuote(quote)
    }

    fun getAllQuotes(): Flow<List<QuoteEntity>> {
        return quoteDao.getAllQuotes()
    }
}

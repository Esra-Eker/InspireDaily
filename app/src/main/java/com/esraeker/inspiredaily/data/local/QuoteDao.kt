package com.esraeker.inspiredaily.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esraeker.inspiredaily.data.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao //Bu arayüzün Room için veri erişim katmanı olduğunu belirtir
interface QuoteDao {  //interface çünkü: DAO’lar sadece "ne yapılacak?" bilgisini tutar.Gerçek kodu yazmazlar, sadece “şu metot şunu yapsın” derler. Gerçek davranışı yine Room bizim yerimize üretir.

    @Insert(onConflict = OnConflictStrategy.REPLACE) //Aynı id'de veri varsa üstüne yazar
    suspend fun insertQuote(quote: QuoteEntity) //Veritabanına yeni bir söz ekler

    @Query("SELECT * FROM quotes ORDER BY id DESC")
    fun getAllQuotes(): Flow<List<QuoteEntity>>  //Tüm sözleri canlı olarak getirir (Live güncellenir)
                                                 //Flow sayesinde, UI tarafında bu sözlerde bir değişiklik olursa Compose arayüz otomatik güncellenir.
                                                 //Flow = "asenkron olarak sürekli veri akışı"

    @Query("DELETE FROM quotes")
    suspend fun deleteAllQuotes() 	//Veritabanındaki tüm sözleri siler
}

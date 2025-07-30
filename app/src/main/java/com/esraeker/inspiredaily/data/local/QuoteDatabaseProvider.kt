package com.esraeker.inspiredaily.data.local

import android.content.Context
import androidx.room.Room

object QuoteDatabaseProvider {  //Kotlin’de singleton tanımıdır — Kotlin’de object anahtar kelimesi ile yazılan sınıf, sadece bir kez oluşturulur.

    @Volatile  //INSTANCE değişkenine farklı thread'lerden erişimde çakışma olmasın, son değeri herkes görsün diye.
    private var INSTANCE: QuoteDatabase? = null

    fun getDatabase(context: Context): QuoteDatabase {
        return INSTANCE ?: synchronized(this) {  // synchronized(this) ile çoklu thread erişimine kilit. Aynı anda 2 thread veritabanı oluşturmaya kalkarsa hata olmasın diye.
            val instance = Room.databaseBuilder(  //Room veritabanını oluşturur
                context.applicationContext, //Veritabanı uygulama seviyesinde çalışsın diye
                QuoteDatabase::class.java,  //Hangi Room database class'ı kullanılacak
                "quote_database"  //Veritabanının cihazda saklanacağı dosya adı
            ).build()
            INSTANCE = instance
            instance
        }
    }
}

package com.esraeker.inspiredaily.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esraeker.inspiredaily.data.model.QuoteEntity

@Database(  //Bu sınıfın bir Room veritabanı olduğunu bildirir
    entities = [QuoteEntity::class],  //Hangi tabloları kullanacağını söyler (şu an sadece QuoteEntity)
    version = 1, //Veritabanı versiyonu (İleride schema değişirse bu sayı artırılır)
    exportSchema = false
)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao // abstract ile Room bu metodu otomatik oluşturur ve bizim DAO’muza erişmemizi sağlar
}

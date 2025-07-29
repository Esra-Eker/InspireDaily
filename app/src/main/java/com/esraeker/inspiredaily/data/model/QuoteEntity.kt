package com.esraeker.inspiredaily.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes") //Bu sınıfın Room için bir tablo olduğunu belirtir ve tablonun veritabanında nasıl adlandırılacağını söyler.
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true) //Otomatik artan birincil anahtar tanımlar (id)
    val id: Int = 0,
    val text: String,
    val author: String? = null
)

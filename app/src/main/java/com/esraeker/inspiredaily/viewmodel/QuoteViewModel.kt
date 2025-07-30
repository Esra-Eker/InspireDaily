package com.esraeker.inspiredaily.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esraeker.inspiredaily.data.model.QuoteEntity
import com.esraeker.inspiredaily.data.repository.QuoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QuoteViewModel(private val repository: QuoteRepository) : ViewModel() {

    val quotes: StateFlow<List<QuoteEntity>> = repository  //StateFlow ile UI tarafına sürekli güncellenebilen veri sunuyoruz
        .getAllQuotes()
        .map { it } // Burada filtre vs. yapılabilir. map verileri dömüştürmek için kullanılır. {it} ile şu an gelen veriyi olduğu gibi gönderir.
        .stateIn(   //Flow’u StateFlow’a çeviriyoruz → Compose ile uyumlu
            scope = viewModelScope, //Coroutine hangi yaşam döngüsünde çalışacak? ViewModelScope → ekranla sınırlı
            started = SharingStarted.WhileSubscribed(5000), //“Sadece UI bu veriyi dinliyorsa çalış” demek. 5 saniye dinleyen kalmazsa durur
            initialValue = emptyList() //Uygulama ilk açıldığında veri gelmeden önce gösterilecek başlangıç değeri (boş liste)
        )

    fun insertQuote(quote: QuoteEntity) {  //UI'dan gelen isteği alıp veri katmanına aktaran mantıksal köprüdür. Yeni söz eklemek için çağrılır.
        viewModelScope.launch { // viewModelScope.launch ile coroutine başlatılır
            repository.insertQuote(quote)
        }
    }
}

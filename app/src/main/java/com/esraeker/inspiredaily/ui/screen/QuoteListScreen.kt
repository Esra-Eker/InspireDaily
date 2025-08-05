package com.esraeker.inspiredaily.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.esraeker.inspiredaily.viewmodel.QuoteViewModel

@Composable
fun QuoteListScreen(viewModel: QuoteViewModel, modifier: Modifier = Modifier) {
    val quoteList by viewModel.quotes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Günün Sözleri",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quoteList) { quote ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = quote.text,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


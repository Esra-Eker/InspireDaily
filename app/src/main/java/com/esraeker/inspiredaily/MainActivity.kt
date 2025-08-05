package com.esraeker.inspiredaily

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.esraeker.inspiredaily.data.local.QuoteDatabaseProvider
import com.esraeker.inspiredaily.data.repository.QuoteRepository
import com.esraeker.inspiredaily.ui.theme.InspireDailyTheme
import com.esraeker.inspiredaily.viewmodel.QuoteViewModel
import com.esraeker.inspiredaily.viewmodel.QuoteViewModelFactory
import com.esraeker.inspiredaily.ui.screen.QuoteListScreen

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("CHECK", "onCreate başladı")

        // Bildirim izni (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }

        // ViewModel kurulumu
        val dao = QuoteDatabaseProvider.getDatabase(applicationContext).quoteDao()
        val repository = QuoteRepository(dao)
        val factory = QuoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[QuoteViewModel::class.java]

        // Tam ekran içerik
        enableEdgeToEdge()

        // Compose arayüzü tanımlanıyor
        setContent {
            InspireDailyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuoteListScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to InspireDaily!")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            showMotivationalNotification(context, "Keep going! You're doing great 🌟")
        }) {
            Text("Get Motivation")
        }
    }
}

private fun showMotivationalNotification(context: Context, message: String) {
    val channelId = "motivation_channel_v3"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Motivational Messages",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel for daily motivational messages"
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Daily Motivation")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        notify(2001, builder.build())
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InspireDailyTheme {
        GreetingScreen()
    }
}

package com.payle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RepliesScreen() {
    var customerMessage by remember { mutableStateOf("") }
    var suggestedReplies by remember { mutableStateOf<List<String>>(emptyList()) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "رد تلقائي على العملاء",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = customerMessage,
            onValueChange = { customerMessage = it },
            label = { Text("رسالة العميل") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("مثال: بكام وآخر سعر؟") }
        )

        Button(
            onClick = {
                if (customerMessage.isNotBlank()) {
                    suggestedReplies = generateReplies(customerMessage)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("اقترح ردود")
        }

        if (suggestedReplies.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("الردود المقترحة:", style = MaterialTheme.typography.titleMedium)
                suggestedReplies.forEach { reply ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(reply, modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

fun generateReplies(message: String): List<String> {
    return listOf(
        "السلام عليكم! السعر كده وده آخر سعر عندنا، متفاوضينش أكتر 😊",
        "أهلاً بك! السعر دي وأي حاجة تانية تحتاجها تكلمنا!",
        "مرحباً! ده آخر سعر عندنا وبيشمل كل حاجة، عاوز تحجز؟"
    )
}

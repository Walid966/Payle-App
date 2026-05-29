package com.payle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PostsScreen() {
    var inputText by remember { mutableStateOf("") }
    var generatedPost by remember { mutableStateOf("") }
    var generatedHashtags by remember { mutableStateOf("") }
    var generatedShort by remember { mutableStateOf("") }
    var generatedFacebook by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "كتابة بوستات تلقائي",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("عاوز بوست عن إيه؟") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("مثال: عاوز بوست عرض زيت وسكر") }
        )

        Button(
            onClick = {
                if (inputText.isNotBlank()) {
                    val (post, hashtags, short, facebook) = generatePost(inputText)
                    generatedPost = post
                    generatedHashtags = hashtags
                    generatedShort = short
                    generatedFacebook = facebook
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ولّي البوست")
        }

        if (generatedPost.isNotBlank()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("البوست احترافي:", style = MaterialTheme.typography.titleMedium)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(generatedPost, modifier = Modifier.padding(16.dp))
                }

                Text("الهاشتاجات:", style = MaterialTheme.typography.titleMedium)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(generatedHashtags, modifier = Modifier.padding(16.dp))
                }

                Text("النسخة القصيرة:", style = MaterialTheme.typography.titleMedium)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(generatedShort, modifier = Modifier.padding(16.dp))
                }

                Text("النسخة لفيسبوك:", style = MaterialTheme.typography.titleMedium)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(generatedFacebook, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

fun generatePost(input: String): Quad<String, String, String, String> {
    val post = """
        🔥 عرض حصري لليوم! 🔥
        $input
        عجلة بس قبل ما ينتهي العرض! 🚀
        متنساش تروح الصفحة وتحجز قبل ما يخلص الكمية!
    """.trimIndent()

    val hashtags = "#عرض #خصم #مصري #تجار_صغار #عروض_يومية"

    val short = """
        🔥 عرض حصري!
        $input
        سارع بالحجز!
    """.trimIndent()

    val facebook = """
        مرحباً بكم أصدقائي! ❤️
        عندنا عرض حصري اليوم: $input
        لأول 50 عميل فقط! 🎉
        عاوز تشتري؟ إرسال رسالة دلوقتي!
    """.trimIndent()

    return Quad(post, hashtags, short, facebook)
}

data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

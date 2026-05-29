package com.payle

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfitScreen() {
    var costPrice by remember { mutableStateOf("") }
    var sellPrice by remember { mutableStateOf("") }
    var shippingCost by remember { mutableStateOf("") }
    var profit by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "حساب المكسب",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = costPrice,
            onValueChange = { costPrice = it },
            label = { Text("سعر الشراء") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = sellPrice,
            onValueChange = { sellPrice = it },
            label = { Text("سعر البيع") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = shippingCost,
            onValueChange = { shippingCost = it },
            label = { Text("مصاريف الشحن") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                val cost = costPrice.toDoubleOrNull() ?: 0.0
                val sell = sellPrice.toDoubleOrNull() ?: 0.0
                val shipping = shippingCost.toDoubleOrNull() ?: 0.0
                profit = sell - cost - shipping
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("حسب المكسب")
        }

        profit?.let {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "صافي الربح: %.2f جنيه".format(it),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

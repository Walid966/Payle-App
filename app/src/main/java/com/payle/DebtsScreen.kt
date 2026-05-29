package com.payle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun DebtsScreen() {
    var debtorName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val debtManager = remember { DebtManager(context) }
    var debts by remember { mutableStateOf(debtManager.getDebts()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "تذكير بالديون والآجل",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = debtorName,
            onValueChange = { debtorName = it },
            label = { Text("اسم المدين") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("المبلغ") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = dueDate,
            onValueChange = { dueDate = it },
            label = { Text("تاريخ الاستحقاق") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("مثال: 15/06/2026") }
        )

        Button(
            onClick = {
                if (debtorName.isNotBlank() && amount.isNotBlank() && dueDate.isNotBlank()) {
                    val debt = Debt(
                        id = debts.size + 1,
                        debtorName = debtorName,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        dueDate = dueDate
                    )
                    debtManager.saveDebt(debt)
                    debts = debtManager.getDebts()
                    debtorName = ""
                    amount = ""
                    dueDate = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("أضف دين")
        }

        if (debts.isNotEmpty()) {
            Text("الديون:", style = MaterialTheme.typography.titleMedium)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(debts) { debt ->
                    DebtItem(debt)
                }
            }
        }
    }
}

@Composable
fun DebtItem(debt: Debt) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("المدين: ${debt.debtorName}", style = MaterialTheme.typography.titleSmall)
            Text("المبلغ: ${debt.amount} جنيه")
            Text("تاريخ الاستحقاق: ${debt.dueDate}")
        }
    }
}

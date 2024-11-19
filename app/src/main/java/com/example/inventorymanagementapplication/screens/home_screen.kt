package com.example.project.ui.screens

import InventoryViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    inventoryViewModel: InventoryViewModel,
    navigateToAddEdit: () -> Unit = {}
) {
    // Trigger data fetching
    LaunchedEffect(Unit) {
        inventoryViewModel.fetchItems()
    }

    // Collect inventory items state
    val inventoryItems by inventoryViewModel.inventoryItems.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFFFF8E1) // Warmer, more pleasant background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp) // Increased padding for better spacing
        ) {
            // Dashboard Header
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Total Stock Info Section
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(inventoryItems) { item ->
                    InfoCard(
                        title = "Total Units in Stock",
                        value = item.quantity.toString(),
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                    InfoCard(
                        title = "Total Inventory Value",
                        value = (item.quantity * item.price).toString(),
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                    InventoryItemCard(
                        itemName = item.name,
                        itemCount = item.quantity.toString(),
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Push button to bottom

            // Add Stock Button
            Button(
                onClick = { navigateToAddEdit() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // Taller button for better touch target
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Add Stock",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String,
    containerColor: Color
) {
    Card(
        modifier = Modifier
            .height(120.dp), // Fixed height for consistency
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun InventoryItemCard(
    itemName: String,
    itemCount: String,
    //reorderLevel: String,
    containerColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = itemName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stock: $itemCount",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            FilledTonalButton(
                onClick = { /* Handle View Stock */ },
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "View Stock",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
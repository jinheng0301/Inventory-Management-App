package com.example.inventorymanagementapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    navigateToAddEdit: () -> Unit = {} // Add navigation parameter
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFFFF8E1) // Warmer, more pleasant background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp) // Increased padding for better spacing
        ) {
            // Dashboard Header with improved typography
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Total Stock Info Section with improved layout
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Consistent spacing
            ) {
                InfoCard(
                    "Total Units in Stock",
                    "786",
                    MaterialTheme.colorScheme.primary
                )
                InfoCard(
                    "Total Inventory Value",
                    "$565,430.00",
                    MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Inventory Section Header
            Text(
                text = "Inventory",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Inventory Items with improved styling
            InventoryItemCard(
                "No. of Tires",
                "486",
                "1 / 12",
                MaterialTheme.colorScheme.primaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            InventoryItemCard(
                "No. of Wheels",
                "300",
                "2 / 14",
                MaterialTheme.colorScheme.secondaryContainer
            )

            Spacer(modifier = Modifier.weight(1f)) // Push button to bottom

            // Add Stock Button with improved styling
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
    reorderLevel: String,
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
                Text(
                    text = "Reorder level: $reorderLevel",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
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
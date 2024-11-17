package com.example.inventorymanagementapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventorymanagementapplication.data.InventoryItem
import com.example.inventorymanagementapplication.models.InventoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    isEditing: Boolean = false,
    InventoryViewModel: InventoryViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    // State variables
    var itemName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var minimumStock by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Error states
    var itemNameError by remember { mutableStateOf<String?>(null) }
    var categoryError by remember { mutableStateOf<String?>(null) }
    var quantityError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }
    var stockError by remember { mutableStateOf<String?>(null) }
    var locationError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }

    // Save confirmation dialog state
    var showDialog by remember { mutableStateOf(false) }

    // Centralized validation function
    fun validateInputs(): Boolean {
        var isValid = true

        // Validation logic
        itemNameError = if (itemName.isBlank()) {
            isValid = false
            "Item name cannot be empty"
        } else null

        categoryError = if (category.isBlank()) {
            isValid = false
            "Category cannot be empty"
        } else null

        quantityError = if (quantity.isBlank() || quantity.toIntOrNull() == null) {
            isValid = false
            "Quantity must be a valid number"
        } else null

        priceError = if (price.isBlank() || price.toDoubleOrNull() == null) {
            isValid = false
            "Price must be a valid number"
        } else null

        stockError = if (minimumStock.isBlank() || minimumStock.toIntOrNull() == null) {
            isValid = false
            "Minimum stock must be a valid number"
        } else null

        locationError = if (location.isBlank()) {
            isValid = false
            "Storage location cannot be empty"
        } else null

        descriptionError = if (description.isBlank()) {
            isValid = false
            "Description cannot be empty"
        } else null

        return isValid
    }

    // Save item function
    fun saveItem() {
        if (validateInputs()) {
            coroutineScope.launch {
                val newId = InventoryViewModel.getHighestId()?.plus(1) ?: 1
                val newItem = InventoryItem(
                    id = newId,
                    name = itemName,
                    category = category,
                    quantity = quantity.toInt(),
                    price = price.toDouble(),
                    minimumStock = minimumStock.toInt(),
                    location = location,
                    description = description
                )
                InventoryViewModel.insertItem(newItem)
                showDialog = true // Show confirmation dialog
            }
        }
    }

    // UI Components
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (isEditing) "Edit Item" else "Add New Item") },
                navigationIcon = {
                    IconButton(onClick = onNavigateToHome) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Item Name Field
            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = itemNameError != null
            )
            itemNameError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

            // Category Field
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = categoryError != null
            )
            categoryError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

            // Quantity Field
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = quantityError != null
            )
            quantityError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

            // Price Field
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price per Unit (RM)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                isError = priceError != null
            )
            priceError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

            // Minimum Stock Field
            OutlinedTextField(
                value = minimumStock,
                onValueChange = { minimumStock = it },
                label = { Text("Minimum Stock Level") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = stockError != null
            )
            stockError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

            // Location Field
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Storage Location") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = locationError != null
            )
            locationError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

            // Description Field
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4,
                isError = descriptionError != null
            )
            descriptionError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateToHome,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = { saveItem() },
                    modifier = Modifier.weight(1f),
                    enabled = true
                ) {
                    Text(if (isEditing) "Update" else "Save")
                }
            }
        }
    }

    // Save Confirmation Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Save Successful") },
            text = { Text("Your item has been saved successfully.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onNavigateToHome()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

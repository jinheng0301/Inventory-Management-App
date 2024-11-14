//package com.example.inventorymanagementapplication.screens
//
//import AppDatabase
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.unit.sp
//import com.example.inventorymanagementapplication.data.ItemDetail
//import com.example.inventorymanagementapplication.models.AddEditModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddEditScreen(
//    onNextButtonClicked: (Int) -> Unit,
//    modifier: Modifier = Modifier,
//    onNavigateToHome: () -> Unit = {},
//    isEditing: Boolean = false,
//    appDatabase: AppDatabase = LocalAppDatabase.current
//) {
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//    val inventoryDao = appDatabase.inventoryDao()
//
//    var itemName by remember { mutableStateOf("") }
//    var quantity by remember { mutableStateOf("") }
//    var price by remember { mutableStateOf("") }
//    var category by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var minimumStock by remember { mutableStateOf("") }
//    var location by remember { mutableStateOf("") }
//    var showDialog by remember { mutableStateOf(false) }
//
//    // Error messages
//    var itemNameError by remember { mutableStateOf<String?>(null) }
//    var quantityError by remember { mutableStateOf<String?>(null) }
//    var priceError by remember { mutableStateOf<String?>(null) }
//    var categoryError by remember { mutableStateOf<String?>(null) }
//    var stockError by remember { mutableStateOf<String?>(null) }
//    var locationError by remember { mutableStateOf<String?>(null) }
//    var descriptionError by remember { mutableStateOf<String?>(null) }
//
//    // Drop-down menu state
//    var expanded by remember { mutableStateOf(false) }
//    val categories = listOf("Electronics", "Furniture", "Clothing", "Food", "Stationery", "Other")
//
//    val appDatabase = LocalAppDatabase.current
//    println("AppDatabase instance: $appDatabase")
//
//    // Function to save item
//    fun saveItem() {
//        if (validateInputs(itemName, category, quantity, price, minimumStock, location, description)) {
//            val newItem = ItemDetail(
//                name = itemName,
//                category = category,
//                quantity = quantity.toInt(),
//                price = price.toDouble(),
//                minimumStock = minimumStock.toInt(),
//                location = location,
//                description = description,
//                lastUpdated = System.currentTimeMillis().toString()
//            )
//
//            scope.launch(Dispatchers.IO){
//                try {
//                    inventoryDao.insertItem(newItem)
//                    withContext(Dispatchers.Main) {
//                        showDialog = true
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(context, "Failed to save item: ${e.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = if (isEditing) "Edit Item" else "Add New Item") },
//                navigationIcon = {
//                    IconButton(onClick = onNavigateToHome) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            )
//        }
//    ) { padding ->
//        Column(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState()),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            // Item Name Field
//            OutlinedTextField(
//                value = itemName,
//                onValueChange = {
//                    itemName = it
//                    itemNameError = if (it.isBlank()) "Item name cannot be empty" else null
//                },
//                label = { Text("Item Name") },
//                modifier = Modifier.fillMaxWidth(),
//                isError = itemNameError != null,
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
//            )
//            itemNameError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
//
//            // Category Dropdown
//            ExposedDropdownMenuBox(
//                expanded = expanded,
//                onExpandedChange = { expanded = !expanded }
//            ) {
//                OutlinedTextField(
//                    value = category,
//                    onValueChange = {},
//                    readOnly = true,
//                    label = { Text("Category") },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor(),
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                    isError = categoryError != null
//                )
//                categoryError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
//                ExposedDropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false }
//                ) {
//                    categories.forEach { option ->
//                        DropdownMenuItem(
//                            text = { Text(option) },
//                            onClick = {
//                                category = option
//                                categoryError = null
//                                expanded = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            // Quantity Field
//            OutlinedTextField(
//                value = quantity,
//                onValueChange = {
//                    if (it.isBlank() || it.toIntOrNull() != null) {
//                        quantity = it
//                        quantityError = if (it.isBlank()) "Quantity cannot be empty" else null
//                    }
//                },
//                label = { Text("Quantity") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Next
//                ),
//                singleLine = true,
//                isError = quantityError != null
//            )
//            quantityError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
//
//            // Price Field
//            OutlinedTextField(
//                value = price,
//                onValueChange = {
//                    if (it.isBlank() || it.toDoubleOrNull() != null) {
//                        price = it
//                        priceError = if (it.isBlank()) "Price cannot be empty" else null
//                    }
//                },
//                label = { Text("Price per Unit (RM)") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Decimal,
//                    imeAction = ImeAction.Next
//                ),
//                singleLine = true,
//                isError = priceError != null
//            )
//            priceError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
//
//            // Minimum Stock Level
//            OutlinedTextField(
//                value = minimumStock,
//                onValueChange = {
//                    if (it.isBlank() || it.toIntOrNull() != null) {
//                        minimumStock = it
//                        stockError = if (it.isBlank()) "Minimum stock level cannot be empty" else null
//                    }
//                },
//                label = { Text("Minimum Stock Level") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Next
//                ),
//                singleLine = true,
//                isError = stockError != null
//            )
//            stockError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
//
//            // Location Field
//            OutlinedTextField(
//                value = location,
//                onValueChange = {
//                    location = it
//                    locationError = if (it.isBlank()) "Storage location cannot be empty" else null
//                },
//                label = { Text("Storage Location") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                isError = locationError != null,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
//            )
//            locationError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
//
//            // Description Field
//            OutlinedTextField(
//                value = description,
//                onValueChange = {
//                    description = it
//                    descriptionError = if (it.isBlank()) "Description cannot be empty" else null
//                },
//                label = { Text("Description") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp),
//                maxLines = 4,
//                isError = descriptionError != null
//            )
//            descriptionError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Action Buttons
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                OutlinedButton(
//                    onClick = onNavigateToHome,
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text("Cancel")
//                }
//
//                Button(
//                    onClick = { saveItem() },
//                    modifier = Modifier.weight(1f),
//                    enabled = itemName.isNotBlank() && quantity.isNotBlank() &&
//                            price.isNotBlank() && category.isNotBlank()
//                ) {
//                    Text(if (isEditing) "Update" else "Save")
//                }
//            }
//        }
//    }
//
//    // Save Confirmation Dialog
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false },
//            title = { Text("Save Successful") },
//            text = { Text("Your item has been saved successfully.") },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        showDialog = false
//                        onNavigateToHome()
//                    }
//                ) {
//                    Text("OK")
//                }
//            }
//        )
//    }
//}
//
//fun validateInputs(
//    itemName: String, category: String, quantity: String,
//    price: String, minimumStock: String, location: String,
//    description: String
//): Boolean {
//    return itemName.isNotBlank() && category.isNotBlank() &&
//            quantity.toIntOrNull() != null &&
//            price.toDoubleOrNull() != null &&
//            minimumStock.toIntOrNull() != null &&
//            location.isNotBlank() && description.isNotBlank()
//}

package com.example.inventorymanagementapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import com.example.inventorymanagementapplication.models.AddEditModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    isEditing: Boolean = false,
    onNavigateToHome: () -> Unit = {},
) {
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var minimumStock by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    // Drop-down menu state
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Electronics", "Furniture", "Clothing", "Food", "Other")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (isEditing) "Edit Item" else "Add New Item") },
                navigationIcon = {
                    IconButton(onClick = onNavigateToHome) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                category = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Quantity Field
            OutlinedTextField(
                value = quantity,
                onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            // Price Field
            OutlinedTextField(
                value = price,
                onValueChange = { if (it.isEmpty() || it.toDoubleOrNull() != null) price = it },
                label = { Text("Price per Unit ($)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            // Minimum Stock Level
            OutlinedTextField(
                value = minimumStock,
                onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) minimumStock = it },
                label = { Text("Minimum Stock Level") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            // Location Field
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Storage Location") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Description Field
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4
            )

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
                    onClick = {
                        // TODO: Implement save logic
                    },
                    modifier = Modifier.weight(1f),
                    enabled = itemName.isNotBlank() && quantity.isNotBlank() &&
                            price.isNotBlank() && category.isNotBlank()
                ) {
                    Text(if (isEditing) "Update" else "Save")
                }
            }
        }
    }
}
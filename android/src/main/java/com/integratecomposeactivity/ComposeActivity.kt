package com.integratecomposeactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MessageBridgeActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Get data passed from React Native
    val inputData = intent.getStringExtra("input_data") ?: ""

    setContent {
      AppBridgeTheme {
        InputFormScreen(
          inputData = inputData,
          onSubmit = { result ->
            returnResult(result)
          },
          onCancel = {
            cancelActivity()
          }
        )
      }
    }
  }

  private fun returnResult(result: String) {
    val resultIntent = Intent().apply {
      putExtra("result_data", result)
    }
    setResult(Activity.RESULT_OK, resultIntent)
    finish()
  }

  private fun cancelActivity() {
    setResult(Activity.RESULT_CANCELED)
    finish()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFormScreen(
  inputData: String,
  onSubmit: (String) -> Unit,
  onCancel: () -> Unit
) {
  var textFieldValue by remember { mutableStateOf("") }

  // Handle back press
  BackHandler {
    onCancel()
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Native Activity") },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.primary,
        )
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start
    ) {
      // Display received data
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
      ) {
        Column(
          modifier = Modifier.padding(16.dp)
        ) {
          Text(
            text = "Received from React Native:",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = inputData,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.bodyLarge
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Input field
      OutlinedTextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        label = { Text("Enter result to return") },
        placeholder = { Text("Type your response...") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
          imeAction = ImeAction.Done
        ),
        singleLine = false,
        minLines = 3,
        maxLines = 5
      )

      Spacer(modifier = Modifier.height(24.dp))

      // Action buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = { onSubmit(textFieldValue) },
          modifier = Modifier.weight(1f),
          enabled = textFieldValue.isNotBlank()
        ) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Submit",
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text("Submit")
        }

        OutlinedButton(
          onClick = onCancel,
          modifier = Modifier.weight(1f)
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Cancel",
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text("Cancel")
        }
      }

      // Additional UI elements to demonstrate Compose capabilities
      Spacer(modifier = Modifier.height(32.dp))

      Divider()

      Spacer(modifier = Modifier.height(16.dp))

      // Example of additional Compose components
      var switchState by remember { mutableStateOf(false) }
      var sliderValue by remember { mutableStateOf(0.5f) }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Include metadata")
        Switch(
          checked = switchState,
          onCheckedChange = { switchState = it }
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Column {
        Text("Priority: ${(sliderValue * 100).toInt()}%")
        Slider(
          value = sliderValue,
          onValueChange = { sliderValue = it },
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}

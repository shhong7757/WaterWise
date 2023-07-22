package com.example.android.waterwise.ui.screen.beveragepreset

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.waterwise.data.hydratepreset.HydratePreset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeveragePresetScreen(
    viewModel: BeveragePresetViewModel = hiltViewModel(), popBackStack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    val openInputNewPreset = remember { mutableStateOf(false) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = uiState.title)
        }, navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "설정 화면으로 이동")
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { openInputNewPreset.value = true },
            modifier = Modifier.navigationBarsPadding()
        ) {
            Icon(Icons.Filled.Add, "프리셋 추가")
        }
    }) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            uiState.presets.map { preset ->
                PresetListItem(preset)
                Divider()
            }
        }

        if (openInputNewPreset.value) {
            InputNewPreset(onDismissRequest = { openInputNewPreset.value = false },
                onConfirm = { amount, nickname ->
                    openInputNewPreset.value = false
                    viewModel.insertHydratedPreset(
                        amount = amount, nickname = nickname
                    )
                })
        }
    }
}

@Composable
fun PresetListItem(preset: HydratePreset) {
    ListItem(
        headlineText = { Text(text = preset.nickname) },
        trailingContent = { Text(text = preset.amount.toString()) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputNewPreset(
    onConfirm: (amount: Int, nickname: String) -> Unit, onDismissRequest: () -> Unit
) {
    val nickname = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(top = 24.dp)) {
                Text(text = "목표 수분 섭취량")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = nickname.value, onValueChange = { nickname.value = it })
                OutlinedTextField(
                    value = amount.value.toString(), onValueChange = {
                        var v = 0

                        if (it == "") v = 0
                        else if (it.toIntOrNull() != null) v = it.toInt()

                        amount.value = v
                    }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text("취소")
                    }
                    TextButton(enabled = amount.value != 0 || nickname.value == "", onClick = {
                        onConfirm(amount.value, nickname.value)
                    }) {
                        Text("설정")
                    }
                }
            }
        }
    }
}
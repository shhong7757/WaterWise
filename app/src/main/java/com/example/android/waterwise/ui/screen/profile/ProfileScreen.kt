package com.example.android.waterwise.ui.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.android.waterwise.model.Sex

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value

    val openInputSexDialog = remember { mutableStateOf(false) }
    val openInputGoalHydrationAmount = remember { mutableStateOf(false) }
    val openInputHeight = remember { mutableStateOf(false) }
    val openInputWeight = remember { mutableStateOf(false) }

    Column {
        ListItem(modifier = Modifier.clickable { openInputGoalHydrationAmount.value = true },
            headlineText = { Text(text = "목표 수분 섭취량") },
            trailingContent = {
                Text(
                    text = uiState.goalHydrationAmount.toString()
                )
            })
        Divider()
        ListItem(modifier = Modifier.clickable { openInputSexDialog.value = true },
            headlineText = { Text(text = "성별") },
            trailingContent = {
                Text(text = uiState.sex.let {
                    when (it) {
                        Sex.Man -> "남자"
                        Sex.Woman -> "여자"
                        else -> ""
                    }
                })
            })
        Divider()
        ListItem(modifier = Modifier.clickable { openInputHeight.value = true },
            headlineText = { Text(text = "키") },
            trailingContent = {
                Text(
                    text = uiState.height.toString()
                )
            })
        Divider()
        ListItem(modifier = Modifier.clickable { openInputWeight.value = true },
            headlineText = { Text(text = "몸무게") },
            trailingContent = {
                Text(
                    text = uiState.weight.toString()
                )
            })
        Divider()

        if (openInputSexDialog.value) {
            InputSexDialog(sex = uiState.sex,
                onDismissRequest = { openInputSexDialog.value = false },
                onConfirm = {
                    openInputSexDialog.value = false
                    viewModel.setUserSex(it)
                })
        }

        if (openInputGoalHydrationAmount.value) {
            InputGoalHydrationAmountDialog(goalHydrationAmount = uiState.goalHydrationAmount,
                onDismissRequest = { openInputGoalHydrationAmount.value = false },
                onConfirm = {
                    openInputGoalHydrationAmount.value = false
                    viewModel.setGoalHydrationAmount(it)
                })
        }

        if (openInputWeight.value) {
            InputWeightDailog(weight = uiState.weight,
                onDismissRequest = { openInputWeight.value = false },
                onConfirm = {
                    openInputWeight.value = false
                    viewModel.setUserWeight(it)
                })
        }

        if (openInputHeight.value) {
            InputHeightDialog(height = uiState.height,
                onDismissRequest = { openInputHeight.value = false },
                onConfirm = {
                    openInputHeight.value = false
                    viewModel.setUserHeight(it)
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSexDialog(sex: Sex?, onDismissRequest: () -> Unit, onConfirm: (sex: Sex) -> Unit) {
    val selectedSex = remember { mutableStateOf(sex) }

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
                Text(text = "성별")
                Divider()
                Column() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedSex.value == Sex.Man,
                            onClick = { selectedSex.value = Sex.Man })
                        Text(text = "남성")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedSex.value == Sex.Woman,
                            onClick = { selectedSex.value = Sex.Woman })
                        Text(text = "여성")
                    }
                }
                Divider()
                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text("취소")
                    }
                    TextButton(enabled = selectedSex.value != null, onClick = {
                        onConfirm(selectedSex.value!!)
                    }) {
                        Text("선택")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputGoalHydrationAmountDialog(
    goalHydrationAmount: Int,
    onDismissRequest: () -> Unit,
    onConfirm: (goalHydrationAmount: Int) -> Unit
) {
    val tempGoalHydrationAmount = remember { mutableStateOf(goalHydrationAmount) }

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
                OutlinedTextField(
                    value = tempGoalHydrationAmount.value.toString(), onValueChange = {
                        var v = 0

                        if (it == "") v = 0
                        else if (it.toIntOrNull() != null) v = it.toInt()

                        tempGoalHydrationAmount.value = v
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
                    TextButton(enabled = tempGoalHydrationAmount.value != 0, onClick = {
                        onConfirm(tempGoalHydrationAmount.value)
                    }) {
                        Text("설정")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputWeightDailog(
    weight: Int? = 0, onDismissRequest: () -> Unit, onConfirm: (weight: Int) -> Unit
) {
    val tempWeight = remember { mutableStateOf(weight) }

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
                Text(text = "몸무게")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = tempWeight.value.toString(), onValueChange = {
                        var v = 0

                        if (it == "") v = 0
                        else if (it.toIntOrNull() != null) v = it.toInt()

                        tempWeight.value = v
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
                    TextButton(enabled = tempWeight.value != 0, onClick = {
                        tempWeight.value?.let { onConfirm(it) }
                    }) {
                        Text("설정")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputHeightDialog(
    height: Int? = 0, onDismissRequest: () -> Unit, onConfirm: (height: Int) -> Unit
) {
    val tempHeight = remember { mutableStateOf(height) }

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
                Text(text = "키")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = tempHeight.value.toString(), onValueChange = {
                        var v = 0

                        if (it == "") v = 0
                        else if (it.toIntOrNull() != null) v = it.toInt()

                        tempHeight.value = v
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
                    TextButton(enabled = tempHeight.value != 0, onClick = {
                        tempHeight.value?.let { onConfirm(it) }
                    }) {
                        Text("설정")
                    }
                }
            }
        }
    }
}
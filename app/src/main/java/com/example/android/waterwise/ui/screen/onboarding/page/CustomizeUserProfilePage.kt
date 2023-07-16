package com.example.android.waterwise.ui.screen.onboarding.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType
import com.example.android.waterwise.model.Sex

@Composable
fun CustomizeUserProfilePage(next: (goalHydrationAmount: Int, sheight: Int, sex: Sex, width: Int) -> Unit) {
    val inputGoalHydrationAmount = remember { mutableStateOf(2000) }
    val inputHeight = remember { mutableStateOf(0) }
    val inputSex = remember { mutableStateOf(Sex.Man) }
    val inputWeight = remember { mutableStateOf(0) }

    Column() {
        UserSex(inputSex.value, onSexChange = { inputSex.value = it })
        Divider()
        UserPreference(label = "몸무게", value = inputHeight.value, onValueChange = {
            inputHeight.value = it
        })
        Divider()
        UserPreference(label = "키", inputWeight.value, onValueChange = {
            inputWeight.value = it
        })
        Divider()
        UserPreference(label = "목표 수분 섭취량",
            value = inputGoalHydrationAmount.value,
            onValueChange = {
                inputGoalHydrationAmount.value = it
            })
        Button(onClick = {
            next(
                inputGoalHydrationAmount.value, inputHeight.value, inputSex.value, inputWeight.value
            )
        }) {
            Text(text = "다음")
        }
    }
}

@Composable
fun UserPreference(
    label: String, value: Int, onValueChange: (value: Int) -> Unit
) {
    Column() {
        Text(text = label)
        NumericTextField(value = value, onChange = onValueChange)
    }
}

@Composable
fun NumericTextField(value: Int, onChange: (value: Int) -> Unit) {
    TextField(
        value = value.toString(), onValueChange = {
            var v = 0

            if (it == "") v = 0
            else if (it.toIntOrNull() != null) v = it.toInt()

            onChange(v)
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}

@Composable
fun UserSex(sex: Sex, onSexChange: (sex: Sex) -> Unit) {
    Column() {
        Text(text = "성별")
        Row() {
            RadioButton(selected = sex == Sex.Man, onClick = { onSexChange(Sex.Man) })
            Text(text = "남성")
        }
        Row() {
            RadioButton(selected = sex == Sex.Woman, onClick = { onSexChange(Sex.Woman) })
            Text(text = "여성")
        }
    }
}
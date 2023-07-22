package com.example.android.waterwise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.android.waterwise.R
import com.example.android.waterwise.ui.screen.main.BeverageOption
import com.example.android.waterwise.ui.screen.main.HydratePresetOption

@Composable
fun HydrateForm(
    beverageOptions: List<BeverageOption>,
    hydratePresetOptions: List<HydratePresetOption>,
    onHydrateRequest: (hydrateAmount: Int, beverageOption: BeverageOption) -> Unit
) {
    val hydrateAmount = remember { mutableStateOf(0) }
    val selectedBeverageOption = remember { mutableStateOf<BeverageOption?>(null) }

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "프리셋")
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            hydratePresetOptions.map { hydratePresetOption ->
                HydratePresetOptionListItem(hydratePresetOption, onSelectHydratePreset = {
                    hydrateAmount.value = it.hydrationAmount
                    selectedBeverageOption.value = it.beverageOption
                })
            }
        }
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "음료")
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            beverageOptions.map { beverageOption ->
                BeverageOptionListItem(beverageOption,
                    selected = ((selectedBeverageOption.value?.beverageId == beverageOption.beverageId)),
                    onSelectBeverageOption = { selectedBeverageOption.value = beverageOption })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = hydrateAmount.value.toString(), onValueChange = {
                if (it == "") hydrateAmount.value = 0
                else if (it.toIntOrNull() != null) hydrateAmount.value = it.toInt()
            }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onHydrateRequest(hydrateAmount.value, selectedBeverageOption.value!!)

            hydrateAmount.value = 0
            selectedBeverageOption.value = null
        }, enabled = hydrateAmount.value != 0 && selectedBeverageOption.value != null) {
            Text(
                text = stringResource(id = R.string.hydrate)
            )
        }
    }
}

@Composable
fun BeverageOptionListItem(
    beverageOption: BeverageOption,
    selected: Boolean,
    onSelectBeverageOption: (beverageOption: BeverageOption) -> Unit
) {
    Column(modifier = Modifier.clickable { onSelectBeverageOption(beverageOption) }) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(beverageOption.color)
        )
        Text(
            text = beverageOption.label,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun HydratePresetOptionListItem(
    hydratePresetOption: HydratePresetOption,
    onSelectHydratePreset: (hydratePresetOption: HydratePresetOption) -> Unit
) {
    Column(modifier = Modifier.clickable { onSelectHydratePreset(hydratePresetOption) }) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(hydratePresetOption.beverageOption.color)
        )
        Text(
            text = hydratePresetOption.beverageOption.label,
        )
        Text(text = hydratePresetOption.hydrationAmount.toString())
    }
}
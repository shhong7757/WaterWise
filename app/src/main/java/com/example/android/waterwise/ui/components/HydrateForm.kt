package com.example.android.waterwise.ui.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import com.example.android.waterwise.model.Beverage

@Composable
fun HydrateForm(
    context: Context,
    onHydrateRequest: (amount: Int, beverage: String) -> Unit
) {
    val amount = remember { mutableStateOf(0) }
    val selectedBeverage = remember { mutableStateOf(Beverage.Water) }

    val beverages = Beverage.values()

    Column {
        Row() {
            beverages.map { beverage ->
                BeverageItem(
                    beverage,
                    context,
                    selected = (selectedBeverage.value == beverage),
                    onSelectBeverage = { selectedBeverage.value = it })
            }
        }
        TextField(
            value = amount.value.toString(),
            onValueChange = {
                if (it == "") amount.value = 0
                else if (it.toIntOrNull() != null) amount.value = it.toInt()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Button(onClick = {
            onHydrateRequest(amount.value, selectedBeverage.value.getLabel(context))

            amount.value = 0
            selectedBeverage.value = Beverage.Water
        }, enabled = amount.value != 0) {
            Text(
                text = "${selectedBeverage.value.getLabel(context)} " +
                        stringResource(id = R.string.hydrate)
            )
        }
    }
}


@Composable
fun BeverageItem(
    beverage: Beverage,
    context: Context,
    selected: Boolean,
    onSelectBeverage: (beverage: Beverage) -> Unit
) {
    Column(modifier = Modifier.clickable { onSelectBeverage(beverage) }) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(beverage.getColor())
        )
        Text(
            text = beverage.getLabel(context),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
package com.example.footballscoresapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.footballscoresapp.ui.components.MatchItem
import com.example.footballscoresapp.viewmodel.FootballViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdowns(viewModel: FootballViewModel) {
    var leagueExpanded by remember { mutableStateOf(false) }
    var countryExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text("Wybierz kraj:", style = MaterialTheme.typography.titleMedium)

        ExposedDropdownMenuBox(
            expanded = countryExpanded,
            onExpandedChange = { countryExpanded = !countryExpanded }
        ) {
            TextField(
                value = viewModel.selectedCountry?.name ?: "Wszystkie kraje",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (countryExpanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = countryExpanded,
                onDismissRequest = { countryExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Wszystkie kraje") },
                    onClick = {
                        viewModel.selectedCountry = null
                        countryExpanded = false
                    }
                )
                viewModel.countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(country.name) },
                        onClick = {
                            viewModel.selectedCountry = country
                            countryExpanded = false
                        },
                        modifier = if (viewModel.selectedCountry == country)
                            Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        else Modifier
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Wybierz ligę:", style = MaterialTheme.typography.titleMedium)

        ExposedDropdownMenuBox(
            expanded = leagueExpanded,
            onExpandedChange = { leagueExpanded = !leagueExpanded }
        ) {
            TextField(
                value = viewModel.selectedLeague?.league?.name ?: "Wszystkie ligi",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (leagueExpanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = leagueExpanded,
                onDismissRequest = { leagueExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Wszystkie ligi") },
                    onClick = {
                        viewModel.selectedLeague = null
                        leagueExpanded = false
                    }
                )
                viewModel.filteredLeagues.forEach { leagueData ->
                    DropdownMenuItem(
                        text = { Text(leagueData.league.name) },
                        onClick = {
                            viewModel.selectedLeague = leagueData
                            leagueExpanded = false
                        },
                        modifier = if (viewModel.selectedLeague == leagueData)
                            Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        else Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun MatchListScreen(viewModel: FootballViewModel = viewModel()) {
    if (viewModel.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (viewModel.errorMessage != null) {
        Text(
            text = "Error: ${viewModel.errorMessage}",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.error
        )
    } else {
        Column(modifier = Modifier.fillMaxWidth()) {
            FilterDropdowns(viewModel)

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.matches.isEmpty()) {
                Text("Brak danych lub trwa ładowanie...", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn {
                    items(viewModel.matches) { match ->
                        MatchItem(match)
                    }
                }
            }
        }
    }
}

//MatchItem.kt
package com.example.footballscoresapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.footballscoresapp.data.model.Match
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement

@Composable
fun MatchItem(match: Match) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "${match.league.name} (${match.league.country})",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(match.teams.home.logo),
                    contentDescription = "${match.teams.home.name} logo",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(match.teams.home.name, style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.width(8.dp))
                Text("vs", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.width(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(match.teams.away.logo),
                    contentDescription = "${match.teams.away.name} logo",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(match.teams.away.name, style = MaterialTheme.typography.titleSmall)
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Wynik: ${match.goals.home ?: "-"} : ${match.goals.away ?: "-"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Data: ${match.fixture.date.take(10)} ${match.fixture.date.substring(11, 16)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

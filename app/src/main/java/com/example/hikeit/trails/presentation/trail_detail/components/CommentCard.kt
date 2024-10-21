package com.example.hikeit.trails.presentation.trail_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.hikeit.R
import com.example.hikeit.trails.presentation.models.UserCommentUi
import com.example.hikeit.ui.theme.HikeItTheme

@Composable
fun CommentCard(
    commentUi: UserCommentUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Column {
                    Text(
                        text = commentUi.username,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = commentUi.commentDate,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Row {
                (1..commentUi.rating).forEach { _ ->
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Star",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                ((commentUi.rating + 1)..5).forEach { _ ->
                    Icon(
                        Icons.Default.StarBorder,
                        contentDescription = "Outlined Star",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                text = commentUi.comment,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CommentCardPreview() {
    HikeItTheme {
        CommentCard(
            commentUi = commentUi,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

internal val commentUi = UserCommentUi(
    userAvatarUrl = "",
    username = "Maciej Sieradz",
    commentDate = "1 miesiąc temu",
    rating = 4,
    comment = "Bardzo fajna trasa z cudownymi widokami! Przy Morskim Oku ludzi bardzo dużo, " +
            "ale po skręceniu na Szpiglasowy Wierch szliśmy już tylko my. Zdecydowanie warto!",
)
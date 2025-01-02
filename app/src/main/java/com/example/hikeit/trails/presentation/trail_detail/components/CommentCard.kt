package com.example.hikeit.trails.presentation.trail_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hikeit.trails.presentation.models.UserCommentUi

@Composable
fun CommentCard(
    commentUi: UserCommentUi,
    modifier: Modifier = Modifier
) {
    val openCommentDialog = remember { mutableStateOf(false) }
    when {
        openCommentDialog.value -> {
            ReadReviewModal(
                comment = commentUi,
                onDiscardButton = { openCommentDialog.value = false }
            )
        }
    }
    Card(
        modifier = modifier
            .clickable {
                openCommentDialog.value = true
            }
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
                AsyncImage(
                    model = commentUi.userAvatarUrl,
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
                text = commentUi.comment ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis,
                minLines = 4,
                maxLines = 4
            )
        }
    }
}
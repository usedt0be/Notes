package com.example.notes.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.notes.model.NoteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListItem(
    modifier: Modifier,
    note: NoteEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    dismissState: DismissState
) {
    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.Gray
                    DismissValue.DismissedToEnd -> Color.Red
                    DismissValue.DismissedToStart -> Color.Red
                }
            )
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val iconTint = when (dismissState.targetValue) {
                DismissValue.Default -> Color.Red
                DismissValue.DismissedToStart -> Color.White
                DismissValue.DismissedToEnd -> Color.White

            }
            val iconScale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1.2f
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = color)
                    .padding(16.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    modifier = Modifier.scale(iconScale),
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "delete note",
                    tint = iconTint
                )
            }
        },
        dismissContent = {
            Box(modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = { onDelete() })
                }
                .clickable {
                    onClick()
                }

            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .weight(1f),
                        text = note.text,
                        maxLines = 1
                    )
                    Icon(
                        modifier = Modifier
                            .align(CenterVertically)
                            .padding(end = 16.dp)
                            .alpha(0.7f),
                        imageVector = Icons.Rounded.EditNote,
                        contentDescription = "Edit note",
                        )
                }
                Spacer(
                    modifier = Modifier
                        .height(0.5.dp)
                        .background(color = Color.Gray.copy(alpha = 0.54f))
                        .fillMaxWidth()
                        .align(BottomCenter)
                )
            }

        })

}

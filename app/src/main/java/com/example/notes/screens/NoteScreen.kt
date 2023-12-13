package com.example.notes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.notes.model.NoteEntity
import com.example.notes.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    homeViewModel: HomeViewModel,
    onClickClose: () -> Unit
) {
    val note = homeViewModel.selectedNoteState.value
    val textState = rememberSaveable { mutableStateOf(note?.text ?: "") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "edit"
                    )
                },
                actions = {
                    IconButton(onClick = {
                        note?.let {
                            homeViewModel.addOrUpdateNote(it.copy(text = textState.value))
                        }?: run {
                            homeViewModel.addOrUpdateNote(note = NoteEntity(text = textState.value))
                        }
                        onClickClose()
                    }) {
                        Icon(imageVector = Icons.Rounded.Done, contentDescription = "Save note")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onClickClose) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription ="Back to home")
                    }

                }
            )
        },

        ) {scaffoldPadding ->

        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .clip(RoundedCornerShape(8.dp))
                .background(color = MaterialTheme.colorScheme.background)
        ) {

            BasicTextField(
                value = textState.value,
                onValueChange = { text: String -> textState.value = text },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}
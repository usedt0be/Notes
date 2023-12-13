package com.example.notes.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notes.model.NoteEntity
import com.example.notes.viewmodel.HomeViewModelAbstract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModelAbstract,
    onClickNote: (NoteEntity) -> Unit,
    onClickAddNote:() -> Unit,
) {


    val noteListState = homeViewModel.noteListFlow.collectAsState(initial = listOf())
    val textState = rememberSaveable { mutableStateOf("") }
    val noteRoomIdState: MutableState<Int?> = rememberSaveable { mutableStateOf(null) }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Notes")
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = Icons.Rounded.Notes,
                        contentDescription = null )
                }
            )

        }
    ) { ScaffoldPadding ->

        LazyColumn(
            modifier = Modifier.padding(ScaffoldPadding)
        ) {

            items(
                items = noteListState.value,
                key = {it.roomId?: ""}
            ) { note ->
                val dismissState = rememberDismissState(
                    confirmValueChange = {
                        if (it == DismissValue.DismissedToStart ||
                            it == DismissValue.DismissedToEnd
                        ) {
                            homeViewModel.deleteNote(note)
                            return@rememberDismissState true
                        }
                        return@rememberDismissState false

                    }
                )

                NoteListItem(
                    modifier = Modifier
                        .animateItemPlacement(),
                    note = note,
                    onClick = {
                        noteRoomIdState.value = note.roomId
                        textState.value = note.text
                        onClickNote(note)
                        homeViewModel.selectedNote(note)
                    },
                    onDelete = {
                        homeViewModel.deleteNote(note)
                    },
                    dismissState = dismissState,
                )

            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(end = 36.dp, bottom = 36.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    homeViewModel.resetSelectedNote()
                    onClickAddNote()
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }


    }

}



@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        homeViewModel = object :HomeViewModelAbstract {
            override val noteListFlow: Flow<List<NoteEntity>>
                get() = flowOf(listOf(
                    NoteEntity(text = "note1"),
                    NoteEntity(text = "note2"),
                    NoteEntity(text = "note3"),
                    NoteEntity(text = "note4"),
                    NoteEntity(text = "note5")
                ))


            override fun addOrUpdateNote(note: NoteEntity) {}

            override fun deleteNote(note: NoteEntity) {}

            override fun updateNote(note: NoteEntity) {}

            override fun selectedNote(note: NoteEntity) {}

            override fun resetSelectedNote() {}

        },
        onClickNote ={},
        onClickAddNote = {},
    )
}
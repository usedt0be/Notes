package com.example.notes.viewmodel


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notes.data.NoteRepository
import com.example.notes.model.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelAbstract {

    val noteListFlow: Flow<List<NoteEntity>>

    fun addOrUpdateNote(note: NoteEntity)
    fun deleteNote(note:NoteEntity)
    fun updateNote(note:NoteEntity)
    fun selectedNote(note: NoteEntity)
    fun resetSelectedNote()
}

@HiltViewModel
class HomeViewModel
    @Inject constructor(
    private val noteRepository: NoteRepository
    ): ViewModel(),HomeViewModelAbstract{

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _selectedNoteState: MutableState<NoteEntity?> = mutableStateOf(null)
    val selectedNoteState: State<NoteEntity?>
        get() = _selectedNoteState




    override val noteListFlow: Flow<List<NoteEntity>>
        get() = noteRepository.getAllFlow()

    override fun addOrUpdateNote(note: NoteEntity) {
        ioScope.launch {
            if(note.roomId == null) {
                noteRepository.addNote(note = note)
            } else {
                noteRepository.update(note = note)
            }
        }
    }

    override fun deleteNote(note: NoteEntity) {
        ioScope.launch { noteRepository.delete(note = note) }


    }

    override fun updateNote(note: NoteEntity) {
        ioScope.launch {
            noteRepository.update(note = note)
        }

    }

    override fun selectedNote(note: NoteEntity) {
        _selectedNoteState.value = note
    }

    override fun resetSelectedNote() {
        _selectedNoteState.value = null
    }

}
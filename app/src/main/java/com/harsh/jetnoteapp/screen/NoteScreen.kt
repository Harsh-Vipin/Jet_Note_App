package com.harsh.jetnoteapp.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harsh.jetnoteapp.R
import com.harsh.jetnoteapp.components.InputTextBox
import com.harsh.jetnoteapp.components.MyButton
import com.harsh.jetnoteapp.data.NotesDataSource
import com.harsh.jetnoteapp.model.Note
import java.time.format.DateTimeFormatter

@Composable
//@Preview(showBackground = true)
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit ,
    onRemoveNote: (Note) -> Unit

) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxSize()
    ) {
        TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }, actions = {
            Icon(
                imageVector = Icons.Rounded.Notifications,
                contentDescription = null
            )
        }, backgroundColor = Color(0xffdadfe3))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputTextBox(
                text = title, label = "Title", onTextChange = { title = it },
                modifier = Modifier.padding(
                    top = 9.dp, bottom = 9.dp
                )
            )
            InputTextBox(
                text = description, label = "Add a note", onTextChange = { description = it },
                modifier = Modifier.padding(
                    top = 9.dp, bottom = 9.dp
                )
            )
            MyButton(text = "Save", onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    onAddNote(Note(title = title, description = description))
                    title = ""
                    description = ""
                    Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                }
            }, shape = CircleShape)
            Divider(modifier = Modifier.padding(10.dp))
            LazyColumn() {
                items(notes) {
                    NoteRow(note = it, onNoteClicked = { onRemoveNote(it) })
                }
            }
        }
    }
}


@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(), color = Color(0xffdfe6eb), elevation = 6.dp
    ) {
        Column(modifier = Modifier
            .clickable {onNoteClicked(note) }
            .padding(horizontal = 14.dp, vertical = 6.dp), horizontalAlignment = Alignment.Start) {
            Text(text = note.title, style = MaterialTheme.typography.subtitle2)
            Text(text = note.description, style = MaterialTheme.typography.subtitle1)
            Text(
                text = note.entryDate.format(DateTimeFormatter.ofPattern("EEE, d MMM")),
                style = MaterialTheme.typography.caption
            )

        }
    }
}


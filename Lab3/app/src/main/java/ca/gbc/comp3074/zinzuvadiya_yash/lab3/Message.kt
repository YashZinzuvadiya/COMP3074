package ca.gbc.comp3074.zinzuvadiya_yash.lab3

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class Message(
    val author: String,
    val body: String
)

val sampleMessages = listOf(
    Message("Joe", "Hi!"),
    Message("Jim", "How are you?"),
    Message("Joe", "Test..1..2...3"),
    Message("Joe", "I hate coding!!!"),
    Message("Joe", "Hi!"),
    Message("Jim", "How are you?"),
    Message("Joe", "Test..1..2...3"),
    Message("Joe", "I hate coding!!!")
)

@Composable
fun MessageCard(message: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Red, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = message.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp
            ) {
                Text(
                    text = message.body,
                    modifier = Modifier.padding(all = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun MessageList(messages: List<Message>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

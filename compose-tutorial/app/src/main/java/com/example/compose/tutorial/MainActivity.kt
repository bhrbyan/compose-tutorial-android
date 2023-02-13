package com.example.compose.tutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.tutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Conversation(messages = SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(
    val author: String,
    val body: String
)

@Composable
private fun MessageCard(message: Message) {
    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember {
            mutableStateOf(false)
        }
        val surfaceColor by animateColorAsState(
            targetValue = if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        )

        Column() {
            Text(
                text = message.author,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                elevation = 4.dp,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .animateContentSize()
                    .padding(1.dp),
                color = surfaceColor
            ) {
                Text(
                    text = message.body,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(8.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

@Composable
private fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
private fun PreviewConversation() {
    Conversation(messages = SampleData.conversationSample)
}


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PreviewMessageCard() {
    ComposeTutorialTheme() {
        Surface() {
            MessageCard(message = Message("Android", "Jetpack Compose Tutorial"))
        }
    }
}
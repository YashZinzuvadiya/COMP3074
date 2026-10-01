package ca.gbc.comp3074.zinzuvadiya_yash.labex1v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import ca.gbc.comp3074.zinzuvadiya_yash.labex1v2.ui.theme.LabEx1v2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabEx1v2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var message = remember { mutableStateOf("Hello COMP3074") }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(
            text = message.value,
            modifier = modifier
        )
        val context = LocalContext.current
        val i = remember { mutableStateOf(0) }


        Button(onClick = {
            val msgs = context.resources.getStringArray(R.array.values)
            message.value = msgs[i.value] // Context.resources.getString(R.string.after_click_msg)
            i.value = (i.value+1) % msgs.size
        }){
            Text("Click me!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabEx1v2Theme {
        Greeting("Android")
    }
}
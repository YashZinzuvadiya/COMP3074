package ca.gbc.comp3074.zinzuvadiya_yash.lab2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.graphics.Color
import ca.gbc.comp3074.zinzuvadiya_yash.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ActionButtons(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButtons(modifier:Modifier = Modifier,
                  uri:Uri = "https://georgebrown.ca".toUri(),
                  phone:Uri = "tel:4164155000".toUri(),
                  location:String = "George Brown College, 160 Kendal Ave, Toronto" ) {

    val context = LocalContext.current

    Column( modifier = modifier.fillMaxWidth().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
        ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Application Logo",
                modifier = Modifier.width(200.dp).height(100.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)) {

            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            },
                modifier = Modifier.weight(1f)){

                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = "Icon Call"
                )
                Spacer(modifier = Modifier.width(5.dp))

                Text("Browser")
            }
            Button(onClick = {
                val intent = Intent(Intent.ACTION_DIAL, phone)
                context.startActivity(intent)
            },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.purple_500)
                    )
                ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Icon Call"
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text("Call")
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Button(onClick = {

                val l = Uri.encode(location)
                val geo = "google.navigation:0,0?q=$l".toUri()
                val intent = Intent(Intent.ACTION_VIEW, geo)
                context.startActivity(intent)
            },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(10,50,80))) {

                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = "Icon Call"
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text("Map")
                }
                Button(onClick = {

                    val intent = Intent(context, AboutActivity::class.java)

                    context.startActivity(intent)
                },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue))
                {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Icon Call"
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("About")
                }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(){
    Lab2Theme{
        ActionButtons()
    }
}

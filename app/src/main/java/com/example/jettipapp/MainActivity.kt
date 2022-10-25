package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettipapp.ui.theme.JetTipAppTheme
import org.intellij.lang.annotations.JdkConstants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                App {
                    Text(text = "Helloo")
                }
            }
        }
    }



@Composable
fun App(context: @Composable () -> Unit)
{
    JetTipAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            context()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Preview
@Composable
fun TopHeader(totalPerPerson :Double = 0.0)
{
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))
            ), color = Color(0xFFE9D7F7)
    ) {
        Column(modifier = Modifier.padding(12.dp)
            , verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Total Per person",
                style = MaterialTheme.typography.h5
            )
            Text(text = "$$totalPerPerson",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.ExtraBold)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetTipAppTheme {
        Greeting("Android")
    }
}
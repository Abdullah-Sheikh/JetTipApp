package com.example.jettipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettipapp.components.InputField
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.widgets.RoundedIconButton
import org.intellij.lang.annotations.JdkConstants


@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                App {


                    MainContext()
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


@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContext()
{
    billForm( ){ billAmt ->

        Log.d("AMT", "MainContext: $billAmt")
    }

}

@Preview
@Composable
fun TopHeader(totalPerPerson :Double = 0.0)
{
    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clip(
                shape = CircleShape.copy(all = CornerSize(12.dp))
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

    }
}

@ExperimentalComposeUiApi
@Composable
fun billForm(modifier: Modifier = Modifier
             ,onValChange: (String) -> Unit = {}
)
{
    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember (totalBillState.value) {

        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderPostionState = remember {
        mutableStateOf(0f)
    }

    Column() {


        TopHeader(23.9)


        Surface(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        )
        {
            Column() {

                InputField(
                    valueState = totalBillState,
                    labelId = "Enter Bill",
                    enabled = true,
                    isSingleLine = true,
                    onAction = KeyboardActions {

                        if (!validState) return@KeyboardActions
                        onValChange(totalBillState.value.trim())
                        keyboardController?.hide()
                    }
                )

                if (validState) {
                    Row(
                        modifier = Modifier.padding(3.dp), horizontalArrangement = Arrangement.Start
                    ) {

                        Text(
                            text = "Split", modifier = Modifier.align(
                                alignment = Alignment.CenterVertically
                            )
                        )
                        Spacer(modifier = Modifier.width(120.dp))
                        Row(
                            modifier = Modifier.padding(horizontal = 3.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            RoundedIconButton(
                                imageVector = Icons.Default.Remove, onClick = { })

                            RoundedIconButton(
                                imageVector = Icons.Default.Add, onClick = { })

                        }
                    }

                    Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {

                        Text(
                            text = "Tip",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(200.dp))
                        Text(
                            text = "$300",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )

                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = (sliderPostionState.value * 100).toString())
                        Spacer(modifier = Modifier.height(14.dp))

                        Slider(modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            steps = 5,
                            value = sliderPostionState.value, onValueChange = { newValue ->
                                sliderPostionState.value = newValue
                                Log.d("Slider", "billForm: $newValue")
                            })
                    }

                } else {

                }
            }

        }
    }


}
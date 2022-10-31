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
import com.example.jettipapp.util.calculateTipAmount
import com.example.jettipapp.util.calculateTotalPerPerson
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
            Text(text = String.format("%.1f", totalPerPerson),
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

    val tipPercentage = (sliderPostionState.value *100).toInt()
    val splitCountState = remember {
        mutableStateOf(1)
    }




    val range = IntRange(1,100)


    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }

    Column() {


        TopHeader(totalPerPersonState.value)


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

                    totalPerPersonState.value =
                        calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                            splitBy = splitCountState.value,
                            tipPercentage = tipPercentage)
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
                                imageVector = Icons.Default.Remove, onClick = {

                                        if(splitCountState.value>1) {
                                            splitCountState.value = splitCountState.value - 1

                                            totalPerPersonState.value =
                                                calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                                    splitBy = splitCountState.value,
                                                    tipPercentage = tipPercentage)
                                        }

                                    else
                                            splitCountState.value = 1



                                })


                            Text(text = (splitCountState.value).toString(),modifier = Modifier
                                .padding(start = 9.dp, end = 9.dp)
                                .align(
                                    Alignment.CenterVertically
                                ))
                            RoundedIconButton(
                                imageVector = Icons.Default.Add, onClick = {

                                    if(splitCountState.value < range.last)
                                    {
                                        splitCountState.value +=1

                                        totalPerPersonState.value =
                                            calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                                splitBy = splitCountState.value,
                                                tipPercentage = tipPercentage)
                                    }

                                })

                        }
                    }

                    Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {

                        Text(
                            text = "Tip",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(200.dp))
                        Text(
                            text = "$ ${tipAmountState.value}",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )

                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = "$tipPercentage %")
                        Spacer(modifier = Modifier.height(14.dp))

                        Slider(modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            steps = 5,
                            value = sliderPostionState.value, onValueChange = { newValue ->
                                sliderPostionState.value = newValue
                                tipAmountState.value =
                                    calculateTipAmount(totalBillState.value.toDouble() , tipPercentage)

                                totalPerPersonState.value =
                                    calculateTotalPerPerson(totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitCountState.value,
                                    tipPercentage = tipPercentage)
                                Log.d("Slider", "billForm: $newValue")
                            })
                    }

                } else {

                }
            }

        }
    }


}


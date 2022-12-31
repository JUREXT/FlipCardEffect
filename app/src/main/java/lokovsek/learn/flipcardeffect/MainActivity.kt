package lokovsek.learn.flipcardeffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lokovsek.learn.flipcardeffect.ui.theme.FlipCardEffectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlipCardEffectTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    AddCreditCard(
                        backgroundColor = Color.Gray,
                        cardProvider = CardProvider.MasterCard
                    )
                }
            }
        }
    }
}

enum class CardProvider {
    MasterCard, VISA
}

@Composable
fun AddCreditCard(
    cardOwner: String = "John Deere",
    cardNumber: String = "5424 1801 2345 6789",
    backgroundColor: Color,
    cardProvider: CardProvider,
    developedBy: String = "Capsule Corp"
) {

    var rotated by remember { mutableStateOf(false) }

    val cardType =
        when (cardProvider) {
            CardProvider.MasterCard -> painterResource(R.drawable.master_card_logo)
            CardProvider.VISA -> painterResource(R.drawable.visa_card_logo)
        }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    Card(
        modifier = Modifier
            .height(220.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable {
                rotated = !rotated
            },
        shape = RoundedCornerShape(14.dp),
        elevation = 4.dp,
        backgroundColor = backgroundColor,
        contentColor = Color.White
    ) {
        if (!rotated) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            ) {

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        painter = painterResource(R.drawable.contact_less),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(top = 6.dp, bottom = 6.dp, end = 20.dp)
                            .graphicsLayer {
                                alpha = animateFront
                            },
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = cardType,
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .graphicsLayer {
                                alpha = animateFront
                            }
                    )
                }
                Text(
                    text = cardNumber,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .graphicsLayer {
                            alpha = animateFront
                        },
                    // fontFamily = fontName,
                    fontWeight = FontWeight.Normal,
                    fontSize = 25.sp
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Card Holder",
                            color = Color.Gray,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .graphicsLayer {
                                    alpha = animateFront
                                }
                        )
                        Text(
                            text = cardOwner,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .graphicsLayer {
                                    alpha = animateFront
                                }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "VALID THRU",
                            color = Color.Gray,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .graphicsLayer {
                                    alpha = animateFront
                                }
                        )
                        Text(
                            text = "20.12.2023",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .graphicsLayer {
                                    alpha = animateFront
                                }
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.padding(top = 20.dp),
            ) {

                Divider(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = animateBack
                        }, color = Color.Black, thickness = 50.dp
                )

                Text(
                    text = "123",
                    color = Color.Black,
                    modifier = Modifier
                        .padding(10.dp)
                        .background(Color.White)
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = animateBack
                            rotationY = rotation
                        }
                        .padding(10.dp),

                    fontSize = 15.sp,
                    textAlign = TextAlign.End
                )

                Text(
                    text = "Developed by $developedBy",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = animateBack
                            rotationY = rotation
                        }
                        .padding(5.dp),

                    //fontFamily = fontName,
                    fontWeight = FontWeight.Thin,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlipCardEffectTheme {
        AddCreditCard(
            backgroundColor = Color.Gray,
            cardProvider = CardProvider.MasterCard
        )
    }
}
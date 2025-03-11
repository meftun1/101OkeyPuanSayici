package com.example.a101okeypuansayici

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a101okeypuansayici.ui.theme._101OkeyPuanSayiciTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _101OkeyPuanSayiciTheme {
                NavYoneticisi()
            }
        }
    }
}

@Serializable
object PuanHesaplamaEkrani

data class Tas(val renk: String, val sayi: UShort)

@Composable
fun NavYoneticisi() {
    val navController = rememberNavController()
    var ciftMiGidiyor by remember { mutableStateOf(false) }
    NavHost(
        navController = navController,
        startDestination = PuanHesaplamaEkrani
    )
    {
        composable<PuanHesaplamaEkrani> {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(verticalAlignment = Alignment.Top)
                {
                    //Renkli taşlar
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(0.8f)) {
                        for (renkler in 1..4) {
                            Row(modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                            ) {
                                for (tasSayisi in 1..13) {
                                    Box(contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                            .width(52.dp)
                                            .height(80.dp)
                                            .testTag(
                                                tag = if (renkler == 1) "Kırmızı"
                                                else if (renkler == 2) "Sarı"
                                                else if (renkler == 3) "Siyah"
                                                else "Mavi"
                                            )
                                            .background(
                                                color = if (renkler == 1) Color.Red
                                                else if (renkler == 2) Color.Yellow
                                                else if (renkler == 3) Color.Black
                                                else Color.Blue
                                            )
                                            .clickable { }

                                    ) {
                                        Text(text = tasSayisi.toString(), fontSize = 25.sp)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    //Sağ panel çakmalar ve çifte
                    Column {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(end = 7.dp)
                                .width(52.dp)
                                .height(80.dp)
                                .testTag("Çakma")
                                .background(Color.Green)
                                .clickable { }
                        ) {
                            Text(text = "Çakma", fontSize = 25.sp)
                        }
                        Switch(checked = ciftMiGidiyor,
                            onCheckedChange = {},
                            modifier = Modifier.size(50.dp),
                        )
                    }
                }
                //tuşlar geri al sıfırla
                Row {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2F80ED)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Sıfırla", color = Color.White, fontSize = 18.sp)
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2F80ED)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Geri al", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
            Box {//Seçili taşlar kalan puan yanda gösterilebilir

            }

        }
    }
}

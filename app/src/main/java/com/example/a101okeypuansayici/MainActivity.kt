package com.example.a101okeypuansayici
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
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
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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

data class Tas(var renk: String, var sayi: Int)

@Composable
fun NavYoneticisi() {
    val navController = rememberNavController()
    var ciftMiGidiyor by remember { mutableStateOf(false) }
    var okey by remember { mutableStateOf(Tas("",-1))}
    NavHost(
        navController = navController,
        startDestination = PuanHesaplamaEkrani
    )
    {
        composable<PuanHesaplamaEkrani> {
            var okeySec by remember { mutableStateOf(false) }
            var okeyiGoster by remember { mutableStateOf(false) }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(brush = Brush.verticalGradient(listOf(Color.DarkGray, Color.LightGray)))
            ) {
                //Okeyin gösterileceği kutu
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(14.dp,14.dp,14.dp,0.dp)
                        .width(52.dp)
                        .height(if (okey.sayi!=-1)80.dp else 0.dp)
                        .background(
                            color = if (okey.renk =="Kırmızı") Color.Red
                            else if (okey.renk =="Sarı") Color.Yellow
                            else if (okey.renk =="Siyah") Color.Black
                            else Color.Blue
                        )
                        .clickable { }

                ) {
                    Text(text = okey.sayi.toString(), fontSize = 25.sp)
                }
                //Okey seçme tuşu
                Button(
                    modifier = Modifier
                        .padding(14.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2F80ED)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        okeySec = true
                        okeyiGoster=true
                    })
                {
                    Text(text = "Okey Seç", color = Color.White, fontSize = 18.sp)
                }
                if (okeySec) {
                    val (izin,secilenOkeyTasi)=OkeyDiyalogSayi(onDismiss = { okeySec = false })
                    okey=secilenOkeyTasi
                    okeySec = izin

                }
                Row(verticalAlignment = Alignment.Top)
                {
                    //Renkli taşlar
                    Column(
                        modifier = Modifier
                            .border(1.dp, color = Color.Magenta)
                            .padding(16.dp, 16.dp, 16.dp, 0.dp)
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
                    Column(modifier = Modifier
                        .border(1.dp, Color.Green)
                        .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .width(52.dp)
                                .height(80.dp)
                                .testTag("Çakma")
                                .background(Color.Green)
                                .clickable { }
                        ) {
                            Text(text = "Çakma", fontSize = 25.sp)
                        }
                        Spacer(modifier = Modifier.height(25.dp))
                        Switch(checked = ciftMiGidiyor,
                            onCheckedChange = { ciftMiGidiyor = it },
                            modifier = Modifier.size(50.dp),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Green,
                                uncheckedThumbColor = Color.Gray,
                                checkedBorderColor = Color.Cyan,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                        Text(text = "Çifte Git")
                        // }

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

@Composable
fun OkeyDiyalogSayi(onDismiss: () -> Unit): Pair<Boolean,Tas> {
    var okeySayisiSecildiMi by remember { mutableStateOf(false) }
    var gonderilmeyeHazir by remember{ mutableStateOf(false)}
    var seciliSayi by remember{ mutableStateOf(-1)}
    var hazirTas by remember{mutableStateOf(Tas("",-1))}
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .width(380.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .shadow(8.dp, RoundedCornerShape(16.dp)) // Gölge efekti
                .padding(16.dp)
        ) {
            Column {
                LazyVerticalGrid(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    //burası oluşturulan kutuların genişliğinin kafasına göre değişmemesi için gerekli
                    columns = GridCells.Adaptive(minSize = 52.dp)
                ) {
                    items(13) { index ->
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .width(52.dp)
                                .height(80.dp)
                                .background(color = Color.Gray)
                                .clickable {
                                    okeySayisiSecildiMi = true
                                    seciliSayi = (index + 1)
                                }
                        ) {
                            Text(text = (index + 1).toString(), fontSize = 25.sp)
                        }
                    }
                }
                //joker sayısının seçilmesiyle beraber renk seçilmesine geçiş yapılır
                if (okeySayisiSecildiMi) {
                    //renk seçildikten sonra buraya false değeri döndürür ve bu false değeri diyaloğun kapatılmaya uygun olduğunu belirtir
                    val(izin,gelenTas)=OkeyDiyalogRenk(onDismiss = { okeySayisiSecildiMi = false }, seciliSayi = seciliSayi)
                    okeySayisiSecildiMi = izin
                    hazirTas=gelenTas
                    //eğer renk seçilmeden seçim kapatılırsa diyaloğun kapatılmasını önler
                    gonderilmeyeHazir = if (!okeySayisiSecildiMi) true
                    else false
                }
            }
        }
    }
    //sayı ve renk seçildiyse false döndürür bu sayede joker için  sayı seçme diyaloğu da kapatılır
    if (gonderilmeyeHazir) return Pair(okeySayisiSecildiMi,hazirTas)

    else return Pair(!gonderilmeyeHazir,hazirTas)

}

@Composable
fun OkeyDiyalogRenk(onDismiss: () -> Unit, seciliSayi: Int): Pair<Boolean,Tas> {
    var secilenOkey by remember{ mutableStateOf(Tas("",-1))}
    var renkSecmedim by remember { mutableStateOf(true) }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .width(380.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .shadow(8.dp, RoundedCornerShape(16.dp)) // Gölge efekti
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                //burası oluşturulan kutuların genişliğinin kafasına göre değişmemesi için gerekli
                columns = GridCells.Adaptive(minSize = 52.dp)
            ) {
                items(4) { index ->
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .width(52.dp)
                            .height(80.dp)
                            .background(color = if (index == 0) Color.Red
                            else if (index == 1) Color.Yellow
                            else if (index == 2) Color.Black
                            else Color.Blue)
                            .clickable {
                                renkSecmedim = false
                                secilenOkey.renk=if (index == 0) "Kırmızı"
                                else if (index == 1) "Sarı"
                                else if (index == 2) "Siyah"
                                else "Mavi"
                                secilenOkey.sayi=seciliSayi
                            }
                    )
                }
            }
        }
    }
    return Pair(renkSecmedim,secilenOkey)
}

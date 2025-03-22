package com.example.a101okeypuansayici

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

data class Tas(var renk: String, var sayi: Int, var resim: Int, var kullanilabilirMi: Boolean = true)

data class Isteka(var eldekiTaslar: MutableList<Tas>, var puan: Int)

@Composable
fun NavYoneticisi() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PuanHesaplamaEkrani
    )
    {
        composable<PuanHesaplamaEkrani> {
            val context = LocalContext.current
            var ciftMiGidiyor by remember { mutableStateOf(false) }
            var okey by remember { mutableStateOf(Tas("", -1, R.drawable.sahteokey)) }
            val sahteOkey by remember { mutableStateOf(Tas(okey.renk, (okey.sayi + 1), R.drawable.sahteokey)) }
            var okeySec by remember { mutableStateOf(false) }
            var okeyiGoster by remember { mutableStateOf(false) }
            val isteka by remember { mutableStateOf(Isteka(eldekiTaslar = mutableStateListOf(), 0)) }
            val diziliIsteka = remember { mutableStateListOf<Tas>() }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(brush = Brush.verticalGradient(listOf(Color.DarkGray, Color.LightGray)))
            ) {
                //Okeyin gösterileceği kutu oluşturuluyor
                Box(modifier = Modifier.padding(start = 16.dp)) {
                    TasGorunumuOlustur(okey, modifier = Modifier.clickable {})
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
                        okeyiGoster = true
                    })
                {
                    Text(text = "Okey Seç", color = Color.White, fontSize = 18.sp)
                }
                if (okeySec) {
                    val (izin, secilenOkeyTasi) = OkeyDiyalogSayi(onDismiss = { okeySec = false })
                    okey = secilenOkeyTasi
                    sahteOkey.renk = okey.renk
                    sahteOkey.sayi = okey.sayi
                    okeySec = izin

                }
                Row(verticalAlignment = Alignment.Top)
                {
                    //Renkli taşlar
                    Column(
                        modifier = Modifier
                            .padding(16.dp, 16.dp, 16.dp, 0.dp)
                            .fillMaxWidth(0.8f)) {
                        for (renkler in 1..4) {
                            Row(modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                            ) {
                                for (tasNumarasi in 1..13) {
                                    val renk = if (renkler == 1) "Kırmızı"
                                    else if (renkler == 2) "Sarı"
                                    else if (renkler == 3) "Siyah"
                                    else "Mavi"
                                    val olusanTas by remember { mutableStateOf(Tas(renk, tasNumarasi, 0)) }
                                    TasGorunumuOlustur(olusanTas, modifier = Modifier.clickable {
                                        if (isteka.eldekiTaslar.size < 22) {
                                            isteka.eldekiTaslar.add(olusanTas)
                                            Log.println(Log.INFO, "ismet", isteka.eldekiTaslar.size.toString() + ". taş seçildi")
                                            Log.println(Log.INFO, "ismet", isteka.eldekiTaslar.last().sayi.toString())
                                            Log.println(Log.INFO, "ismet", isteka.eldekiTaslar.last().renk)
                                            Log.println(Log.INFO, "ismet", "--------------------------------")
                                        } else {
                                            Log.println(Log.INFO, "ismet", "İSTEKA DOLDU")
                                        }
                                    })
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    //Sağ panel çakma ve çifte
                    Column(modifier = Modifier
                        .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        //buraya müdahale edilecek outofbounds vermesin
                        TasGorunumuOlustur(Tas(okey.renk, okey.sayi, R.drawable.sahteokey), modifier = Modifier.clickable {
                            if (okey.sayi != -1 && isteka.eldekiTaslar.size < 22) {
                                isteka.eldekiTaslar.add(Tas(okey.renk, okey.sayi, R.drawable.sahteokey))
                            } else {
                                Toast.makeText(context, "Okey seçilmedi", Toast.LENGTH_LONG).show()
                            }

                        })
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
                    }
                }
                //tuşlar geri al sıfırla
                Row {
                    Button(
                        onClick = {
                            isteka.eldekiTaslar.clear()
                            diziliIsteka.clear()
                        },
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
                //Seçili taşlar, kalan puan yanda gösterilebilir
                Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp)) {
                    LazyRow {
                        items(isteka.eldekiTaslar) { tas ->
                            TasGorunumuOlustur(tas, modifier = Modifier)
                        }
                    }
                    Text(text = isteka.eldekiTaslar.size.toString())
                    LazyRow {
                        diziliIsteka.addAll(EliDiz(isteka))
                        items(diziliIsteka) { item ->
                            TasGorunumuOlustur(item, modifier = Modifier)
                        }
                    }
                    Text(text = diziliIsteka.size.toString())
                }
            }
        }
    }
}

@Composable
fun OkeyDiyalogSayi(onDismiss: () -> Unit): Pair<Boolean, Tas> {
    var okeySayisiSecildiMi by remember { mutableStateOf(false) }
    var gonderilmeyeHazir by remember { mutableStateOf(false) }
    var seciliSayi by remember { mutableStateOf(-1) }
    var hazirTas by remember { mutableStateOf(Tas("", -1, 0)) }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .width(380.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color.White)
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
                                .background(shape = RoundedCornerShape(10.dp),
                                    color = Color(252, 244, 198)
                                )
                                .clickable {
                                    okeySayisiSecildiMi = true
                                    seciliSayi = (index + 1)
                                }
                        ) {
                            Text(text = (index + 1).toString(),
                                color = Color.Gray,
                                style = TextStyle(shadow = Shadow(Color.DarkGray, blurRadius = 4f)),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold)
                        }
                    }
                }
                //joker sayısının seçilmesiyle beraber renk seçilmesine geçiş yapılır
                if (okeySayisiSecildiMi) {
                    //renk seçildikten sonra buraya false değeri döndürür ve bu false değeri diyaloğun kapatılmaya uygun olduğunu belirtir
                    val (izin, gelenTas) = OkeyDiyalogRenk(onDismiss = { okeySayisiSecildiMi = false }, seciliSayi = seciliSayi)
                    okeySayisiSecildiMi = izin
                    hazirTas = gelenTas
                    //eğer renk seçilmeden seçim kapatılırsa diyaloğun kapatılmasını önler
                    gonderilmeyeHazir = if (!okeySayisiSecildiMi) true
                    else false
                }
            }
        }
    }
    //sayı ve renk seçildiyse false döndürür bu sayede joker için  sayı seçme diyaloğu da kapatılır
    if (gonderilmeyeHazir) return Pair(okeySayisiSecildiMi, hazirTas)
    else return Pair(!gonderilmeyeHazir, hazirTas)

}

@Composable
fun OkeyDiyalogRenk(onDismiss: () -> Unit, seciliSayi: Int): Pair<Boolean, Tas> {
    var secilenOkey by remember { mutableStateOf(Tas("", -1, 0)) }
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
                            .background(shape = RoundedCornerShape(10.dp), color = if (index == 0) Color(158, 21, 31)
                            else if (index == 1) Color(252, 158, 60)
                            else if (index == 2) Color.Black
                            else Color(0, 91, 170))
                            .clickable {
                                renkSecmedim = false
                                secilenOkey.renk = if (index == 0) "Kırmızı"
                                else if (index == 1) "Sarı"
                                else if (index == 2) "Siyah"
                                else "Mavi"
                                secilenOkey.sayi = seciliSayi
                            }
                    )
                }
            }
        }
    }
    return Pair(renkSecmedim, secilenOkey)
}

@Composable
fun TasGorunumuOlustur(uretilecekTas: Tas, modifier: Modifier) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(3.dp, 0.dp, 3.dp, 0.dp)
            .width(52.dp)
            .height(if (uretilecekTas.sayi != -1) 80.dp else 0.dp)
            .background(
                shape = RoundedCornerShape(10.dp),
                color = Color(252, 244, 198))
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
    )
    {
        //Normal taş üretimi, sahte okey else içinde üretiliyor
        if (uretilecekTas.resim == 0) {
            Text(
                style = TextStyle(shadow = Shadow(Color.Black, blurRadius = 4f)),
                color = if (uretilecekTas.renk == "Kırmızı") Color(158, 21, 31)
                else if (uretilecekTas.renk == "Sarı") Color(252, 158, 60)
                else if (uretilecekTas.renk == "Siyah") Color.Black
                else Color(0, 91, 170),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 12.dp),
                text = uretilecekTas.sayi.toString()
            )

            Box(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .border(
                        4.dp,
                        if (uretilecekTas.renk == "Kırmızı") Color(158, 21, 31)
                        else if (uretilecekTas.renk == "Sarı") Color(252, 158, 60)
                        else if (uretilecekTas.renk == "Siyah") Color.Black
                        else if (uretilecekTas.renk == "Mavi") Color(0, 91, 170)
                        else {
                            Color(0, 0, 0)
                        }, shape = CircleShape
                    )
                    .align(Alignment.BottomCenter)
                    .size(18.dp),
            ) {}
        }
        //Sahte okey üretiliyor
        else {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .testTag("Çakma")
                    .width(52.dp)
                    .height(80.dp)
                    .background(shape = RoundedCornerShape(10.dp),
                        color = Color(252, 244, 198)
                    )
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            ) {
                Image(painter = painterResource(id = uretilecekTas.resim),
                    contentDescription = "sahte okey", modifier = Modifier.size(30.dp))
            }
        }

    }
}

//puan hesaplama algoritması başlıyor yippie
fun EliDiz(alinanIsteka: Isteka): List<Tas> {
    var deneyselIsteka = alinanIsteka
    var per = mutableStateListOf<Tas>()
    var dizilecekIsteka = mutableStateListOf<Tas>()
    var eklemeIzni: Boolean

    if (deneyselIsteka.eldekiTaslar.size == 22) {
        deneyselIsteka.eldekiTaslar.sortBy { it.sayi }
        Loop@ for ((indis, soldakiTas) in deneyselIsteka.eldekiTaslar.withIndex()) {
            eklemeIzni = false
            if (soldakiTas.kullanilabilirMi) {
                if (indis + 1 < 22) {
                    val sagdakiTas = deneyselIsteka.eldekiTaslar.get(indis + 1)
                    //sayılar aynı ise
                    if (soldakiTas.sayi == sagdakiTas.sayi) {
                        //sayılar aynı, renkler farklı ise
                        if (soldakiTas.renk != sagdakiTas.renk) {
                            if (per.size < 1) {
                                per.add(soldakiTas)
                                per.add(sagdakiTas)
                            } else {
                                for ((i, renkAriyorum) in per.withIndex()) {
                                    if (i + 1 == per.size) {
                                        if (renkAriyorum.renk != sagdakiTas.renk) {
                                            eklemeIzni = true
                                        } else {
                                            eklemeIzni = false
                                            break
                                        }
                                    } else {
                                        if (renkAriyorum.renk == sagdakiTas.renk) {
                                            eklemeIzni = false
                                            break
                                        } else {
                                            eklemeIzni = true
                                            continue
                                        }
                                    }
                                }
                                if (eklemeIzni) {
                                    per.add(sagdakiTas)
                                    if (indis + 2==22)IstekayaEkle(per, dizilecekIsteka, deneyselIsteka.eldekiTaslar)
                                }
                            }
                            continue
                        } else continue
                    }
                    else if (true) {
                        if (per.size > 2) {
                            IstekayaEkle(per, dizilecekIsteka, deneyselIsteka.eldekiTaslar)
                            continue@Loop
                        }
                    }
                    //sayılar ardışık ise
                       else if (soldakiTas.sayi == (sagdakiTas.sayi - 1)) {
                           //sayılar ardışık, renkler aynı ise
                           if (soldakiTas.renk == sagdakiTas.renk) {
                               if (per.size < 1) {
                                   per.add(soldakiTas)
                                   per.add(sagdakiTas)
                                   continue
                               }
                               else{
                                   per.add(sagdakiTas)
                                   continue
                               }
                           }
                       }
                    else {
                        if (per.size > 2) {
                            IstekayaEkle(per, dizilecekIsteka, deneyselIsteka.eldekiTaslar)
                            continue@Loop
                        }
                    }

                }
            } else continue
        }
    }
    return dizilecekIsteka
}

fun IstekayaEkle(alinanPer: MutableList<Tas>, dizilecekIsteka: MutableList<Tas>, deneyselIsteka: MutableList<Tas>) {
    dizilecekIsteka.addAll(alinanPer.toList())
    for (x in alinanPer) {
        for (y in deneyselIsteka) {
            if (x.sayi == y.sayi&&x.renk==y.renk) {
                y.kullanilabilirMi = false
                Log.d("aloa", x.renk + "-" + x.sayi + "/" + y.renk + "-" + y.sayi)
                Log.d("aloa", alinanPer.size.toString())
                break
            }
        }
    }
    alinanPer.clear()
}
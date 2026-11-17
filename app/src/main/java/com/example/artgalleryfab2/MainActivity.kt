package com.example.artgalleryfab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artgalleryfab2.ui.theme.Artgalleryfab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Artgalleryfab2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtGalleryApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Artwork(
    @DrawableRes val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String,
    val description: String
)

private val artworks = listOf(
    Artwork(
        R.drawable.art1,
        "Nissan Skyline GT-R R32",
        "Nissan Motor Co.",
        "1989",
        "Mobil sport legendaris asal Jepang yang dijuluki 'Godzilla'. Mengusung mesin twin-turbo RB26DETT dan sistem penggerak seluruh roda ATTESA E-TS yang mendominasi ajang balap."
    ),
    Artwork(
        R.drawable.art2,
        "Nissan Skyline GT-R R33",
        "Nissan Motor Co.",
        "1995",
        "Generasi penerus R32 dengan aerodinamika yang disempurnakan dan sasis lebih kaku. Menjadi GT-R pertama yang mencatatkan waktu lap di bawah 8 menit di Nürburgring."
    ),
    Artwork(
        R.drawable.art3,
        "Toyota Supra MK4",
        "Toyota Motor Corporation",
        "1993",
        "Ikon JDM bermesin 2JZ-GTE twin-turbo yang sangat tangguh dan berpotensi memproduksi tenaga besar. Populer di seluruh dunia melalui budaya pop dan balap jalanan."
    ),
    Artwork(
        R.drawable.art4,
        "Mitsubishi Eclipse",
        "Mitsubishi Motors",
        "1989",
        "Mobil kupe sport hasil kerja sama Diamond-Star Motors (DSM) antara Mitsubishi dan Chrysler. Dikenal dengan varian GSX berbalut mesin 4G63 turbo dan penggerak AWD."
    ),
    Artwork(
        R.drawable.art5,
        "Mazda RX-7",
        "Mazda Motor Corporation",
        "1992",
        "Mobil sport indah berkode FD3S dengan mesin wankel rotary twin-turbo 13B-REW. Terkenal akan distribusi bobot sempurna 50:50 dan penanganan yang sangat lincah."
    ),
)

@Composable
fun ArtGalleryApp(modifier: Modifier = Modifier) {
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    val artwork = artworks[currentIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ArtworkWall(
                imageRes = artwork.imageRes,
                contentDescription = artwork.title
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        ArtworkDescriptor(
            title = artwork.title,
            artist = artwork.artist,
            year = artwork.year,
            description = artwork.description
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${currentIndex + 1} / ${artworks.size}",
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        DisplayController(
            onPrevious = {
                currentIndex = if (currentIndex == 0) artworks.lastIndex else currentIndex - 1
            },
            onNext = {
                currentIndex = if (currentIndex == artworks.lastIndex) 0 else currentIndex + 1
            }
        )
    }
}

@Composable
fun ArtworkWall(
    @DrawableRes imageRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = contentDescription,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(
    title: String,
    artist: String,
    year: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(artist)
                        }
                        append(" ")
                        withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                            append("($year)")
                        }
                    },
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DisplayController(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = onPrevious, modifier = Modifier.weight(1f)) {
            Text("sebelumnya")
        }
        Button(onClick = onNext, modifier = Modifier.weight(1f)) {
            Text("berikutnya")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtGalleryPreview() {
    Artgalleryfab2Theme {
        ArtGalleryApp()
    }
}
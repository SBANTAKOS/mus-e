package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.AppLanguageManager
import com.example.model.Artwork
import com.example.model.MuseumRepository
import com.example.model.SculptureType
import com.example.ui.components.Interactive3DViewer
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated

@Composable
fun SculpturesScreen(
    initialSculptureType: SculptureType = SculptureType.ISLAMIC_ASTROLABE,
    onListenAudio: (Artwork) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedType by remember { mutableStateOf(initialSculptureType) }
    val currentLang = AppLanguageManager.currentLanguage

    // Map sculpture type to corresponding artwork for audio guide
    val currentArtwork = remember(selectedType) {
        MuseumRepository.artworks.firstOrNull { it.sculptureType == selectedType }
            ?: MuseumRepository.artworks.first { it.has3DModel }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MuseumObsidian)
            .testTag("sculptures_screen")
    ) {
        // Top Header
        Surface(
            color = Color(0xFF0F1017),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = when (currentLang) {
                                AppLanguage.ENGLISH -> "3D SCULPTURES ROTUNDA"
                                AppLanguage.ARABIC -> "بهو المنحوتات ثلاثية الأبعاد"
                                AppLanguage.FRENCH -> "ROTONDE DES SCULPTURES 3D"
                            },
                            fontFamily = CinzelFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MuseumGoldPrimary,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = when (currentLang) {
                                AppLanguage.ENGLISH -> "Stereoscopic Inspection & Authentic Materials"
                                AppLanguage.ARABIC -> "فحص مجسم وتغيير الخامات الأصلية"
                                AppLanguage.FRENCH -> "Inspection Stéréoscopique & Matières"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF8E919E)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Sculpture Model Tabs
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(SculptureType.values()) { type ->
                        val isSelected = selectedType == type
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedType = type },
                            label = {
                                Text(
                                    text = type.title.split(" ").take(3).joinToString(" "),
                                    fontSize = 12.sp,
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MuseumGoldPrimary,
                                selectedLabelColor = MuseumObsidian,
                                containerColor = MuseumObsidianSurfaceElevated,
                                labelColor = Color(0xFFC7C9D6)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) MuseumGoldPrimary else MuseumObsidianBorder,
                                selectedBorderColor = MuseumGoldPrimary
                            )
                        )
                    }
                }
            }
        }

        // Full Interactive 3D Viewer
        Interactive3DViewer(
            sculptureType = selectedType,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            onListenAudio = { onListenAudio(currentArtwork) }
        )
    }
}

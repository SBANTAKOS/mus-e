package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.AppLanguageManager
import com.example.model.Artwork
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated
import com.example.ui.theme.PlayfairFontFamily

@Composable
fun HdArtworkDetailModal(
    artwork: Artwork,
    isFavorite: Boolean,
    onToggleFavorite: (Artwork) -> Unit,
    onPlayAudio: (Artwork) -> Unit,
    onOpen3DView: ((Artwork) -> Unit)? = null,
    onDismiss: () -> Unit
) {
    BackHandler { onDismiss() }

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var showFullCritique by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MuseumObsidian)
            .testTag("hd_artwork_detail_modal")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Interactive HD Zoom Area
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .background(Color.Black)
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onDoubleTap = {
                                if (scale > 1.2f) {
                                    scale = 1f
                                    offset = Offset.Zero
                                } else {
                                    scale = 2.5f
                                }
                            }
                        )
                    }
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(1f, 4.5f)
                            val maxOffsetX = (size.width * (scale - 1f)) / 2f
                            val maxOffsetY = (size.height * (scale - 1f)) / 2f
                            offset = Offset(
                                x = (offset.x + pan.x).coerceIn(-maxOffsetX, maxOffsetX),
                                y = (offset.y + pan.y).coerceIn(-maxOffsetY, maxOffsetY)
                            )
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = artwork.imageRes),
                    contentDescription = artwork.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        ),
                    contentScale = ContentScale.Fit
                )

                // Top Vignette gradient for top bar contrast
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xCC000000), Color.Transparent)
                            )
                        )
                )

                // Top Controls: Close button and Favorite toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color(0xAA11131A))
                            .border(1.dp, MuseumObsidianBorder, CircleShape)
                            .testTag("close_detail_modal")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fermer",
                            tint = MuseumGoldLight
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Zoom multiplier badge
                        if (scale > 1.05f) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = Color(0xCC11131A),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary)
                            ) {
                                Text(
                                    text = "Zoom ${(scale * 100).toInt()}%",
                                    fontSize = 11.sp,
                                    fontFamily = CinzelFontFamily,
                                    color = MuseumGoldPrimary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }

                        IconButton(
                            onClick = { onToggleFavorite(artwork) },
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color(0xAA11131A))
                                .border(1.dp, MuseumObsidianBorder, CircleShape)
                                .testTag("favorite_toggle_btn")
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Favori",
                                tint = if (isFavorite) MuseumGoldPrimary else Color.White
                            )
                        }
                    }
                }

                // Bottom Zoom Tip Badge
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0x99000000),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ZoomIn,
                            contentDescription = null,
                            tint = MuseumGoldPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        val lang = AppLanguageManager.currentLanguage
                        Text(
                            text = when (lang) {
                                AppLanguage.ENGLISH -> "Pinch to inspect ultra-HD details • Double tap to zoom"
                                AppLanguage.ARABIC -> "قرّب بإصبعيك لتفحص التفاصيل فائقة الدقة • انقر مرتين للتكبير"
                                AppLanguage.FRENCH -> "Pincez pour explorer les détails HD • Double tape pour agrandir"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xDDFFFFFF)
                        )
                    }
                }
            }

            val lang = AppLanguageManager.currentLanguage

            // Information Sheet Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Period & Wing Tag
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF282315),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = artwork.period.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MuseumGoldPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            letterSpacing = 1.2.sp
                        )
                    }

                    Text(
                        text = "${artwork.wing} • ${artwork.roomNumber}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF8E919E)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Title
                Text(
                    text = artwork.title,
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MuseumGoldLight,
                    lineHeight = 30.sp
                )

                // Artist & Year
                Text(
                    text = "${artwork.artist} (${artwork.year})",
                    fontFamily = PlayfairFontFamily,
                    fontSize = 17.sp,
                    color = Color(0xFFC7A863),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Primary Action Bar: Audio guide & 3D Model inspection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onPlayAudio(artwork) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("play_audio_guide_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MuseumGoldPrimary,
                            contentColor = MuseumObsidian
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = when (lang) {
                                AppLanguage.ENGLISH -> "Audio Guide"
                                AppLanguage.ARABIC -> "الشرح الصوتي"
                                AppLanguage.FRENCH -> "Écouter l'Audioguide"
                            },
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    if (artwork.has3DModel && onOpen3DView != null) {
                        OutlinedButton(
                            onClick = { onOpen3DView(artwork) },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("inspect_in_3d_btn"),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MuseumGoldPrimary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ViewInAr,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = when (lang) {
                                    AppLanguage.ENGLISH -> "Inspect in 3D"
                                    AppLanguage.ARABIC -> "استكشف 3D"
                                    AppLanguage.FRENCH -> "Vue 3D Interactive"
                                },
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Curatorial Specifications Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = MuseumObsidianSurfaceElevated),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = when (lang) {
                                AppLanguage.ENGLISH -> "CURATORIAL SPECIFICATIONS & CARTEL"
                                AppLanguage.ARABIC -> "البطاقة التعريفية والمواصفات الفنية"
                                AppLanguage.FRENCH -> "FICHE TECHNIQUE & CARTEL"
                            },
                            fontFamily = CinzelFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MuseumGoldPrimary,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        DetailMetaRow(
                            when (lang) {
                                AppLanguage.ENGLISH -> "Medium / Technique"
                                AppLanguage.ARABIC -> "الخامات والتقنية"
                                AppLanguage.FRENCH -> "Technique / Médium"
                            },
                            artwork.medium
                        )
                        DetailMetaRow(
                            when (lang) {
                                AppLanguage.ENGLISH -> "Dimensions"
                                AppLanguage.ARABIC -> "الأبعاد الحقيقية"
                                AppLanguage.FRENCH -> "Dimensions Réelles"
                            },
                            artwork.dimensions
                        )
                        DetailMetaRow(
                            when (lang) {
                                AppLanguage.ENGLISH -> "Provenance"
                                AppLanguage.ARABIC -> "المصدر والاكتشاف"
                                AppLanguage.FRENCH -> "Provenance"
                            },
                            artwork.provenance
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Curatorial Analysis
                Text(
                    text = when (lang) {
                        AppLanguage.ENGLISH -> "CURATORIAL ESSAY & INTERPRETATION"
                        AppLanguage.ARABIC -> "قراءة نقدية وشرح أمين المتحف"
                        AppLanguage.FRENCH -> "ANALYSE DU CONSERVATEUR"
                    },
                    fontFamily = CinzelFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MuseumGoldLight,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = artwork.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFD6D7E0),
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // In-depth critique box
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF141620),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = when (lang) {
                                AppLanguage.ENGLISH -> "WORKSHOP SECRETS & HISTORICAL CONTEXT"
                                AppLanguage.ARABIC -> "أسرار المحترف والسياق التاريخي"
                                AppLanguage.FRENCH -> "SECRET D'ATELIER & HISTOIRE"
                            },
                            fontFamily = CinzelFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MuseumGoldPrimary,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = artwork.curatorialNotes,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFB0B3C4),
                            lineHeight = 21.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun DetailMetaRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF7A7D8F),
            modifier = Modifier.width(130.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFFE4E4EB),
            modifier = Modifier.weight(1f)
        )
    }
}

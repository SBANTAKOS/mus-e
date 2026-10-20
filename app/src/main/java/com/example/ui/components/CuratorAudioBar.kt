package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.MuseumAudioManager
import com.example.audio.NarrationLanguage
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated

@Composable
fun CuratorAudioBar(
    audioManager: MuseumAudioManager,
    modifier: Modifier = Modifier
) {
    val artwork = audioManager.currentArtwork ?: return
    var isExpanded by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("curator_audio_bar"),
        color = Color(0xF212141D),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
        shadowElevation = 8.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Mini progress line at the very top
            LinearProgressIndicator(
                progress = { audioManager.playbackProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.5.dp),
                color = MuseumGoldPrimary,
                trackColor = Color(0x33FFFFFF)
            )

            // Main Bar Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Artwork thumbnail
                Image(
                    painter = painterResource(id = artwork.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, MuseumGoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Title & Curator label
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Audioguide Conservateur",
                            style = MaterialTheme.typography.labelSmall,
                            color = MuseumGoldPrimary,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        SoundwaveVisualizer(
                            isPlaying = audioManager.isPlaying,
                            modifier = Modifier.size(width = 24.dp, height = 12.dp)
                        )
                    }
                    Text(
                        text = artwork.title,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = MuseumGoldLight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Language toggle (FR/EN)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x33D4AF37),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { audioManager.toggleLanguage() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("toggle_audio_lang")
                ) {
                    Text(
                        text = audioManager.currentAppLanguage.shortLabel,
                        fontFamily = CinzelFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MuseumGoldLight
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Ambient Sound toggle
                IconButton(
                    onClick = { audioManager.toggleAmbientSound() },
                    modifier = Modifier.size(36.dp).testTag("toggle_ambient_sound")
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "Ambiance",
                        tint = if (audioManager.isAmbientSoundActive) MuseumGoldPrimary else Color(0xFF6B6E7E),
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Play / Pause Button
                IconButton(
                    onClick = { audioManager.pauseOrResume() },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(MuseumGoldPrimary)
                        .testTag("play_pause_audio_btn")
                ) {
                    Icon(
                        imageVector = if (audioManager.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Lecture / Pause",
                        tint = MuseumObsidian,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Close button
                IconButton(
                    onClick = { audioManager.stopAudio() },
                    modifier = Modifier.size(32.dp).testTag("close_audio_bar")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fermer",
                        tint = Color(0xFF7A7D8F),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Expanded view with transcription
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MuseumObsidianBorder)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Transcription de l'analyse :",
                        style = MaterialTheme.typography.labelSmall,
                        color = MuseumGoldPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (audioManager.language == NarrationLanguage.FRENCH) artwork.audioNarrationFr else artwork.audioNarrationEn,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFDDDDDD),
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SoundwaveVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "soundwave")
    val wave1 by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(350, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "w1"
    )
    val wave2 by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 0.3f,
        animationSpec = infiniteRepeatable(tween(420, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "w2"
    )
    val wave3 by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(280, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "w3"
    )

    Canvas(modifier = modifier) {
        val barWidth = 2.5.dp.toPx()
        val spacing = 3.dp.toPx()
        val maxHeight = size.height

        val heights = if (isPlaying) {
            listOf(wave1, wave2, wave3, wave1 * 0.7f)
        } else {
            listOf(0.2f, 0.2f, 0.2f, 0.2f)
        }

        var startX = 0f
        for (h in heights) {
            val barH = maxHeight * h
            val yOffset = (maxHeight - barH) / 2f
            drawLine(
                color = MuseumGoldPrimary,
                start = Offset(startX, yOffset),
                end = Offset(startX, yOffset + barH),
                strokeWidth = barWidth,
                cap = StrokeCap.Round
            )
            startX += barWidth + spacing
        }
    }
}

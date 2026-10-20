package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RoomHotspot
import com.example.model.VirtualRoom
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated
import com.example.ui.theme.PlayfairFontFamily
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun VirtualTourViewer(
    room: VirtualRoom,
    onSelectHotspotArtwork: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var panOffsetX by remember(room.id) { mutableFloatStateOf(0f) }
    var selectedHotspot by remember(room.id) { mutableStateOf<RoomHotspot?>(null) }
    var isGyroModeActive by remember { mutableStateOf(false) }

    // Pulsing hotspot animation
    val infiniteTransition = rememberInfiniteTransition(label = "hotspot_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    // Gentle ambient drift
    LaunchedEffect(isGyroModeActive) {
        while (isGyroModeActive) {
            kotlinx.coroutines.delay(16)
            panOffsetX = (panOffsetX - 0.8f)
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MuseumObsidian)
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    panOffsetX += dragAmount.x * 1.2f
                }
            }
    ) {
        val totalWidth = constraints.maxWidth.toFloat()
        val totalHeight = constraints.maxHeight.toFloat()
        val panoramaWidth = totalWidth * 2.8f

        // Wrap pan offset smoothly
        val normalizedPan = ((panOffsetX % panoramaWidth) + panoramaWidth) % panoramaWidth

        val panelWidthDp = with(androidx.compose.ui.platform.LocalDensity.current) { (panoramaWidth / 2f).toDp() }

        // Panoramic Image with seamless horizontal wrap
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(-normalizedPan.toInt(), 0) }
        ) {
            Row {
                Image(
                    painter = painterResource(id = room.panoramaRes),
                    contentDescription = room.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(panelWidthDp)
                        .fillMaxSize()
                )
                Image(
                    painter = painterResource(id = room.panoramaRes),
                    contentDescription = room.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(panelWidthDp)
                        .fillMaxSize()
                )
                Image(
                    painter = painterResource(id = room.panoramaRes),
                    contentDescription = room.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(panelWidthDp)
                        .fillMaxSize()
                )
            }
        }

        // Vignette Overlay for cinematic museum mood
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x99000000),
                            Color.Transparent,
                            Color(0xCC000000)
                        )
                    )
                )
        )

        // Render Hotspots over current panoramic projection
        for (hotspot in room.hotspots) {
            // Hotspot coordinates mapped to panorama width
            val hsWorldX = hotspot.xNormalized * (panoramaWidth / 2f)
            val screenX = ((hsWorldX - normalizedPan) % (panoramaWidth / 2f) + (panoramaWidth / 2f)) % (panoramaWidth / 2f)

            if (screenX >= -50f && screenX <= totalWidth + 50f) {
                val screenY = hotspot.yNormalized * totalHeight

                Box(
                    modifier = Modifier
                        .offset { IntOffset(screenX.toInt() - 24.dp.roundToPx(), screenY.toInt() - 24.dp.roundToPx()) }
                        .size(48.dp)
                        .clickable {
                            selectedHotspot = hotspot
                        }
                        .testTag("hotspot_${hotspot.id}"),
                    contentAlignment = Alignment.Center
                ) {
                    // Pulsing golden aura
                    Canvas(modifier = Modifier.size(44.dp)) {
                        drawCircle(
                            color = MuseumGoldPrimary.copy(alpha = pulseAlpha),
                            radius = (size.minDimension / 2f) * pulseScale
                        )
                        drawCircle(
                            color = MuseumGoldLight,
                            radius = size.minDimension * 0.28f
                        )
                        drawCircle(
                            color = MuseumObsidian,
                            radius = size.minDimension * 0.16f
                        )
                    }
                }
            }
        }

        // Top Navigation Bar / Room Identification
        Surface(
            color = Color(0xDD0D0E15),
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = room.name,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MuseumGoldLight
                    )
                    Text(
                        text = "${room.wingName} • ${room.floor}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF9FA2B4)
                    )
                }

                // Mini Radar Orientation Widget
                RadarOrientationWidget(
                    headingDeg = (normalizedPan / panoramaWidth * 360f),
                    modifier = Modifier.size(38.dp)
                )
            }
        }

        // Bottom Controls: Gyro Mode Toggle & Panoramic Swipe Hint
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isGyroModeActive) MuseumGoldPrimary else Color(0xCC11131A),
                border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
                modifier = Modifier.clickable { isGyroModeActive = !isGyroModeActive }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CompassCalibration,
                        contentDescription = null,
                        tint = if (isGyroModeActive) MuseumObsidian else MuseumGoldLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isGyroModeActive) "Auto-Dérive On" else "Mode Dérive 360°",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isGyroModeActive) MuseumObsidian else Color(0xFFDDDDDD)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0x99000000),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x22FFFFFF))
            ) {
                Text(
                    text = "Glisser 360° • Touchez les points d'or",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xEEFFFFFF),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }

        // Hotspot Info Popup Card
        AnimatedVisibility(
            visible = selectedHotspot != null,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            selectedHotspot?.let { hs ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xF0141620)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("hotspot_info_card")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = hs.title,
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MuseumGoldLight
                            )
                            Text(
                                text = "Époque : ${hs.era}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB0B3C4)
                            )
                        }

                        Button(
                            onClick = {
                                onSelectHotspotArtwork(hs.artworkId)
                                selectedHotspot = null
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MuseumGoldPrimary,
                                contentColor = MuseumObsidian
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("inspect_hotspot_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Inspecter",
                                fontFamily = CinzelFontFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RadarOrientationWidget(
    headingDeg: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = size.minDimension / 2f - 2.dp.toPx()

        // Radar dial ring
        drawCircle(
            color = Color(0x44D4AF37),
            radius = radius,
            center = center,
            style = Stroke(width = 1.5.dp.toPx())
        )

        // North indicator 'N'
        drawCircle(
            color = MuseumGoldPrimary,
            radius = 2.dp.toPx(),
            center = Offset(center.x, center.y - radius)
        )

        // Field of view cone
        val rad = Math.toRadians((headingDeg - 90).toDouble())
        val fovRad1 = rad - Math.toRadians(35.0)
        val fovRad2 = rad + Math.toRadians(35.0)

        val p1 = Offset(
            (center.x + radius * cos(fovRad1)).toFloat(),
            (center.y + radius * sin(fovRad1)).toFloat()
        )
        val p2 = Offset(
            (center.x + radius * cos(fovRad2)).toFloat(),
            (center.y + radius * sin(fovRad2)).toFloat()
        )

        val conePath = androidx.compose.ui.graphics.Path().apply {
            moveTo(center.x, center.y)
            lineTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
            close()
        }

        drawPath(
            path = conePath,
            brush = Brush.radialGradient(
                colors = listOf(Color(0x99D4AF37), Color.Transparent),
                center = center,
                radius = radius
            )
        )
    }
}

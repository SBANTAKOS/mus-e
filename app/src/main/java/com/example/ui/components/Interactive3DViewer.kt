package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engine3d.SculptureGenerator
import com.example.engine3d.SculptureRenderer
import com.example.model.AppLanguage
import com.example.model.AppLanguageManager
import com.example.model.SculptureMaterial
import com.example.model.SculptureType
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated
import com.example.ui.theme.PlayfairFontFamily

@Composable
fun Interactive3DViewer(
    sculptureType: SculptureType,
    modifier: Modifier = Modifier,
    onListenAudio: (() -> Unit)? = null
) {
    val mesh = remember(sculptureType) { SculptureGenerator.getMeshFor(sculptureType) }

    var yaw by remember { mutableFloatStateOf(0.4f) }
    var pitch by remember { mutableFloatStateOf(-0.1f) }
    var zoomScale by remember { mutableFloatStateOf(1.0f) }
    var lightAngle by remember { mutableFloatStateOf(0.8f) }
    var selectedMaterial by remember(sculptureType) {
        mutableStateOf(
            if (sculptureType == SculptureType.ISLAMIC_ASTROLABE) SculptureMaterial.DAMASCUS_BRASS
            else SculptureMaterial.CARRARA_MARBLE
        )
    }
    var isTurntableActive by remember { mutableStateOf(true) }
    var isWireframeOnly by remember { mutableStateOf(false) }
    val lang = AppLanguageManager.currentLanguage

    // Turntable continuous smooth rotation
    val turntableAnim = remember { Animatable(0f) }
    LaunchedEffect(isTurntableActive) {
        if (isTurntableActive) {
            turntableAnim.animateTo(
                targetValue = (2 * Math.PI).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 14000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val effectiveYaw = if (isTurntableActive) yaw + turntableAnim.value else yaw

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MuseumObsidian)
    ) {
        // Top 3D Viewport
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF1E212D),
                            Color(0xFF0F1117),
                            Color(0xFF07080B)
                        ),
                        radius = 800f
                    )
                )
                .pointerInput(isTurntableActive) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        isTurntableActive = false
                        yaw += pan.x * 0.012f
                        pitch = (pitch - pan.y * 0.012f).coerceIn(-1.2f, 1.2f)
                        zoomScale = (zoomScale * zoom).coerceIn(0.6f, 2.8f)
                    }
                }
        ) {
            // Interactive 3D Canvas
            Canvas(modifier = Modifier.fillMaxSize().testTag("sculpture_3d_canvas")) {
                val faces = SculptureRenderer.projectAndRender(
                    mesh = mesh,
                    pitchRad = pitch,
                    yawRad = effectiveYaw,
                    zoomScale = zoomScale,
                    canvasWidth = size.width,
                    canvasHeight = size.height,
                    lightAngleRad = lightAngle,
                    material = selectedMaterial,
                    wireframeMode = isWireframeOnly
                )

                // Draw pedestal podium shadow ellipse
                val centerX = size.width / 2f
                val podiumY = size.height * 0.82f
                drawOval(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0x99000000), Color.Transparent),
                        center = Offset(centerX, podiumY),
                        radius = size.width * 0.35f * zoomScale
                    ),
                    topLeft = Offset(centerX - size.width * 0.35f * zoomScale, podiumY - 20.dp.toPx()),
                    size = androidx.compose.ui.geometry.Size(size.width * 0.7f * zoomScale, 40.dp.toPx())
                )

                // Render faces
                for (face in faces) {
                    val path = Path().apply {
                        moveTo(face.p1.x, face.p1.y)
                        lineTo(face.p2.x, face.p2.y)
                        lineTo(face.p3.x, face.p3.y)
                        close()
                    }

                    if (!isWireframeOnly && selectedMaterial != SculptureMaterial.GLOWING_WIREFRAME) {
                        drawPath(path = path, color = face.color, style = Fill)
                    }

                    // Delicate wireframe outline for depth and craftsmanship
                    val strokeAlpha = if (isWireframeOnly || selectedMaterial == SculptureMaterial.GLOWING_WIREFRAME) 1.0f else 0.25f
                    drawPath(
                        path = path,
                        color = face.wireframeColor.copy(alpha = face.wireframeColor.alpha * strokeAlpha),
                        style = Stroke(width = if (selectedMaterial == SculptureMaterial.GLOWING_WIREFRAME) 1.5f else 0.8f)
                    )
                }

                // Spotlight beam indicator dot
                val lightX = centerX + 120.dp.toPx() * kotlin.math.cos(lightAngle)
                val lightY = 40.dp.toPx()
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(MuseumGoldLight, Color.Transparent),
                        center = Offset(lightX, lightY),
                        radius = 24.dp.toPx()
                    ),
                    radius = 24.dp.toPx(),
                    center = Offset(lightX, lightY)
                )
            }

            // Spotlight / Lighting Controller Pill (Top Floating)
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color(0xCC11131A),
                border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = "Projecteur de musée",
                        tint = MuseumGoldPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Éclairage Scénographique",
                        style = MaterialTheme.typography.bodySmall,
                        color = MuseumGoldLight
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Slider(
                        value = lightAngle,
                        onValueChange = { lightAngle = it },
                        valueRange = -3.14f..3.14f,
                        modifier = Modifier.width(100.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = MuseumGoldPrimary,
                            activeTrackColor = MuseumGoldPrimary,
                            inactiveTrackColor = Color(0x44FFFFFF)
                        )
                    )
                }
            }

            // Quick Floating Controls (Reset & Turntable)
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = {
                        yaw = 0.4f
                        pitch = -0.1f
                        zoomScale = 1.0f
                        isTurntableActive = false
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xCC11131A))
                        .border(1.dp, MuseumObsidianBorder, CircleShape)
                        .testTag("reset_3d_orientation")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Réinitialiser la vue",
                        tint = MuseumGoldLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Gesture hint overlay
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0x99000000),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = "Glisser pour tourner • Pincer pour zoomer",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xDDFFFFFF),
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }
        }

        // Bottom Curatorial Controls Sheet
        Surface(
            color = Color(0xFF101218),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                // Title and Turntable Switch Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = sculptureType.title,
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MuseumGoldLight
                        )
                        Text(
                            text = "${sculptureType.period} • ${mesh.faces.size} facettes polyédriques",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9FA2B4)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = when (lang) {
                                AppLanguage.ENGLISH -> "Rotate"
                                AppLanguage.ARABIC -> "دوران"
                                AppLanguage.FRENCH -> "Rotation"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isTurntableActive) MuseumGoldPrimary else Color(0xFF888888)
                        )
                        Switch(
                            checked = isTurntableActive,
                            onCheckedChange = { isTurntableActive = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MuseumGoldPrimary,
                                checkedTrackColor = Color(0xFF382D12),
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color(0xFF22242F)
                            ),
                            modifier = Modifier.testTag("toggle_turntable")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Material selector chips
                Text(
                    text = when (lang) {
                        AppLanguage.ENGLISH -> "Material & Surface Finish"
                        AppLanguage.ARABIC -> "الخامة والإكساء التراثي"
                        AppLanguage.FRENCH -> "Matière & Rendu Scénique"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF7A7D8F),
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(end = 8.dp)
                ) {
                    items(SculptureMaterial.values()) { mat ->
                        val isSelected = selectedMaterial == mat
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedMaterial = mat
                                isWireframeOnly = (mat == SculptureMaterial.GLOWING_WIREFRAME)
                            },
                            label = {
                                Text(
                                    text = mat.displayName,
                                    fontSize = 12.sp,
                                    fontFamily = CinzelFontFamily
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MuseumGoldPrimary,
                                selectedLabelColor = MuseumObsidian,
                                containerColor = MuseumObsidianSurfaceElevated,
                                labelColor = Color(0xFFCCCBD6)
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

                Spacer(modifier = Modifier.height(14.dp))

                // Action Row: Audio guide trigger
                if (onListenAudio != null) {
                    Button(
                        onClick = onListenAudio,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("listen_sculpture_audio"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MuseumGoldPrimary,
                            contentColor = MuseumObsidian
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Écouter l'analyse du conservateur",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

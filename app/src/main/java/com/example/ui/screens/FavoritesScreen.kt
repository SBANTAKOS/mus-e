package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Artwork
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldDark
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated
import com.example.ui.theme.PlayfairFontFamily

@Composable
fun FavoritesScreen(
    favoriteArtworks: List<Artwork>,
    onSelectArtwork: (Artwork) -> Unit,
    onRemoveFavorite: (Artwork) -> Unit,
    onPlayAudio: (Artwork) -> Unit,
    onNavigateToExhibitions: () -> Unit,
    isVipPatron: Boolean = false,
    userProfileManager: com.example.user.UserProfileManager? = null,
    onOpenProfileDialog: () -> Unit = {},
    onUpgradeClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MuseumObsidian)
            .testTag("favorites_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // VIP Holographic Member Card
        item {
            VipCollectorPassCard(
                favoriteCount = favoriteArtworks.size,
                isVipPatron = isVipPatron,
                userProfileManager = userProfileManager,
                onOpenProfileDialog = onOpenProfileDialog,
                onUpgradeClick = onUpgradeClick
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Section Title: Mon Salon Privé
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MON SALON DE COLLECTION",
                    fontFamily = CinzelFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MuseumGoldPrimary,
                    letterSpacing = 1.2.sp
                )

                Text(
                    text = "${favoriteArtworks.size} œuvres réunies",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF7A7D8F)
                )
            }
        }

        if (favoriteArtworks.isEmpty()) {
            item {
                Surface(
                    color = MuseumObsidianSurfaceElevated,
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = MuseumGoldPrimary.copy(alpha = 0.6f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Votre Salon est vide",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MuseumGoldLight
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Explorez les collections, marquez vos chefs-d'œuvre favoris et composez votre propre galerie privée.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF8E919E),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Button(
                            onClick = onNavigateToExhibitions,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MuseumGoldPrimary,
                                contentColor = MuseumObsidian
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("explore_museum_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Explore,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Visiter la Collection",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        } else {
            items(favoriteArtworks) { artwork ->
                FavoriteArtItem(
                    artwork = artwork,
                    onClick = { onSelectArtwork(artwork) },
                    onRemove = { onRemoveFavorite(artwork) },
                    onPlayAudio = { onPlayAudio(artwork) }
                )
            }
        }
    }
}

@Composable
fun VipCollectorPassCard(
    favoriteCount: Int,
    isVipPatron: Boolean = false,
    userProfileManager: com.example.user.UserProfileManager? = null,
    onOpenProfileDialog: () -> Unit = {},
    onUpgradeClick: () -> Unit = {}
) {
    val level = userProfileManager?.currentLevel ?: com.example.user.UserLevel.CONNAISSEUR
    val memberName = userProfileManager?.displayName ?: (if (isVipPatron) "Chan Ayoub" else "Visiteur Libre")
    val memberHonorific = userProfileManager?.patronHonorific ?: (if (isVipPatron) "Conservateur d'Honneur" else "Visiteur du Musée")
    val memberId = userProfileManager?.memberNumber ?: (if (isVipPatron) "ETH-VIP-2026-8849" else "ETH-FREE-9901")
    val totalXp = userProfileManager?.totalXp ?: 520

    // Holographic sheen animation
    val shimmerAnim = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        shimmerAnim.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF13141C)),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, if (isVipPatron) MuseumGoldPrimary else Color(0xFF4A4D62)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("vip_pass_card")
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Dynamic Holographic Foil Sheen
            Canvas(modifier = Modifier.matchParentSize()) {
                val shimmerX = size.width * shimmerAnim.value
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x22FFFFFF),
                            if (isVipPatron) Color(0x44D4AF37) else Color(0x228E919E),
                            Color(0x22FFFFFF),
                            Color.Transparent
                        ),
                        start = Offset(shimmerX - 120f, 0f),
                        end = Offset(shimmerX + 120f, size.height)
                    ),
                    start = Offset(shimmerX - 120f, 0f),
                    end = Offset(shimmerX + 120f, size.height),
                    strokeWidth = 140f
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header with Museum Crest & Pass Type
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = if (isVipPatron) MuseumGoldPrimary else Color(0xFF9E9FA8),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "MUSÉE ÉTHÉRÉ",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MuseumGoldLight,
                            letterSpacing = 2.sp
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isVipPatron) Color(0xFF2B220F) else Color(0xFF202330),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isVipPatron) MuseumGoldPrimary else Color(0xFF434658)),
                        modifier = Modifier.clickable { onOpenProfileDialog() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isVipPatron) "PASS PRIVILÈGE VIP" else "VISITEUR STANDARD",
                                fontFamily = CinzelFontFamily,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isVipPatron) MuseumGoldPrimary else Color(0xFFC7C9D6),
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Member Name / Rank with Level pill
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = memberHonorific.uppercase(),
                            fontFamily = CinzelFontFamily,
                            fontSize = 10.sp,
                            color = Color(0xFF9E9FA8),
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = memberName,
                            fontFamily = PlayfairFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MuseumGoldLight
                        )
                    }

                    // Level Chip
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = level.badgeColor.copy(alpha = 0.18f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, level.badgeColor),
                        modifier = Modifier
                            .clickable { onOpenProfileDialog() }
                            .padding(start = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Niv. ${level.levelNumber}",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = level.badgeColor
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$totalXp XP",
                                fontSize = 10.sp,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Pass info row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "IDENTIFIANT CARTE",
                            fontFamily = CinzelFontFamily,
                            fontSize = 9.sp,
                            color = Color(0xFF7A7D8F),
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = memberId,
                            fontFamily = CinzelFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isVipPatron) MuseumGoldPrimary else Color(0xFF9FA2B4)
                        )
                    }

                    Column {
                        Text(
                            text = "COLLECTION PRIVÉE",
                            fontFamily = CinzelFontFamily,
                            fontSize = 9.sp,
                            color = Color(0xFF7A7D8F),
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "$favoriteCount chefs-d'œuvre",
                            fontFamily = CinzelFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.QrCode,
                        contentDescription = "Pass VIP QR",
                        tint = MuseumGoldLight,
                        modifier = Modifier.size(34.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Quick Profile & Level button
                OutlinedButton(
                    onClick = onOpenProfileDialog,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFF45495E)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = MuseumGoldPrimary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Gérer mon Identité & Activité (Niveau ${level.levelNumber})",
                        fontFamily = CinzelFontFamily,
                        fontSize = 10.5.sp,
                        color = Color(0xFFE2E4ED)
                    )
                }

                // If not yet VIP, offer immediate 1-tap activation
                if (!isVipPatron) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onUpgradeClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .testTag("pass_card_upgrade_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MuseumGoldPrimary,
                            contentColor = MuseumObsidian
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Activer le Pass Mécène Perpétuel (4,99 $)",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteArtItem(
    artwork: Artwork,
    onClick: () -> Unit,
    onRemove: () -> Unit,
    onPlayAudio: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MuseumObsidianSurfaceElevated,
        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() }
            .testTag("fav_item_${artwork.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = artwork.imageRes),
                contentDescription = artwork.title,
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, MuseumGoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = artwork.period.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MuseumGoldPrimary,
                    letterSpacing = 0.8.sp
                )
                Text(
                    text = artwork.title,
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MuseumGoldLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${artwork.artist} (${artwork.year})",
                    fontFamily = PlayfairFontFamily,
                    fontSize = 12.sp,
                    color = Color(0xFF9E9FA8),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = onPlayAudio,
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Écouter",
                    tint = MuseumGoldPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Retirer",
                    tint = Color(0xFF7A7D8F),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

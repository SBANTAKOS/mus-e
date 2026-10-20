package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.AppLanguageManager
import com.example.model.Artwork
import com.example.model.ArtworkCategory
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurface
import com.example.ui.theme.MuseumObsidianSurfaceElevated
import com.example.ui.theme.PlayfairFontFamily

@Composable
fun ExhibitionsScreen(
    artworks: List<Artwork>,
    favoriteIds: Set<String>,
    onSelectArtwork: (Artwork) -> Unit,
    onToggleFavorite: (Artwork) -> Unit,
    onPlayAudio: (Artwork) -> Unit,
    onOpen3DView: (Artwork) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ArtworkCategory.ALL) }

    val currentLang = AppLanguageManager.currentLanguage

    val filteredArtworks = remember(artworks, searchQuery, selectedCategory, currentLang) {
        artworks.filter { art ->
            val matchesSearch = searchQuery.isBlank() ||
                art.title.contains(searchQuery, ignoreCase = true) ||
                art.titleEn.contains(searchQuery, ignoreCase = true) ||
                art.titleAr.contains(searchQuery, ignoreCase = true) ||
                art.artist.contains(searchQuery, ignoreCase = true) ||
                art.artistEn.contains(searchQuery, ignoreCase = true) ||
                art.artistAr.contains(searchQuery, ignoreCase = true) ||
                art.period.contains(searchQuery, ignoreCase = true) ||
                art.keywords.any { it.contains(searchQuery, ignoreCase = true) } ||
                art.suitableGrades.any { it.equals(searchQuery.trim(), ignoreCase = true) }

            val matchesCategory = when (selectedCategory) {
                ArtworkCategory.ALL -> true
                ArtworkCategory.MIDDLE_EAST_ISLAMIC -> art.categories.contains(ArtworkCategory.MIDDLE_EAST_ISLAMIC)
                ArtworkCategory.SCULPTURES_3D -> art.has3DModel
                ArtworkCategory.PAINTINGS -> !art.has3DModel
                ArtworkCategory.SCHOOL_CE1 -> art.suitableGrades.contains("CE1")
                ArtworkCategory.SCHOOL_CM1 -> art.suitableGrades.contains("CM1")
                ArtworkCategory.SCHOOL_CM2 -> art.suitableGrades.contains("CM2")
                else -> art.categories.contains(selectedCategory)
            }

            matchesSearch && matchesCategory
        }
    }

    val featuredArtwork = remember(artworks) { artworks.firstOrNull { it.isFeatured } ?: artworks.first() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MuseumObsidian)
            .testTag("exhibitions_list"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Hero Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
            ) {
                Image(
                    painter = painterResource(id = featuredArtwork.imageRes),
                    contentDescription = featuredArtwork.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Vignette gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x66000000),
                                    Color(0x9907080B),
                                    Color(0xFF07080B)
                                )
                            )
                        )
                )

                // Hero Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0x99D4AF37),
                        modifier = Modifier.clip(RoundedCornerShape(6.dp))
                    ) {
                        Text(
                            text = when (currentLang) {
                                AppLanguage.ENGLISH -> "FEATURED EXHIBITION"
                                AppLanguage.ARABIC -> "معرض الشرف الرئيسي"
                                AppLanguage.FRENCH -> "EXPOSITION MAJEURE"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = MuseumObsidian,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            letterSpacing = 1.2.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = featuredArtwork.title,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = MuseumGoldLight,
                        lineHeight = 32.sp
                    )

                    Text(
                        text = "${featuredArtwork.artist} • ${featuredArtwork.period}",
                        fontFamily = PlayfairFontFamily,
                        fontSize = 15.sp,
                        color = Color(0xFFDCD8C8)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MuseumGoldPrimary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSelectArtwork(featuredArtwork) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                .testTag("hero_explore_btn")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = MuseumObsidian,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Explorer l'Œuvre",
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MuseumObsidian
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0x44000000),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary),
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onPlayAudio(featuredArtwork) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                .testTag("hero_listen_btn")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = MuseumGoldLight,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Audioguide",
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MuseumGoldLight
                                )
                            }
                        }
                    }
                }
            }
        }

        // Search Bar
        item {
            Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = when (currentLang) {
                                AppLanguage.ENGLISH -> "Search artwork, keywords (astrolabe, Babylon) or grade (CE1, CM1, CM2)..."
                                AppLanguage.ARABIC -> "ابحث عن تحفة، كلمة مفتاحية (أسطرلاب، بابل) أو مستوى مدرسي..."
                                AppLanguage.FRENCH -> "Rechercher une œuvre, un mot-clé ou niveau (CE1, CM1, CM2)..."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B6E7E)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Recherche",
                            tint = MuseumGoldPrimary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_artworks_field"),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MuseumGoldPrimary,
                        unfocusedBorderColor = MuseumObsidianBorder,
                        focusedContainerColor = MuseumObsidianSurface,
                        unfocusedContainerColor = MuseumObsidianSurface,
                        cursorColor = MuseumGoldPrimary,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ArtworkCategory.values()) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = {
                            Text(
                                text = cat.getDisplayName(currentLang),
                                fontSize = 12.sp,
                                fontFamily = CinzelFontFamily
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MuseumGoldPrimary,
                            selectedLabelColor = MuseumObsidian,
                            containerColor = MuseumObsidianSurfaceElevated,
                            labelColor = Color(0xFFC5C7D4)
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
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Section Title: Collection Curatée
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "COLLECTION PERMANENTE",
                    fontFamily = CinzelFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MuseumGoldPrimary,
                    letterSpacing = 1.2.sp
                )

                Text(
                    text = "${filteredArtworks.size} œuvres",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF7A7D8F)
                )
            }
        }

        // Artwork Cards Grid/List
        items(filteredArtworks) { artwork ->
            ArtworkCard(
                artwork = artwork,
                isFavorite = favoriteIds.contains(artwork.id),
                onCardClick = { onSelectArtwork(artwork) },
                onToggleFavorite = { onToggleFavorite(artwork) },
                onPlayAudio = { onPlayAudio(artwork) },
                onOpen3DView = { onOpen3DView(artwork) }
            )
        }
    }
}

@Composable
fun ArtworkCard(
    artwork: Artwork,
    isFavorite: Boolean,
    onCardClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onPlayAudio: () -> Unit,
    onOpen3DView: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onCardClick() }
            .testTag("artwork_card_${artwork.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MuseumObsidianSurfaceElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Artwork Image with badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                Image(
                    painter = painterResource(id = artwork.imageRes),
                    contentDescription = artwork.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Bottom gradient shadow for readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xBB11131A)
                                )
                            )
                        )
                )

                // Top badges: 3D badge and Favorite Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (artwork.has3DModel) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xEE11131A),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ViewInAr,
                                    contentDescription = null,
                                    tint = MuseumGoldPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Modèle 3D",
                                    fontSize = 11.sp,
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = MuseumGoldPrimary
                                )
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.width(1.dp))
                    }

                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0x9911131A))
                            .border(1.dp, MuseumObsidianBorder, CircleShape)
                            .testTag("card_fav_${artwork.id}")
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Favori",
                            tint = if (isFavorite) MuseumGoldPrimary else Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Information details
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = artwork.period.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MuseumGoldPrimary,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = artwork.roomNumber,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF7A7D8F)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = artwork.title,
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MuseumGoldLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${artwork.artist} (${artwork.year})",
                    fontFamily = PlayfairFontFamily,
                    fontSize = 14.sp,
                    color = Color(0xFFC7A863)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = artwork.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9E9FA8),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                // Educational Grade Badges & Keyword Tags
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    artwork.suitableGrades.forEach { grade ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF1E2638),
                            border = androidx.compose.foundation.BorderStroke(0.6.dp, Color(0xFF4A90E2))
                        ) {
                            Text(
                                text = grade,
                                fontSize = 9.sp,
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF90CAF9),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    val previewKeywords = artwork.keywords.filter { it !in listOf("CE1", "CM1", "CM2") }.take(3)
                    previewKeywords.forEach { kw ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF1A1C26)
                        ) {
                            Text(
                                text = "#$kw",
                                fontSize = 9.sp,
                                color = Color(0xFF9E9FA8),
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF232532),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onPlayAudio() }
                            .padding(vertical = 8.dp)
                            .testTag("card_audio_${artwork.id}")
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = MuseumGoldLight,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Audioguide",
                                fontFamily = CinzelFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MuseumGoldLight
                            )
                        }
                    }

                    if (artwork.has3DModel) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF332912),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onOpen3DView() }
                                .padding(vertical = 8.dp)
                                .testTag("card_3d_${artwork.id}")
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ViewInAr,
                                    contentDescription = null,
                                    tint = MuseumGoldPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Sculpture 3D",
                                    fontFamily = CinzelFontFamily,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MuseumGoldPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

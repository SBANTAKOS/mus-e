package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.admob.AdMobManager
import com.example.audio.MuseumAudioManager
import com.example.billing.MembershipManager
import com.example.model.AppLanguage
import com.example.model.AppLanguageManager
import com.example.model.Artwork
import com.example.ui.components.AdMobBannerView
import com.example.ui.components.AdMobRewardedDialog
import com.example.ui.components.CulturalAdBanner
import com.example.ui.components.CuratorAudioBar
import com.example.ui.components.GooglePlayPurchaseDialog
import com.example.ui.components.HdArtworkDetailModal
import com.example.ui.components.PrivacyPolicyModal
import com.example.ui.components.UserProfileDialog
import com.example.ui.screens.EncyclopediaScreen
import com.example.ui.screens.ExhibitionsScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.SculpturesScreen
import com.example.ui.screens.VirtualTourScreen
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated
import com.example.ui.theme.MyApplicationTheme
import com.example.user.CulturalActivityType
import com.example.user.UserProfileManager
import com.example.viewmodel.MuseumTab
import com.example.viewmodel.MuseumViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private var audioManager: MuseumAudioManager? = null
    private var membershipManager: MembershipManager? = null
    private var userProfileManager: UserProfileManager? = null
    private var adMobManager: AdMobManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        audioManager = MuseumAudioManager(this)
        membershipManager = MembershipManager(this)
        userProfileManager = UserProfileManager(this)
        adMobManager = AdMobManager(this)

        setContent {
            MyApplicationTheme {
                MuseumApp(
                    audioManager = audioManager!!,
                    membershipManager = membershipManager!!,
                    userProfileManager = userProfileManager!!,
                    adMobManager = adMobManager!!
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioManager?.release()
    }
}

@Composable
fun MuseumApp(
    audioManager: MuseumAudioManager,
    membershipManager: MembershipManager,
    userProfileManager: UserProfileManager,
    adMobManager: AdMobManager,
    viewModel: MuseumViewModel = viewModel()
) {
    val currentTab = viewModel.currentTab
    val selectedArtwork = viewModel.selectedArtworkForDetail

    var showProfileDialog by remember { mutableStateOf(false) }
    var showRewardedAdDialog by remember { mutableStateOf(false) }
    var showPrivacyPolicyDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = selectedArtwork != null || currentTab != MuseumTab.EXHIBITIONS) {
        if (selectedArtwork != null) {
            viewModel.closeArtworkDetail()
        } else if (currentTab != MuseumTab.EXHIBITIONS) {
            viewModel.selectTab(MuseumTab.EXHIBITIONS)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        topBar = {
            MuseumTopBar(
                audioManager = audioManager,
                membershipManager = membershipManager,
                userProfileManager = userProfileManager,
                onOpenProfileDialog = { showProfileDialog = true },
                onOpenPrivacyPolicy = { showPrivacyPolicyDialog = true }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0C0D12))
            ) {
                // Persistent Curator Audio Bar if an artwork was triggered
                if (audioManager.currentArtwork != null) {
                    CuratorAudioBar(
                        audioManager = audioManager,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }

                // Official Google AdMob Adaptive Banner (displayed ONLY for free non-VIP users)
                if (!membershipManager.isVipPatron) {
                    AdMobBannerView(
                        creative = adMobManager.activeCreative,
                        onUpgradeClick = { membershipManager.openPurchaseFlow() },
                        onAdClick = { adMobManager.nextAd() }
                    )
                }

                MuseumBottomNavigation(
                    currentTab = currentTab,
                    onSelectTab = { viewModel.selectTab(it) }
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                MuseumTab.EXHIBITIONS -> {
                    ExhibitionsScreen(
                        artworks = viewModel.allArtworks,
                        favoriteIds = viewModel.favoriteArtworkIds,
                        onSelectArtwork = { artwork ->
                            userProfileManager.logActivity(CulturalActivityType.VIEW_ARTWORK, artwork.title)
                            viewModel.openArtworkDetail(artwork)
                        },
                        onToggleFavorite = { artwork ->
                            userProfileManager.logActivity(CulturalActivityType.ADD_FAVORITE, artwork.title)
                            viewModel.toggleFavorite(artwork)
                        },
                        onPlayAudio = { artwork ->
                            userProfileManager.logActivity(CulturalActivityType.LISTEN_AUDIO, artwork.title)
                            audioManager.playArtworkNarration(artwork)
                        },
                        onOpen3DView = { artwork ->
                            userProfileManager.logActivity(CulturalActivityType.EXPLORE_3D, artwork.title)
                            viewModel.open3DViewerForArtwork(artwork)
                        }
                    )
                }
                MuseumTab.SCULPTURES_3D -> {
                    SculpturesScreen(
                        initialSculptureType = viewModel.active3DSculptureType,
                        onListenAudio = { artwork ->
                            userProfileManager.logActivity(CulturalActivityType.LISTEN_AUDIO, artwork.title)
                            audioManager.playArtworkNarration(artwork)
                        }
                    )
                }
                MuseumTab.VIRTUAL_TOURS -> {
                    VirtualTourScreen(
                        onSelectArtwork = { artwork ->
                            userProfileManager.logActivity(CulturalActivityType.VIRTUAL_TOUR, artwork.title)
                            viewModel.openArtworkDetail(artwork)
                        }
                    )
                }
                MuseumTab.ENCYCLOPEDIA -> {
                    EncyclopediaScreen()
                }
                MuseumTab.FAVORITES -> {
                    FavoritesScreen(
                        favoriteArtworks = viewModel.favoriteArtworks,
                        onSelectArtwork = { artwork ->
                            userProfileManager.logActivity(CulturalActivityType.VIEW_ARTWORK, artwork.title)
                            viewModel.openArtworkDetail(artwork)
                        },
                        onRemoveFavorite = { viewModel.toggleFavorite(it) },
                        onPlayAudio = { artwork ->
                            userProfileManager.logActivity(CulturalActivityType.LISTEN_AUDIO, artwork.title)
                            audioManager.playArtworkNarration(artwork)
                        },
                        onNavigateToExhibitions = { viewModel.selectTab(MuseumTab.EXHIBITIONS) },
                        isVipPatron = membershipManager.isVipPatron,
                        userProfileManager = userProfileManager,
                        onOpenProfileDialog = { showProfileDialog = true },
                        onUpgradeClick = { membershipManager.openPurchaseFlow() }
                    )
                }
            }

            // Floating animated XP Toast notification
            userProfileManager.latestXpGainedNotification?.let { toastMsg ->
                LaunchedEffect(toastMsg) {
                    delay(2400)
                    userProfileManager.dismissXpNotification()
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .testTag("xp_gain_toast"),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color(0xF21C180E),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary),
                        shadowElevation = 8.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = MuseumGoldPrimary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = toastMsg,
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.5.sp,
                                color = MuseumGoldLight
                            )
                        }
                    }
                }
            }

            // Full-screen Ultra-HD artwork inspector overlay
            AnimatedVisibility(
                visible = selectedArtwork != null,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
            ) {
                selectedArtwork?.let { artwork ->
                    HdArtworkDetailModal(
                        artwork = artwork,
                        isFavorite = viewModel.favoriteArtworkIds.contains(artwork.id),
                        onToggleFavorite = {
                            userProfileManager.logActivity(CulturalActivityType.ADD_FAVORITE, artwork.title)
                            viewModel.toggleFavorite(artwork)
                        },
                        onPlayAudio = {
                            userProfileManager.logActivity(CulturalActivityType.LISTEN_AUDIO, artwork.title)
                            audioManager.playArtworkNarration(artwork)
                        },
                        onOpen3DView = {
                            userProfileManager.logActivity(CulturalActivityType.EXPLORE_3D, artwork.title)
                            viewModel.open3DViewerForArtwork(artwork)
                        },
                        onDismiss = { viewModel.closeArtworkDetail() }
                    )
                }
            }

            // User Profile, Google Sign-in & Activity Level Dialog
            if (showProfileDialog) {
                UserProfileDialog(
                    userProfileManager = userProfileManager,
                    isVipPatron = membershipManager.isVipPatron,
                    onWatchRewardedAdClick = {
                        showProfileDialog = false
                        showRewardedAdDialog = true
                    },
                    onDismiss = { showProfileDialog = false }
                )
            }

            // Google AdMob Rewarded Ad Simulation Dialog (+50 XP)
            if (showRewardedAdDialog) {
                AdMobRewardedDialog(
                    adMobManager = adMobManager,
                    onRewardEarned = {
                        userProfileManager.recordRewardedAdWatched()
                    },
                    onDismiss = {
                        showRewardedAdDialog = false
                    }
                )
            }

            // Google Play In-App Purchase Dialog simulation
            if (membershipManager.showGooglePlayBillingDialog) {
                GooglePlayPurchaseDialog(
                    membershipManager = membershipManager,
                    onDismiss = { membershipManager.dismissPurchaseFlow() }
                )
            }

            // Privacy Policy & Legal Terms Modal (Play Store & AdMob Compliance)
            if (showPrivacyPolicyDialog) {
                PrivacyPolicyModal(
                    onDismiss = { showPrivacyPolicyDialog = false }
                )
            }
        }
    }
}

@Composable
fun MuseumTopBar(
    audioManager: MuseumAudioManager,
    membershipManager: MembershipManager,
    userProfileManager: UserProfileManager,
    onOpenProfileDialog: () -> Unit,
    onOpenPrivacyPolicy: () -> Unit = {}
) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val userLevel = userProfileManager.currentLevel

    Surface(
        color = Color(0xF2090A0E),
        border = androidx.compose.foundation.BorderStroke(0.8.dp, MuseumObsidianBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = statusBarPadding + 6.dp,
                    bottom = 10.dp,
                    start = 14.dp,
                    end = 12.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = MuseumGoldPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Column {
                    Text(
                        text = "MUSÉE ÉTHÉRÉ",
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MuseumGoldLight,
                        letterSpacing = 1.6.sp
                    )
                    Text(
                        text = AppLanguageManager.currentLanguage.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF8E919E),
                        fontSize = 9.sp,
                        maxLines = 1
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // User Level Badge (Clickable to open Profile & Gamification sheet)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = userLevel.badgeColor.copy(alpha = 0.16f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, userLevel.badgeColor),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onOpenProfileDialog() }
                        .padding(horizontal = 7.dp, vertical = 3.5.dp)
                        .testTag("top_user_profile_btn")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Mon Profil",
                            tint = userLevel.badgeColor,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Niv. ${userLevel.levelNumber}",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = userLevel.badgeColor
                        )
                    }
                }

                // VIP Status Badge / Upgrade Button
                if (membershipManager.isVipPatron) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF2E2411),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { membershipManager.debugToggleVip() }
                            .padding(horizontal = 7.dp, vertical = 3.5.dp)
                            .testTag("vip_patron_badge")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = MuseumGoldPrimary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "VIP OR",
                                fontFamily = CinzelFontFamily,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = MuseumGoldPrimary
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF282315),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { membershipManager.openPurchaseFlow() }
                            .padding(horizontal = 7.dp, vertical = 3.5.dp)
                            .testTag("top_upgrade_btn")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = MuseumGoldPrimary,
                                modifier = Modifier.size(11.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "VIP 4,99 $",
                                fontFamily = CinzelFontFamily,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = MuseumGoldPrimary
                            )
                        }
                    }
                }

                // Language Switcher Pill (EN 🇺🇸 | AR 🇦🇪 | FR 🇫🇷)
                val currentLang = AppLanguageManager.currentLanguage
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF1E2230),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary.copy(alpha = 0.7f)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            val next = AppLanguageManager.cycleNextLanguage()
                            audioManager.setAppLanguage(next)
                        }
                        .padding(horizontal = 7.dp, vertical = 4.dp)
                        .testTag("top_language_toggle_btn")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = currentLang.flag,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = currentLang.shortLabel,
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = MuseumGoldLight
                        )
                    }
                }

                // Ambient Audio Soundscape Pill
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (audioManager.isAmbientSoundActive) Color(0xFF352912) else MuseumObsidianSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (audioManager.isAmbientSoundActive) MuseumGoldPrimary else MuseumObsidianBorder
                    ),
                    modifier = Modifier.clip(RoundedCornerShape(14.dp))
                ) {
                    IconButton(
                        onClick = { audioManager.toggleAmbientSound() },
                        modifier = Modifier.size(32.dp).testTag("top_ambient_sound_btn")
                    ) {
                        Icon(
                            imageVector = if (audioManager.isAmbientSoundActive) Icons.Default.GraphicEq else Icons.Default.MusicNote,
                            contentDescription = "Ambiance Sonore",
                            tint = if (audioManager.isAmbientSoundActive) MuseumGoldPrimary else Color(0xFF8E919E),
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }

                // Privacy Policy & Legal Terms Button (Google Play & AdMob Requirement)
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MuseumObsidianSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
                    modifier = Modifier.clip(RoundedCornerShape(14.dp))
                ) {
                    IconButton(
                        onClick = onOpenPrivacyPolicy,
                        modifier = Modifier.size(32.dp).testTag("top_privacy_policy_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Politique de Confidentialité",
                            tint = MuseumGoldLight,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MuseumBottomNavigation(
    currentTab: MuseumTab,
    onSelectTab: (MuseumTab) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF090A0E),
        contentColor = MuseumGoldPrimary,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets.navigationBars,
        modifier = Modifier.testTag("museum_bottom_nav")
    ) {
        val currentLang = AppLanguageManager.currentLanguage

        NavigationBarItem(
            selected = currentTab == MuseumTab.EXHIBITIONS,
            onClick = { onSelectTab(MuseumTab.EXHIBITIONS) },
            icon = {
                Icon(
                    imageVector = if (currentTab == MuseumTab.EXHIBITIONS) Icons.Filled.Collections else Icons.Outlined.Collections,
                    contentDescription = "Expositions"
                )
            },
            label = {
                Text(
                    text = when (currentLang) {
                        AppLanguage.ENGLISH -> "Exhibits"
                        AppLanguage.ARABIC -> "المعارض"
                        AppLanguage.FRENCH -> "Expos"
                    },
                    fontFamily = CinzelFontFamily,
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == MuseumTab.EXHIBITIONS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MuseumObsidian,
                selectedTextColor = MuseumGoldPrimary,
                indicatorColor = MuseumGoldPrimary,
                unselectedIconColor = Color(0xFF6F7282),
                unselectedTextColor = Color(0xFF6F7282)
            ),
            modifier = Modifier.testTag("nav_tab_exhibitions")
        )

        NavigationBarItem(
            selected = currentTab == MuseumTab.SCULPTURES_3D,
            onClick = { onSelectTab(MuseumTab.SCULPTURES_3D) },
            icon = {
                Icon(
                    imageVector = if (currentTab == MuseumTab.SCULPTURES_3D) Icons.Filled.ViewInAr else Icons.Outlined.ViewInAr,
                    contentDescription = "Sculptures 3D"
                )
            },
            label = {
                Text(
                    text = when (currentLang) {
                        AppLanguage.ENGLISH -> "3D Art"
                        AppLanguage.ARABIC -> "مجسمات 3D"
                        AppLanguage.FRENCH -> "3D"
                    },
                    fontFamily = CinzelFontFamily,
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == MuseumTab.SCULPTURES_3D) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MuseumObsidian,
                selectedTextColor = MuseumGoldPrimary,
                indicatorColor = MuseumGoldPrimary,
                unselectedIconColor = Color(0xFF6F7282),
                unselectedTextColor = Color(0xFF6F7282)
            ),
            modifier = Modifier.testTag("nav_tab_sculptures_3d")
        )

        NavigationBarItem(
            selected = currentTab == MuseumTab.VIRTUAL_TOURS,
            onClick = { onSelectTab(MuseumTab.VIRTUAL_TOURS) },
            icon = {
                Icon(
                    imageVector = if (currentTab == MuseumTab.VIRTUAL_TOURS) Icons.Filled.Explore else Icons.Outlined.Explore,
                    contentDescription = "Visites 360°"
                )
            },
            label = {
                Text(
                    text = when (currentLang) {
                        AppLanguage.ENGLISH -> "360° Tours"
                        AppLanguage.ARABIC -> "جولات 360°"
                        AppLanguage.FRENCH -> "Visites 360°"
                    },
                    fontFamily = CinzelFontFamily,
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == MuseumTab.VIRTUAL_TOURS) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MuseumObsidian,
                selectedTextColor = MuseumGoldPrimary,
                indicatorColor = MuseumGoldPrimary,
                unselectedIconColor = Color(0xFF6F7282),
                unselectedTextColor = Color(0xFF6F7282)
            ),
            modifier = Modifier.testTag("nav_tab_virtual_tours")
        )

        NavigationBarItem(
            selected = currentTab == MuseumTab.ENCYCLOPEDIA,
            onClick = { onSelectTab(MuseumTab.ENCYCLOPEDIA) },
            icon = {
                Icon(
                    imageVector = if (currentTab == MuseumTab.ENCYCLOPEDIA) Icons.Filled.AutoStories else Icons.Outlined.AutoStories,
                    contentDescription = "Codex"
                )
            },
            label = {
                Text(
                    text = when (currentLang) {
                        AppLanguage.ENGLISH -> "Codex"
                        AppLanguage.ARABIC -> "الموسوعة"
                        AppLanguage.FRENCH -> "Codex"
                    },
                    fontFamily = CinzelFontFamily,
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == MuseumTab.ENCYCLOPEDIA) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MuseumObsidian,
                selectedTextColor = MuseumGoldPrimary,
                indicatorColor = MuseumGoldPrimary,
                unselectedIconColor = Color(0xFF6F7282),
                unselectedTextColor = Color(0xFF6F7282)
            ),
            modifier = Modifier.testTag("nav_tab_encyclopedia")
        )

        NavigationBarItem(
            selected = currentTab == MuseumTab.FAVORITES,
            onClick = { onSelectTab(MuseumTab.FAVORITES) },
            icon = {
                Icon(
                    imageVector = if (currentTab == MuseumTab.FAVORITES) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Mon Pavillon"
                )
            },
            label = {
                Text(
                    text = when (currentLang) {
                        AppLanguage.ENGLISH -> "My Gallery"
                        AppLanguage.ARABIC -> "جناحي"
                        AppLanguage.FRENCH -> "Salon"
                    },
                    fontFamily = CinzelFontFamily,
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == MuseumTab.FAVORITES) FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MuseumObsidian,
                selectedTextColor = MuseumGoldPrimary,
                indicatorColor = MuseumGoldPrimary,
                unselectedIconColor = Color(0xFF6F7282),
                unselectedTextColor = Color(0xFF6F7282)
            ),
            modifier = Modifier.testTag("nav_tab_favorites")
        )
    }
}

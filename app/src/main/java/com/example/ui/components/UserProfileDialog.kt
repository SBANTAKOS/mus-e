package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated
import com.example.ui.theme.PlayfairFontFamily
import com.example.user.ActivityHistoryItem
import com.example.user.CulturalActivityType
import com.example.user.UserLevel
import com.example.user.UserProfileManager

@Composable
fun UserProfileDialog(
    userProfileManager: UserProfileManager,
    isVipPatron: Boolean,
    onWatchRewardedAdClick: () -> Unit,
    onDismiss: () -> Unit
) {
    var isEditingProfile by remember { mutableStateOf(false) }
    var editNameInput by remember { mutableStateOf(userProfileManager.displayName) }
    var editEmailInput by remember { mutableStateOf(userProfileManager.userEmail) }
    var editHonorificInput by remember { mutableStateOf(userProfileManager.patronHonorific) }
    var showPrivacyPolicy by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MuseumObsidian)
                .testTag("user_profile_modal"),
            color = MuseumObsidian
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // Top Dismiss Bar
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = MuseumGoldPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "IDENTITÉ & NIVEAU MÉCÈNE",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MuseumGoldLight,
                                letterSpacing = 1.5.sp
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MuseumObsidianSurfaceElevated)
                                .testTag("close_profile_dialog")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fermer",
                                tint = MuseumGoldLight,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }

                // 1. User Identity & Authentication Card
                item {
                    UserIdentityCard(
                        userProfileManager = userProfileManager,
                        isVipPatron = isVipPatron,
                        onEditClick = { isEditingProfile = !isEditingProfile },
                        onQuickGoogleSignIn = {
                            userProfileManager.signInWithGoogle("Chan Ayoub", "chan.ayoub@gmail.com")
                        },
                        onSignOut = {
                            userProfileManager.signOut()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Edit Profile Form if toggled
                if (isEditingProfile) {
                    item {
                        EditProfileCard(
                            name = editNameInput,
                            onNameChange = { editNameInput = it },
                            email = editEmailInput,
                            onEmailChange = { editEmailInput = it },
                            honorific = editHonorificInput,
                            onHonorificChange = { editHonorificInput = it },
                            onSave = {
                                userProfileManager.signInAsCustom(editNameInput, editEmailInput, editHonorificInput)
                                isEditingProfile = false
                            },
                            onCancel = { isEditingProfile = false }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // 2. Activity Level & Gamification System
                item {
                    UserActivityLevelCard(
                        userProfileManager = userProfileManager,
                        onWatchAdClick = onWatchRewardedAdClick
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // 3. Cultural Statistics Grid
                item {
                    Text(
                        text = "VOS STATISTIQUES CULTURELLES",
                        fontFamily = CinzelFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MuseumGoldPrimary,
                        letterSpacing = 1.2.sp,
                        modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
                    )

                    ActivityStatsGrid(userProfileManager = userProfileManager)
                    Spacer(modifier = Modifier.height(22.dp))
                }

                // 4. Perks Unlocked at this Level
                item {
                    LevelPerksCard(level = userProfileManager.currentLevel)
                    Spacer(modifier = Modifier.height(22.dp))
                }

                // 5. Recent Activity Feed
                item {
                    Text(
                        text = "HISTORIQUE RÉCENT D'ACTIVITÉ",
                        fontFamily = CinzelFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MuseumGoldPrimary,
                        letterSpacing = 1.2.sp,
                        modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
                    )
                }

                items(userProfileManager.recentActivities) { activity ->
                    ActivityHistoryRow(activity = activity)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = { showPrivacyPolicy = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("profile_privacy_policy_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MuseumGoldPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mentions Légales & Confidentialité",
                            fontFamily = CinzelFontFamily,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (showPrivacyPolicy) {
                PrivacyPolicyModal(
                    onDismiss = { showPrivacyPolicy = false }
                )
            }
        }
    }
}

@Composable
fun UserIdentityCard(
    userProfileManager: UserProfileManager,
    isVipPatron: Boolean,
    onEditClick: () -> Unit,
    onQuickGoogleSignIn: () -> Unit,
    onSignOut: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF13141D)),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, MuseumObsidianBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar with gold border
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF252838),
                    border = androidx.compose.foundation.BorderStroke(1.8.dp, MuseumGoldPrimary),
                    modifier = Modifier.size(58.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MuseumGoldLight,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = userProfileManager.displayName,
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = MuseumGoldLight,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        if (userProfileManager.isGoogleAccount) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(0xFF1A3B2B),
                                border = androidx.compose.foundation.BorderStroke(0.6.dp, Color(0xFF4ADE80))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF4ADE80),
                                        modifier = Modifier.size(10.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "Google",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF4ADE80)
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = userProfileManager.patronHonorific,
                        fontFamily = PlayfairFontFamily,
                        fontSize = 12.sp,
                        color = MuseumGoldPrimary
                    )

                    Text(
                        text = userProfileManager.userEmail,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF888B9C),
                        fontSize = 11.sp
                    )
                }

                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Modifier",
                        tint = MuseumGoldPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            Divider(color = Color(0xFF262838), thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Identification Credentials Summary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CARTE D'IDENTIFICATION",
                        fontFamily = CinzelFontFamily,
                        fontSize = 8.5.sp,
                        color = Color(0xFF7A7D8F),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = userProfileManager.memberNumber,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFFD6D8E4)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "STATUT DU COMPTE",
                        fontFamily = CinzelFontFamily,
                        fontSize = 8.5.sp,
                        color = Color(0xFF7A7D8F),
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = if (isVipPatron) "Mécène Perpétuel VIP" else if (userProfileManager.isUserSignedIn) "Visiteur Enregistré" else "Visiteur Invité",
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = if (isVipPatron) MuseumGoldPrimary else Color(0xFF9FA2B4)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action buttons: Google Sign In / Switch Profile / Sign Out
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!userProfileManager.isGoogleAccount) {
                    Button(
                        onClick = onQuickGoogleSignIn,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF282B3E),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Login,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFF60A5FA)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Lier Google",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                OutlinedButton(
                    onClick = onSignOut,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFF45495E)),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color(0xFFE57373)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (userProfileManager.isUserSignedIn) "Déconnexion" else "Mode Invité",
                        fontSize = 11.sp,
                        color = Color(0xFFC7C9D6)
                    )
                }
            }
        }
    }
}

@Composable
fun EditProfileCard(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    honorific: String,
    onHonorificChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF191B26)),
        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumGoldPrimary.copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "ÉDITER L'IDENTITÉ MÉCÈNE",
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MuseumGoldPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Nom complet ou pseudonyme", fontSize = 11.sp) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MuseumGoldPrimary,
                    unfocusedBorderColor = Color(0xFF383C52),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color(0xFFE2E4ED)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Adresse email", fontSize = 11.sp) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MuseumGoldPrimary,
                    unfocusedBorderColor = Color(0xFF383C52),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color(0xFFE2E4ED)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = honorific,
                onValueChange = onHonorificChange,
                label = { Text("Titre honorifique (ex: Érudit, Conservateur, Passionné)", fontSize = 11.sp) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MuseumGoldPrimary,
                    unfocusedBorderColor = Color(0xFF383C52),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color(0xFFE2E4ED)
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = onCancel) {
                    Text("Annuler", fontSize = 11.sp, color = Color(0xFFB0B3C2))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onSave,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MuseumGoldPrimary,
                        contentColor = MuseumObsidian
                    )
                ) {
                    Text("Enregistrer", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun UserActivityLevelCard(
    userProfileManager: UserProfileManager,
    onWatchAdClick: () -> Unit
) {
    val level = userProfileManager.currentLevel

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161824)),
        border = androidx.compose.foundation.BorderStroke(1.4.dp, level.badgeColor),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("user_activity_level_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Level Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = level.badgeColor.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, level.badgeColor),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${level.levelNumber}",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = level.badgeColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "NIVEAU D'ACTIVITÉ ${level.levelNumber}",
                            fontFamily = CinzelFontFamily,
                            fontSize = 9.sp,
                            color = level.badgeColor,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = level.titleFr,
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MuseumGoldLight
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF282012),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, MuseumGoldPrimary)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = MuseumGoldPrimary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${userProfileManager.totalXp} XP",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MuseumGoldPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // XP Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Progression vers Niveau ${level.levelNumber + 1}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF8B8E9E),
                    fontSize = 11.sp
                )
                Text(
                    text = "${(userProfileManager.currentLevelProgress * 100).toInt()}% (${userProfileManager.xpToNextLevel} XP restant)",
                    style = MaterialTheme.typography.bodySmall,
                    color = level.badgeColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { userProfileManager.currentLevelProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = level.badgeColor,
                trackColor = Color(0xFF2B2E40)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action to earn XP via AdMob or Daily bonus
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF212536),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFF454E6E)),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onWatchAdClick() }
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .testTag("watch_admob_xp_btn")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = null,
                            tint = Color(0xFF60A5FA),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = "Pub AdMob",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "+50 XP gratuit",
                                fontSize = 9.sp,
                                color = Color(0xFF93C5FD)
                            )
                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF282315),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, MuseumGoldPrimary),
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { userProfileManager.claimDailyBonus() }
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .testTag("claim_daily_xp_btn")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = null,
                            tint = MuseumGoldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = "Visite du Jour",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "+40 XP (Série ${userProfileManager.streakDays}j)",
                                fontSize = 9.sp,
                                color = MuseumGoldPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityStatsGrid(userProfileManager: UserProfileManager) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatMetricCard(
                icon = Icons.Default.Collections,
                count = "${userProfileManager.artworksContemplatedCount}",
                label = "Œuvres observées",
                modifier = Modifier.weight(1f)
            )
            StatMetricCard(
                icon = Icons.Default.ViewInAr,
                count = "${userProfileManager.sculpturesRotatedCount}",
                label = "Sculptures 3D",
                modifier = Modifier.weight(1f)
            )
            StatMetricCard(
                icon = Icons.Default.Videocam,
                count = "${userProfileManager.virtualToursCount}",
                label = "Tours 360°",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatMetricCard(
                icon = Icons.Default.Headphones,
                count = "${userProfileManager.audioListenedCount}",
                label = "Audioguides",
                modifier = Modifier.weight(1f)
            )
            StatMetricCard(
                icon = Icons.Default.School,
                count = "${userProfileManager.encyclopediaLookupsCount}",
                label = "Fiches scolaires",
                modifier = Modifier.weight(1f)
            )
            StatMetricCard(
                icon = Icons.Default.AutoAwesome,
                count = "${userProfileManager.streakDays} jours",
                label = "Série active",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatMetricCard(
    icon: ImageVector,
    count: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF141620),
        border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFF292C3D)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MuseumGoldPrimary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = count,
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF888B9C),
                fontSize = 9.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun LevelPerksCard(level: UserLevel) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF13151F)),
        border = androidx.compose.foundation.BorderStroke(1.dp, MuseumObsidianBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LockOpen,
                    contentDescription = null,
                    tint = level.badgeColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PRIVILÈGES DÉBLOQUÉS (NIVEAU ${level.levelNumber})",
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp,
                    color = level.badgeColor,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            level.perks.forEach { perk ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MuseumGoldPrimary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = perk,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFD6D8E4),
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ActivityHistoryRow(activity: ActivityHistoryItem) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF13151E),
        border = androidx.compose.foundation.BorderStroke(0.6.dp, Color(0xFF252736)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = activity.type.icon,
                    contentDescription = null,
                    tint = MuseumGoldPrimary,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = activity.title,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFE2E4EE),
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = activity.formattedTime,
                        fontSize = 9.sp,
                        color = Color(0xFF7E8192)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFF282112),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, MuseumGoldPrimary)
            ) {
                Text(
                    text = "+${activity.xpEarned} XP",
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.5.sp,
                    color = MuseumGoldPrimary,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

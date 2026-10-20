package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.admob.AdMobConfig
import com.example.admob.AdMobManager
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian

@Composable
fun AdMobRewardedDialog(
    adMobManager: AdMobManager,
    onRewardEarned: () -> Unit,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        adMobManager.runRewardedCountdown {
            onRewardEarned()
        }
    }

    Dialog(
        onDismissRequest = {
            if (adMobManager.rewardedAdCompleted) onDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = adMobManager.rewardedAdCompleted,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xE608090E))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF14151E)),
                border = androidx.compose.foundation.BorderStroke(1.2.dp, MuseumGoldPrimary.copy(alpha = 0.8f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admob_rewarded_dialog")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header Bar with AdMob info and countdown
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF232533)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Google AdMob",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFC4C7D6)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Bloc ${AdMobConfig.PROD_AD_UNIT_ID.takeLast(10)}",
                                    fontSize = 8.5.sp,
                                    color = Color(0xFF8B8E9E)
                                )
                            }
                        }

                        if (adMobManager.rewardedAdCompleted) {
                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Fermer",
                                    tint = MuseumGoldLight
                                )
                            }
                        } else {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFF2C2415),
                                border = androidx.compose.foundation.BorderStroke(0.8.dp, MuseumGoldPrimary)
                            ) {
                                Text(
                                    text = "Récompense dans ${adMobManager.rewardedAdCountdown}s",
                                    fontSize = 10.sp,
                                    fontFamily = CinzelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = MuseumGoldPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Video simulation display frame
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF0A0B10),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A2D3E)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                if (adMobManager.rewardedAdCompleted) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MuseumGoldPrimary,
                                        modifier = Modifier.size(46.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "+50 XP DÉBLOQUÉS !",
                                        fontFamily = CinzelFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = MuseumGoldLight
                                    )
                                    Text(
                                        text = "Votre activité de mécénat est enregistrée",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF9E9FA8),
                                        fontSize = 11.sp
                                    )
                                } else {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(36.dp),
                                        color = MuseumGoldPrimary,
                                        strokeWidth = 2.5.dp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = adMobManager.activeCreative.headline,
                                        fontFamily = CinzelFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = adMobManager.activeCreative.body,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFA5A8BA),
                                        fontSize = 11.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            // Audio & Test watermark in bottom corners
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "ANNONCE RÉCOMPENSÉE ADMOB",
                                    fontSize = 8.sp,
                                    color = Color(0xFF6B6E7E),
                                    letterSpacing = 0.5.sp
                                )
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = null,
                                    tint = Color(0xFF8E919E),
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (!adMobManager.rewardedAdCompleted) {
                        LinearProgressIndicator(
                            progress = { (5 - adMobManager.rewardedAdCountdown) / 5f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = MuseumGoldPrimary,
                            trackColor = Color(0xFF262838)
                        )
                    } else {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MuseumGoldPrimary,
                                contentColor = MuseumObsidian
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reward_claim_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Récupérer ma récompense (+50 XP)",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

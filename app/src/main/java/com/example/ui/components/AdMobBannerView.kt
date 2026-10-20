package com.example.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.admob.AdMobConfig
import com.example.admob.AdMobCreative
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldPrimary

/**
 * Standard Google AdMob Adaptive Banner slot for non-VIP users.
 * Displays official Google AdMob branding, AdChoices mark, Test Unit ID info,
 * and a direct In-App Purchase button to remove all ads for $4.99.
 */
@Composable
fun AdMobBannerView(
    creative: AdMobCreative,
    onUpgradeClick: () -> Unit,
    onAdClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp)
            .testTag("admob_banner_slot"),
        color = Color(0xF2101118),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(0.9.dp, Color(0xFF333547))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            // Google AdMob Header Tagline
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Official Google Ad badge style
                    Surface(
                        shape = RoundedCornerShape(3.dp),
                        color = Color(0xFFE8EAED),
                        modifier = Modifier.padding(end = 6.dp)
                    ) {
                        Text(
                            text = "Ad",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF202124),
                            modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp)
                        )
                    }

                    Text(
                        text = "Google AdMob • Bloc ${AdMobConfig.PROD_AD_UNIT_ID.takeLast(10)}",
                        fontSize = 9.sp,
                        color = Color(0xFF8A8D9F),
                        letterSpacing = 0.4.sp
                    )
                }

                // Remove ads CTA (VIP Pass at 4,99$)
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFF262013),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, MuseumGoldPrimary),
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { onUpgradeClick() }
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                        .testTag("remove_ads_button")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MuseumGoldPrimary,
                            modifier = Modifier.size(9.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Supprimer pubs (4,99 $)",
                            fontFamily = CinzelFontFamily,
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = MuseumGoldPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            // Ad Headline & CTA Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAdClick() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = creative.headline,
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.5.sp,
                        color = Color(0xFFECEEF4),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${creative.advertiser} — ${creative.body}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF9093A3),
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFF1E2838),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFF4A6B9C))
                ) {
                    Text(
                        text = creative.callToAction,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF93C5FD),
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

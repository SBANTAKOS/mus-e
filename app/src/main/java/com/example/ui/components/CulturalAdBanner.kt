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
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billing.CulturalAd
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated

@Composable
fun CulturalAdBanner(
    ad: CulturalAd,
    onUpgradeClick: () -> Unit,
    onNextAd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .testTag("cultural_ad_banner"),
        color = Color(0xF212131C),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF383A4C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Top Ad Badge & Remove Ads CTA
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = null,
                        tint = Color(0xFFC5A059),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "ANNONCE CULTURELLE • ${ad.sponsorName.uppercase()}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF9FA2B4),
                        fontSize = 9.sp,
                        letterSpacing = 0.5.sp
                    )
                }

                // Remove ads button
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF282315),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, MuseumGoldPrimary),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onUpgradeClick() }
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                        .testTag("remove_ads_btn")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MuseumGoldPrimary,
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Sans pub (4,99 $)",
                            fontFamily = CinzelFontFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = MuseumGoldPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Ad Headline & Description
            Text(
                text = ad.headline,
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFFE5E7EB),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = ad.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF8E919E),
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

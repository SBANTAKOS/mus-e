package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.billing.MembershipManager
import com.example.billing.PurchaseFlowState
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MuseumGoldLight
import com.example.ui.theme.MuseumGoldPrimary
import com.example.ui.theme.MuseumObsidian
import com.example.ui.theme.MuseumObsidianBorder
import com.example.ui.theme.MuseumObsidianSurfaceElevated
import kotlinx.coroutines.launch

@Composable
fun GooglePlayPurchaseDialog(
    membershipManager: MembershipManager,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = membershipManager.purchaseState

    Dialog(
        onDismissRequest = {
            if (state != PurchaseFlowState.PROCESSING) {
                onDismiss()
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.2.dp, MuseumGoldPrimary, RoundedCornerShape(24.dp))
                .testTag("google_play_purchase_dialog"),
            color = Color(0xFF13151E),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                // Top Header: Google Play Brand Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF232636),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Google Play",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF9FA2B4)
                            )
                            Text(
                                text = "Musée Éthéré",
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MuseumGoldLight
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp).testTag("close_purchase_dialog")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fermer",
                            tint = Color(0xFF8E919E),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Pass Title & Price
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF1B1E2B),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF323547)),
                    modifier = Modifier.fillMaxWidth()
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
                                text = membershipManager.productName,
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MuseumGoldLight
                            )
                            Text(
                                text = "Accès à vie • Paiement unique",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF8E919E)
                            )
                        }

                        Text(
                            text = membershipManager.currentPrice,
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MuseumGoldPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // VIP Benefits Checklist
                Text(
                    text = "AVANTAGES DU PASS MÉCÈNE :",
                    style = MaterialTheme.typography.labelSmall,
                    color = MuseumGoldPrimary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                VipBenefitItem("Expérience 100% sans aucune publicité")
                VipBenefitItem("Toutes les sculptures 3D en Or Impérial 24K & Marbre")
                VipBenefitItem("Exploration Ultra-HD zoom 450% illimitée")
                VipBenefitItem("Audioguides intégraux bilingues du conservateur")
                VipBenefitItem("Carte de Membre Mécène Or personnalisée")

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Method Simulation Box
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF10121A),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CreditCard,
                            contentDescription = null,
                            tint = Color(0xFF4A90E2),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Solde Google Play ou Carte bancaire",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                            Text(
                                text = "Paiement sécurisé via Google Play Services",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF7A7D8F),
                                fontSize = 10.sp
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFF7A7D8F),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Primary 1-Tap Buy Button
                Button(
                    onClick = {
                        scope.launch {
                            membershipManager.executeGooglePlayPurchase()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("confirm_google_play_purchase_btn"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MuseumGoldPrimary,
                        contentColor = MuseumObsidian
                    ),
                    shape = RoundedCornerShape(14.dp),
                    enabled = state != PurchaseFlowState.PROCESSING
                ) {
                    if (state == PurchaseFlowState.PROCESSING) {
                        CircularProgressIndicator(
                            color = MuseumObsidian,
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Traitement sécurisé...",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    } else if (state == PurchaseFlowState.SUCCESS) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MuseumObsidian,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pass Mécène Débloqué !",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    } else {
                        Text(
                            text = "Acheter en 1 clic (${membershipManager.currentPrice})",
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Restore Purchases
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                membershipManager.restorePurchases()
                            }
                        },
                        modifier = Modifier.testTag("restore_purchases_btn")
                    ) {
                        Text(
                            text = "Restaurer un achat précédent",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9FA2B4),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VipBenefitItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = MuseumGoldPrimary,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFE4E4EB),
            fontSize = 12.sp
        )
    }
}

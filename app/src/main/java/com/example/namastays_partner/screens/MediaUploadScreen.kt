package com.example.namastays_partner.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.namastays_partner.ui.theme.GradientButton
import com.example.namastays_partner.ui.theme.InterFont
import com.example.namastays_partner.ui.theme.rememberAdaptiveSpacing
import com.example.namastays_partner.utilities.PhotoCategoryState
import com.example.namastays_partner.utilities.compressImage
import com.example.namastays_partner.viewmodel.VendorOnboardingViewModel
import kotlinx.coroutines.launch
import sh.calvin.reorderable.*


//YES

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaUploadScreen(navController: NavController,
                      viewModel: VendorOnboardingViewModel) {

    val primaryBlue = Color(0xFF1F3A68)
    val bgColor = Color(0xFFF3F4F6)

    val spacing = rememberAdaptiveSpacing()
    val verticalEdgePadding = spacing.edge


    val categories = viewModel.state.photoCategories

    // Scroll state for the full column
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().background(bgColor)) {

        // Main scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = verticalEdgePadding,
                    bottom = 100.dp //  reserve space for bottom button
                )
        ) {

            // --- Top Bar ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ArrowBack, null, modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() })
                Spacer(Modifier.width(16.dp))
                Text(
                    "Registration",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    fontFamily = InterFont
                )
            }

            // --- Step Progress ---
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "STEP 6 OF 7",
                        fontSize = 12.sp,
                        color = primaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFont
                    )
                    Text(
                        "86% Completed",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = InterFont
                    )
                }

                Spacer(Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = 0.86f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(50)),
                    color = primaryBlue,
                    trackColor = Color(0xFFE5E7EB)
                )
            }

            Spacer(Modifier.height(24.dp))

            // --- Photo Categories ---
            categories.forEachIndexed { index, category ->
                PhotoCategoryCard(
                    category = category,
                    categoryIndex = index,
                    viewModel = viewModel
                )
            }

        }

        // --- Sticky Bottom Button ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {


            GradientButton(
                text = "Continue",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate("pricing_and_policies_screen")
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoCategoryCard(category: PhotoCategoryState,
                      categoryIndex: Int,
                      viewModel: VendorOnboardingViewModel) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedImage by remember(categoryIndex) { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->

        scope.launch {
            uris.forEach { uri ->
                val compressedFile = compressImage(context, uri)

                val finalUri = compressedFile?.let { Uri.fromFile(it) } ?: uri

                viewModel.addPhotos(categoryIndex, listOf(finalUri))
            }
        }
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(Modifier.padding(20.dp)) {

            Text(
                category.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Spacer(Modifier.height(12.dp))

            if (category.photos.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF1F5F9))
                        .clickable { launcher.launch("image/*") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.CloudUpload, null, tint = Color.Blue)
                    Spacer(Modifier.height(6.dp))
                    Text(category.description, color = Color.Gray)
                    Spacer(Modifier.height(6.dp))
                    Text("Tap to Upload", color = Color.Blue)
                }

            } else {

                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    items(
                        items = category.photos,
                        key = { it.toString() }
                    ) { uri ->

                        Card(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(120.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {

                            Box {

                                Image(
                                    painter = rememberAsyncImagePainter(uri),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            selectedImage = uri
                                        }
                                )

                                if (category.photos.first() == uri) {
                                    Text(
                                        "Cover",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .background(
                                                Color(0x99000000),
                                                RoundedCornerShape(6.dp)
                                            )
                                            .padding(4.dp)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFE2E8F0))
                                .clickable { launcher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, null)
                        }
                    }
                }
            }
        }
    }

    selectedImage?.let { uri ->
        AlertDialog(
            onDismissRequest = { selectedImage = null },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.removePhoto(categoryIndex, uri)
                    selectedImage = null

                }) {
                    Text("Remove", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    selectedImage = null
                }) {
                    Text("Close")
                }
            },
            text = {
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }
        )
    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun MediaUploadScreenPreview() {
//    MediaUploadScreen(navController = rememberNavController())
//}
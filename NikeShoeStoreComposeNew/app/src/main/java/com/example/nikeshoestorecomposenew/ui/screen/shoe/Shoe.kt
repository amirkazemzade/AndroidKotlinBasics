package com.example.nikeshoestorecomposenew.ui.screen.shoe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nikeshoestorecomposenew.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Shoe(
    id: Int,
    title: String,
    price: Int,
    previous_price: Int,
    image: String,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(topBar = {
        TopAppBar(title = {}, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = null,
                )
            }
        }, actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_bookmark_border_24),
                    contentDescription = null,
                )
            }
        })
    }) {
        val shoeImage =
            "https://s3.ir-thr-at1.arvanstorage.com/nike/zoomx-vaporfly-next-running-shoe-T5qg9m.jpg"
        val sizes = listOf(37, 38, 39, 40, 41, 42)
        val colors = listOf(
            Color(0xFF60b0fd),
            Color(0xFFDCACA6),
            Color.White,
            Color.Gray
        )

        val selectedSize = remember {
            mutableStateOf(39)
        }
        val selectedColor = remember {
            mutableStateOf(colors[3])
        }

        Column(modifier = Modifier.padding(it)) {
            Box(modifier = Modifier.height(20.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Box(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = shoeImage,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 46.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.surface)
            )
            Box(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.size),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
            )
            Box(modifier = Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 24.dp),
            ) {
                items(
                    count = sizes.size,
                ) { index ->
                    val currentItem = sizes[index]
                    val isSelected = currentItem == selectedSize.intValue
                    val backGroundColor =
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .then(
                                if (isSelected)
                                    Modifier.border(
                                        width = 1.dp,
                                        MaterialTheme.colorScheme.onSurface,
                                        shape = RoundedCornerShape(10.dp),
                                    )
                                else
                                    Modifier
                            )
                            .size(40.dp)
                            .background(backGroundColor)
                            .clickable {
                                selectedSize.intValue = currentItem
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "$currentItem",
                            fontSize = 18.sp,
                        )
                    }
                }
            }
            Box(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.color),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
            )
            Box(modifier = Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 24.dp),
            ) {
                items(
                    count = colors.size,
                ) { index ->
                    val currentItem = colors[index]
                    val isSelected = currentItem == selectedColor.value
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .then(
                                if (isSelected)
                                    Modifier
                                        .border(
                                            width = 1.dp,
                                            MaterialTheme.colorScheme.onSurface,
                                            shape = RoundedCornerShape(10.dp),
                                        )
                                else
                                    Modifier
                            )
                            .background(currentItem)
                            .size(40.dp)
                            .clickable {
                                selectedColor.value = currentItem
                            },
                    )
                }
            }
        }
    }
}
//
//@Preview
//@Composable
//fun Default() {
//    NikeShoeStoreComposeNewTheme {
//        Shoe()
//    }
//}
//
//@Preview(locale = "fa")
//@Composable
//fun DefaultFa() {
//    NikeShoeStoreComposeNewTheme {
//        Shoe()
//    }
//}
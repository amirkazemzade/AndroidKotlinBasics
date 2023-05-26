package com.example.nikeshoestorecomposenew.ui.screen.shoe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nikeshoestorecomposenew.R
import com.example.nikeshoestorecomposenew.ui.theme.NikeShoeStoreComposeNewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Shoe(
    id: Int,
    title: String,
    image: String,
    price: Int,
    previousPrice: Int,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        topBar = {
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
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(text = stringResource(id = R.string.add_to_cart))
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$previousPrice ${stringResource(id = R.string.tooman)}",
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(text = "$price ${stringResource(id = R.string.tooman)}", fontSize = 15.sp)
                }
            }
        }
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            Box(modifier = Modifier.height(20.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 46.dp)
                    .fillMaxWidth(),
            )
            Box(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 46.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .aspectRatio(1.0f)
                    .background(MaterialTheme.colorScheme.surface)
            )
            Box(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.size),
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                )
            }
            Box(modifier = Modifier.height(8.dp))

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
                mutableStateOf(colors[0])
            }

            LazyRow(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                items(
                    sizes.size,
                ) { index ->
                    val size = sizes[index]
                    val isSelected = size == selectedSize.intValue
                    val background =
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .size(40.dp)
                            .background(background)
                            .clickable {
                                selectedSize.intValue = size
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        val textColor =
                            if (isSelected) MaterialTheme.colorScheme.onPrimary
                            else Color.Unspecified
                        Text(
                            text = size.toString(),
                            fontSize = 18.sp,
                            color = textColor,
                        )
                    }
                }
            }
            Box(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.color),
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                )
            }
            Box(modifier = Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                items(
                    colors.size,
                ) { index ->
                    val color = colors[index]
                    val isSelected = color == selectedColor.value
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .size(40.dp)
                            .background(color)
                            .then(
                                if (isSelected)
                                    Modifier.border(
                                        width = 1.dp,
                                        MaterialTheme.colorScheme.onSurface,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                else Modifier
                            )
                            .clickable {
                                selectedColor.value = color
                            },
                    )
                }
            }
            Box(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.comments),
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                )
                Text(
                    text = stringResource(id = R.string.more),
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Box(modifier = Modifier.height(8.dp))
            val commentTitle = "خیلی شیک و اسپرت"
            val date = "1398.29.14, 01:04"
            val email = "saeedshahinit@gmail.com"
            val content = "واقعا یکی از بهترین کفش هاییه که تا حالا دیدمم خیلی خوبه"
            Box(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp
                        )
                    )
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primaryContainer
                    )
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = commentTitle, fontSize = 14.sp)
                        Text(text = date, fontSize = 13.sp)
                    }
                    Box(modifier = Modifier.height(4.dp))
                    Text(text = email, fontSize = 13.sp)
                    Box(modifier = Modifier.height(8.dp))
                    Text(
                        text = content, fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Default() {
    NikeShoeStoreComposeNewTheme {
        Shoe(
            id = 9,
            title = "کفش ورزشی مردانه مدل ZoomX",
            image = "https://s3.ir-thr-at1.arvanstorage.com/nike/zoomx-vaporfly-next-running-shoe-T5qg9m.jpg",
            price = 1873000,
            previousPrice = 1973000,
        )
    }
}

@Preview(locale = "fa")
@Composable
fun DefaultFa() {
    NikeShoeStoreComposeNewTheme {
        Shoe(
            id = 9,
            title = "کفش ورزشی مردانه مدل ZoomX",
            image = "https://s3.ir-thr-at1.arvanstorage.com/nike/zoomx-vaporfly-next-running-shoe-T5qg9m.jpg",
            price = 1873000,
            previousPrice = 1973000,
        )
    }
}
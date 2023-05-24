package com.example.nikeshoestorecomposenew.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nikeshoestorecomposenew.R
import com.example.nikeshoestorecomposenew.ui.theme.NikeShoeStoreComposeNewTheme

@Composable
fun Home(viewModel: HomeViewModel = viewModel()) {
    val query = remember {
        mutableStateOf("")
    }
    val active = remember {
        mutableStateOf(false)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(modifier = Modifier.height(32.dp))
        SearchBox(query, active)
        Box(modifier = Modifier.height(16.dp))
        Slider(viewModel)
        Box(modifier = Modifier.height(20.dp))
        NewestShoesHeader()
        NewestShoes(viewModel = viewModel)
        // TODO: show favorite shoes here instead of newest shoes
        NewestShoesHeader()
        NewestShoes(viewModel = viewModel)
    }
}

@Composable
private fun NewestShoes(viewModel: HomeViewModel) {
    val context = LocalContext.current
    val shoeState = viewModel.newestShoesState.value
    if (shoeState is ShoeState.Initial) {
        viewModel.fetchNewestShoes(context)
    }
    if (shoeState is ShoeState.Success) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            val shoes = shoeState.shoes
            items(count = shoes.size) { index ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(130.dp)

                ) {
                    val shoe = shoes[index]
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .width(130.dp)
                            .height(130.dp)
                            .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.TopEnd,
                    ) {

                        AsyncImage(
                            model = shoe.image,
                            contentDescription = null,
                        )
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_bookmark_border_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Box(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        text = shoe.title,
                        fontSize = 16.sp,
                    )
                    Text(
                        text = "${shoe.previous_price} ${stringResource(id = R.string.tooman)}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp),
                        textAlign = TextAlign.End,
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "${shoe.price} ${stringResource(id = R.string.tooman)}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp),
                        textAlign = TextAlign.End,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun NewestShoesHeader() {
    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.newest),
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
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun Slider(viewModel: HomeViewModel) {
    val context = LocalContext.current
    val bannersState = viewModel.bannerState.value
    if (bannersState is BannerState.Initial) {
        viewModel.fetchBanners(context)
    }
    if (bannersState is BannerState.Success) {
        Column {
            val pagerState = rememberPagerState {
                bannersState.banners.size
            }
            HorizontalPager(state = pagerState) { index ->
                AsyncImage(
                    model = bannersState.banners[index].image,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .clip(shape = RoundedCornerShape(18.dp))
                        .fillMaxWidth()
                        .aspectRatio(2.0f)
                        .background(color = MaterialTheme.colorScheme.surface)
                )
            }
            Box(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                for (index in bannersState.banners.indices) {
                    if (index == pagerState.currentPage) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .width(40.dp)
                                .height(8.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        )

                    } else {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(CircleShape)
                                .width(8.dp)
                                .height(8.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchBox(
    query: MutableState<String>,
    active: MutableState<Boolean>,
) {
    SearchBar(modifier = Modifier
        .padding(horizontal = 32.dp)
        .fillMaxWidth(),
        query = query.value,
        onQueryChange = {
            query.value = it
        },
        onSearch = {
            active.value = false
        },
        active = active.value,
        onActiveChange = {
            active.value = it
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                color = MaterialTheme.colorScheme.outline,
            )
        }) {

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun Default() {
    NikeShoeStoreComposeNewTheme {
        Scaffold {
            Home()
        }
    }
}
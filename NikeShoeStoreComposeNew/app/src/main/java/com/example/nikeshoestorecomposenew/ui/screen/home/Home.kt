package com.example.nikeshoestorecomposenew.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .scrollable(scrollState, orientation = Orientation.Vertical)
            .padding(top = 24.dp)
    ) {
        SearchBox(query)
        Box(modifier = Modifier.height(16.dp))
        Banner(viewModel)
        Box(modifier = Modifier.height(20.dp))
        NewestShoesHeader()
        NewestShoesList(viewModel)
    }
}

@Composable
private fun NewestShoesList(viewModel: HomeViewModel) {
    val context = LocalContext.current
    val newestShoesState = viewModel.shoesListState.value

    LaunchedEffect(newestShoesState) {
        if (newestShoesState == ShoesListState.Initial) {
            viewModel.getNewestShoes(context)
        }
    }

    if (newestShoesState is ShoesListState.Success)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
        ) {
            val shoes = newestShoesState.response
            items(count = shoes.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .width(130.dp)
                        .height(130.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = shoes[index].image,
                        contentDescription = null,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_bookmark_border_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.newest),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
        TextButton(onClick = {}) {
            Text(
                text = stringResource(id = R.string.more),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun Banner(viewModel: HomeViewModel) {
    val context = LocalContext.current
    val bannerState = viewModel.bannerState.value
    LaunchedEffect(bannerState) {
        if (bannerState == BannerState.Initial) {
            viewModel.getBanners(context)
        }
    }
    if (bannerState is BannerState.Success) {
        Column {
            val banners = bannerState.response
            val pageState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f
            ) {
                banners.size
            }

            BannerSlider(pageState, banners.map { it.image })
            Box(modifier = Modifier.height(12.dp))
            SliderPositionIndicator(banners.size, pageState)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SliderPositionIndicator(
    pageCount: Int,
    pageState: PagerState,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        for (dotIndex in 0 until pageCount) {
            if (dotIndex == pageState.currentPage) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .width(48.dp)
                        .height(8.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .size(8.dp)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                )

            }
        }

    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun BannerSlider(
    pageState: PagerState,
    banners: List<String>,
) {
    HorizontalPager(
        state = pageState,
        key = { index -> banners[index] }
    ) { index ->
        AsyncImage(
            model = banners[index],
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .aspectRatio(2.0f)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchBox(query: MutableState<String>) {
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        query = query.value,
        onQueryChange = {
            query.value = it
        },
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        placeholder = {
            Text(
                stringResource(id = R.string.search),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        colors = SearchBarDefaults.colors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp)
    ) {}
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(locale = "fa")
@Composable
fun DefaultFa() {
    NikeShoeStoreComposeNewTheme {
        Scaffold {
            Home()
        }
    }
}
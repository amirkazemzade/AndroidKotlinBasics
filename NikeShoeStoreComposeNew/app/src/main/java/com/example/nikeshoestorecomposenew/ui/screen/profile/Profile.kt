package com.example.nikeshoestorecomposenew.ui.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nikeshoestorecomposenew.R
import com.example.nikeshoestorecomposenew.ui.theme.NikeShoeStoreComposeNewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.profile))
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_person_24),
                        "",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Box(
                    modifier = Modifier.width(16.dp)
                )
                Text(
                    "user@gamil.com",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            Box(modifier = Modifier.height(40.dp))
            ActionTile(
                onTap = {
                    // TODO: navigate to bookmark screen
                },
                title = stringResource(id = R.string.favoriet_list), icon = painterResource(
                    id = R.drawable.outline_bookmark_border_24,
                )
            )
            Box(modifier = Modifier.height(16.dp))
            ActionTile(
                onTap = {
                    // TODO: navigate to bookmark screen
                },
                title = stringResource(id = R.string.order_history), icon = painterResource(
                    id = R.drawable.outline_local_mall_24,
                )
            )
            Box(modifier = Modifier.height(16.dp))
            ActionTile(
                onTap = {
                    // TODO: navigate to bookmark screen
                },
                title = stringResource(id = R.string.log_out), icon = painterResource(
                    id = R.drawable.baseline_exit_to_app_24,
                )
            )
        }
    }
}

@Composable
fun ActionTile(
    onTap: () -> Unit,
    title: String,
    icon: Painter
) {
    Card(
        modifier = Modifier.clickable(onClick = onTap),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Icon(
                painter = icon,
                ""
            )
            Box(modifier = Modifier.width(16.dp))
            Text(text = title)
            Box(modifier = Modifier.weight(1.0f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                ""
            )
        }
    }
}

@Preview
@Composable
fun Default() {
    NikeShoeStoreComposeNewTheme {
        Profile()
    }
}

@Preview(locale = "fa")
@Composable
fun DefaultFa() {
    NikeShoeStoreComposeNewTheme {
        Profile()
    }
}
package com.example.nikeshoestorecomposenew.ui.screen.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nikeshoestorecomposenew.R
import com.example.nikeshoestorecomposenew.data.model.reponse.CommentResponse
import com.example.nikeshoestorecomposenew.ui.states.CommentsState
import com.example.nikeshoestorecomposenew.ui.theme.NikeShoeStoreComposeNewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Comments(
    id: Int,
    navController: NavHostController = rememberNavController(),
    viewModel: CommentsViewModel = viewModel(),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.comments), fontSize = 22.sp)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            when (val commentsState = viewModel.commentsState.value) {
                is CommentsState.Success -> {
                    val comments = commentsState.comments
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(count = comments.size) { index ->
                            CommentBox(comment = comments[index])
                        }
                    }
                }

                is CommentsState.Error -> {
                    Text(text = commentsState.message, color = MaterialTheme.colorScheme.error)
                }

                CommentsState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                CommentsState.Initial -> {
                    viewModel.fetchDocuments(id)
                }
            }
        }
    }
}

@Composable
fun CommentBox(comment: CommentResponse) {
    Box(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 8.dp)
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
                Text(text = comment.title, fontSize = 14.sp)
                Text(text = comment.date, fontSize = 13.sp)
            }
            Box(modifier = Modifier.height(4.dp))
            Text(text = comment.author.email, fontSize = 13.sp)
            Box(modifier = Modifier.height(8.dp))
            Text(
                text = comment.content, fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

}

@Preview
@Composable
fun Default() {
    NikeShoeStoreComposeNewTheme {
        Comments(1)
    }
}

@Preview(locale = "fa")
@Composable
fun DefaultFa() {
    NikeShoeStoreComposeNewTheme {
        Comments(1)
    }
}
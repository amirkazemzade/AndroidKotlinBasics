package com.example.nikeshoestorecomposenew.screen.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nikeshoestorecomposenew.R
import com.example.nikeshoestorecomposenew.navigation.AppDestinations
import com.example.nikeshoestorecomposenew.ui.theme.NikeShoeStoreComposeNewTheme


@Composable
fun Auth(
    navController: NavHostController = rememberNavController(),
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val loginState = viewModel.loginResult.collectAsState()
    val signUpState = viewModel.signUpResult.collectAsState()

    SideEffect {
        if (viewModel.authType.value == AuthType.Login) {
            val state = loginState.value
            if (state is LoginState.Success) {
                navController.navigate(AppDestinations.profile)
            } else if (state is LoginState.Error) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            val state = signUpState.value
            if (state is SignUpState.Success) {
                navController.navigate(AppDestinations.profile)
            } else if (state is SignUpState.Error) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    if (loginState.value == LoginState.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else if (signUpState.value == SignUpState.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else {
        AuthForm(viewModel)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AuthForm(viewModel: AuthViewModel) {
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 40.dp)
                .fillMaxSize()
        ) {
            Text(
                text = actionText(viewModel.authType.value),
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp
            )
            Box(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.auth_helper_text))
            Box(modifier = Modifier.height(80.dp))
            EmailTextField(viewModel)
            Box(modifier = Modifier.height(16.dp))
            PasswordTextField(viewModel)
            Box(modifier = Modifier.height(40.dp))
            AuthButton(viewModel)
            Box(modifier = Modifier.height(20.dp))
            Text(text = otherActionHelperText(viewModel.authType.value))
            TextButton(onClick = {
                viewModel.changeAuthType()
            }) {
                Text(text = otherActionText(viewModel.authType.value))
            }
        }
    }
}

@Composable
private fun AuthButton(
    viewModel: AuthViewModel
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            viewModel.auth()
        },
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = actionText(viewModel.authType.value))
    }
}

@Composable
private fun actionText(type: AuthType) =
    if (type == AuthType.Login) stringResource(R.string.login)
    else stringResource(R.string.sign_up)

@Composable
private fun otherActionText(type: AuthType) =
    if (type == AuthType.SignUp) stringResource(id = R.string.login) else stringResource(id = R.string.sign_up)

@Composable
private fun otherActionHelperText(type: AuthType) =
    if (type != AuthType.SignUp)
        stringResource(id = R.string.do_not_have_account_question)
    else
        stringResource(id = R.string.have_account_question)


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PasswordTextField(viewModel: AuthViewModel) {
    val showPassword = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = viewModel.password.value,
        onValueChange = {
            viewModel.setPassword(it)
        },
        placeholder = { Text(text = stringResource(id = R.string.password)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_key_24),
                "",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                showPassword.value = !showPassword.value
            }) {
                Icon(
                    painter = if (showPassword.value) painterResource(id = R.drawable.baseline_visibility_24)
                    else painterResource(id = R.drawable.baseline_visibility_off_24),
                    "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        visualTransformation = if (showPassword.value) VisualTransformation.None
        else PasswordVisualTransformation(),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        )
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EmailTextField(viewModel: AuthViewModel) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = viewModel.email.value,
        onValueChange = {
            viewModel.setEmail(it)
        },
        placeholder = { Text(text = stringResource(id = R.string.email)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_alternate_email_24),
                "",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        )
    )
}

@Preview
@Composable
fun Default() {
    NikeShoeStoreComposeNewTheme {
        Auth()
    }
}

@Preview(locale = "fa")
@Composable
fun DefaultFa() {
    NikeShoeStoreComposeNewTheme {
        Auth()
    }
}
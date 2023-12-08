package com.tmr.chemx2


import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(navController: NavController) {
    val auth = Firebase.auth
    val context = LocalContext.current
    val oneTapClient = remember {
        Identity.getSignInClient(context)
    }

    val googleSignInHelper = remember {
        GoogleSignInHelper(
            auth = auth,
            context = context,
            oneTapClient = oneTapClient
        )
    }

    val coroutineScope = rememberCoroutineScope()

    val isAuthenticated by remember(auth.currentUser) {
        derivedStateOf {
            auth.currentUser != null
        }
    }

    val signInWithGoogleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                    googleSignInHelper.signInWithIntent(
                        result.data ?: return@rememberLauncherForActivityResult,
                        navController
                    ).launchIn(coroutineScope)

            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        IconButton(onClick = { /*todo: action */}) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back_button)
            )
        }

        Text(
            modifier = Modifier.width(200.dp),
            text = stringResource(R.string.welcome_back),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { newText ->
                email = newText
            },
            label = {
                Text(text = stringResource(R.string.email_address))
            },
            placeholder = {
                Text(text = stringResource(R.string.sample_email))
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
        )

        var pass by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        TextField(
            value = pass,
            onValueChange = { newText ->
                pass = newText
            },
            label = {
                Text(text = stringResource(R.string.create_password))
            },
            placeholder = {
                Text(text = stringResource(R.string.enter_your_password))
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.CheckCircle
                else Icons.Filled.AddCircle

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(imageVector = image, contentDescription = "")
                }
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.remember_me))

            var switchState by remember {
                mutableStateOf(false)
            }
            Switch(checked = switchState, onCheckedChange = {
                switchState = it
            })
        }

        Button(
            onClick = { googleSignInHelper.createIntent {
                signInWithGoogleLauncher.launch(
                    IntentSenderRequest.Builder(
                        it.intentSender
                    ).build()
                )
            } },
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 255, green = 125, blue = 0),
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.sign_in), fontWeight = FontWeight.Bold)
        }

    }

}


package com.example.catfacts.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.catfacts.R
import com.example.catfacts.data.Repo
import com.example.catfacts.data.User
import com.example.catfacts.helper.Item
import com.example.catfacts.ui_settings.typography
import dev.chrisbanes.accompanist.coil.CoilImage

@Preview(showBackground = true)
@Composable
fun ShowScreen() {
    Screen(
        items = listOf(User("Login", "http://image.url")),
        selectedUser = "Login",
        isErrorOccurs = false,
        isLoading = false,
        errMsg = "",
        getUsersCallback = {},
        getReposCallback = {},
    )
}

@Composable
fun Screen(
    items: List<Item>,
    selectedUser: String,
    isErrorOccurs: Boolean,
    errMsg: String,
    isLoading: Boolean,
    getUsersCallback: () -> Unit,
    getReposCallback: (user: User) -> Unit,
) {
    val isClickEnable = !(isLoading || isErrorOccurs)
    var selectedId by savedInstanceState(items) { items }
    selectedId = items
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            asset = imageResource(id = R.drawable.octocat),
            modifier = Modifier.size(128.dp)
        )
        Text(
            text = selectedUser,
            modifier = Modifier.padding(5.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Btn(
            label = "GET USERS",
            click = getUsersCallback,
            modifier = Modifier,
            enabled = isClickEnable
        )
        if (isErrorOccurs) Text(
            text = errMsg,
            color = Color.Red,
            style = typography.h4,
            modifier = Modifier.padding(5.dp)
        )
        Spacer(modifier = Modifier.preferredHeight(5.dp).fillMaxWidth())
        Stack(
            modifier = Modifier.weight(1f).fillMaxSize()
        ) {
            LazyColumnForIndexed(
                items = items,
                modifier = Modifier.fillMaxSize()
            ) { index, item ->
                when (item) {
                    is User -> {
                        UserItem(
                            index = index,
                            user = item,
                            userClick = getReposCallback,
                            isClickEnable = isClickEnable
                        )
                    }
                    is Repo -> {
                        Text(text = item.name)
                    }
                }
            }
            if (isLoading) ProgressBar()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowBtn() {
    Btn(label = "BUTTON", click = {})
}

@Composable
fun Btn(
    label: String,
    click: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = click,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.then(modifier),
        enabled = enabled
    ) {
        Text(label, color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun ShowItem() {
    UserItem(
        index = 10,
        user = User("Login", "http://image.url"),
        userClick = {},
        isClickEnable = true
    )
}


@Composable
fun UserItem(
    index: Int,
    user: User,
    isClickEnable: Boolean,
    userClick: (User) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .preferredHeight(60.dp)
            .fillMaxWidth()
            .then(
                if (isClickEnable) Modifier.clickable { userClick(user) }
                else Modifier
            ),
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                gravity = Alignment.Center,
                modifier = Modifier.aspectRatio(1f).fillMaxSize(),
                backgroundColor = Color.Gray
            ) {
                CoilImage(
                    data = user.avatarUrl,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            gravity = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                asset = Icons.Default.AccountBox,
                                tint = Color.LightGray
                            )
                            CircularProgressIndicator()
                        }
                    }
                )
            }
            Box(
                gravity = Alignment.Center,
                modifier = Modifier.weight(1f).fillMaxSize(),
            ) {
                Text(
                    text = user.login,
                    style = typography.h6,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                gravity = Alignment.Center,
                modifier = Modifier.aspectRatio(0.7f).fillMaxSize(),
                backgroundColor = Color.Gray
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = index.toString(),
                )
            }
        }
    }
}
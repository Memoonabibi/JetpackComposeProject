package com.example.jetpackcomposeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeproject.ui.theme.JetpackComposeProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeProjectTheme {
                Scaffold { innerPadding ->
                    UserList (
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun UserList(modifier: Modifier) {
    var name by remember { mutableStateOf(value ="") }
    var empty by remember { mutableStateOf(value = false) }
    val nameList = remember { mutableStateListOf<String>() }
    Box (
        modifier = Modifier
            .background(
                color = Color (0xffeaf4f4)
            )
            .padding(16.dp, top = 34.dp,16.dp)
            .fillMaxSize()
    )
       {

        Column {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField (
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    placeholder = {
                        Text(text = "Enter name here!")
                    },
                    isError = empty
                )
                Button (onClick = {

                    if (name.isNotBlank()) {
                        empty = false
                        nameList.add(name)
                        name = ""
                    } else {
                        empty = true
                    }

                }
                ) {
                    Text(text = "Add")
                }
            }
            Spacer (modifier = Modifier.height(24.dp))
            LazyColumn {
                items (count = nameList.size) {
                        index -> NameItem (index = index, name = nameList[index], modifier = modifier)

                }
            }
        }


    }
}

@Preview
@Composable
fun UserListPreview() {
    JetpackComposeProjectTheme {
        Scaffold { innerPadding ->
            UserList(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Composable
fun NameItem (index: Int, modifier: Modifier, name: String) {
    Text(
        text = name,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        modifier = Modifier
            .background (
                color = if ( index % 2 == 0) Color (color = 0xffff7b00) else Color(0xffe9ecef)
            )
            .fillMaxWidth()
            .padding(
                bottom = 12.dp,
                top = 12.dp,
                start = 6.dp
            )
    )
    HorizontalDivider()

}

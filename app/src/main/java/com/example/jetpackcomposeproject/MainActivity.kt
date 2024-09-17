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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeproject.ui.theme.JetpackComposeProjectTheme
import com.example.jetpackcomposeproject.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeProjectTheme {
                Scaffold { innerPadding ->
                    UserList(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Contact(val name: String, val phoneNumber: String)

@Composable
fun UserList(modifier: Modifier) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var empty by remember { mutableStateOf(false) }
    var isDuplicate by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    val contactList = remember { mutableStateListOf<Contact>() }

    Box(
        modifier = Modifier
            .background(color = Color(0xffeaf4f4))
            .padding(16.dp, top = 44.dp, 16.dp)
            .fillMaxSize()
    ) {
        Column {

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        isDuplicate = false
                        empty = false
                    },
                    placeholder = { Text(text = "Enter name") },
                    isError = empty || isDuplicate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        phoneError = false

                                    },
                    placeholder = { Text(text = "Enter phone number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (isDuplicate) {
                    Text(
                        text = "Contact already exists!",
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(25.dp))
                }
                Button(
                    onClick = {
                        when {
                            name.isBlank() || phoneNumber.isBlank() -> {
                                empty = true
                                isDuplicate = false
                            }
                            contactList.any { it.name == name && it.phoneNumber == phoneNumber } -> {
                                isDuplicate = true
                                empty = false
                            }
                            else -> {
                                contactList.add(Contact(name, phoneNumber))
                                name = ""
                                phoneNumber = ""
                                empty = false
                                isDuplicate = false
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff7f5539),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "Add", fontSize = 16.sp)
                }
            }


            Text(
                text = "Total contacts: ${contactList.size}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(count = contactList.size) { index ->
                    ContactItem(
                        contact = contactList[index],
                        index = index,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, index: Int, modifier: Modifier) {
    Box(
        modifier = Modifier
            .background(
                color = if (index % 2 == 0) Color(0xffd4a373) else Color(0xfffaedcd)
            )
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(48.dp)
            )

            Column {
                Text(
                    text = "${contact.name}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(start = 6.dp)
                )
                Text(
                    text = "${contact.phoneNumber}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
}

@Preview
@Composable
fun UserListPreview() {
    JetpackComposeProjectTheme {
        Scaffold { innerPadding ->
            UserList(modifier = Modifier.padding(innerPadding))
        }
    }
}

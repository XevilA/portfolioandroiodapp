package com.example.portfolioapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortfolioAppTheme {
                PortfolioApp()
            }
        }
    }
}

@Composable
fun PortfolioAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        ),
        content = content
    )
}

@Composable
fun PortfolioApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "home") {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("about") { AboutScreen() }
            composable("skills") { SkillsScreen() }
            composable("experience") { ExperienceScreen() }
            composable("contact") { ContactScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("about", "About", Icons.Default.Person),
        BottomNavItem("skills", "Skills", Icons.Default.Build),
        BottomNavItem("experience", "Experience", Icons.Default.Work),
        BottomNavItem("contact", "Contact", Icons.Default.Email)
    )

    NavigationBar(
        containerColor = Color(0xFF1E1E1E),
        contentColor = Color.White
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title, fontSize = 10.sp) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF03DAC6),
                    selectedTextColor = Color(0xFF03DAC6),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E1E), Color(0xFF121212))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .shadow(8.dp, CircleShape)
            ) {
                // ใช้ placeholder image เนื่องจากไม่มีรูปจริง
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF6200EE)
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Name
            Text(
                text = "Natthaphon Yapyang",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "นัทธพร ยับยั้ง",
                fontSize = 20.sp,
                color = Color(0xFF03DAC6)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Computer Technician",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Navigation Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NavigationButton("About Me", Icons.Default.Person) {
                    navController.navigate("about")
                }
                NavigationButton("Skills", Icons.Default.Build) {
                    navController.navigate("skills")
                }
                NavigationButton("Experience", Icons.Default.Work) {
                    navController.navigate("experience")
                }
                NavigationButton("Contact", Icons.Default.Email) {
                    navController.navigate("contact")
                }
            }
        }
    }
}

@Composable
fun NavigationButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E2E2E))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = text,
                tint = Color(0xFF03DAC6),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Go",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun AboutScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "About Me",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            InfoCard(
                title = "ข้อมูลส่วนตัว",
                content = listOf(
                    "ชื่อ - สกุล" to "นัทธพร ยับยั้ง",
                    "ชื่อเล่น" to "แนว",
                    "วันเกิด" to "20 ธันวาคม 2548",
                    "อายุ" to "19 ปี",
                    "สัญชาติ" to "ไทย",
                    "เชื้อชาติ" to "ไทย"
                )
            )
        }

        item {
            InfoCard(
                title = "การศึกษา",
                content = listOf(
                    "ประถมศึกษา" to "โรงเรียนอนุบาลระยองวัดหนองสนม",
                    "มัธยมต้น" to "โรงเรียนนคระยองวิทยารม (วัดโขดใต้)",
                    "ปวช." to "วิทยาลัยเทคนิคระยอง",
                    "ปวส." to "วิทยาลัยเทคนิคระยอง"
                )
            )
        }
    }
}

@Composable
fun SkillsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Skills",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            SkillCategory(
                title = "Computer Skills",
                skills = listOf(
                    "กราฟฟิกดีไซน์",
                    "ตัดต่อวิดีโอ",
                    "Microsoft Word",
                    "Microsoft Excel",
                    "Microsoft PowerPoint",
                    "Canva"
                )
            )
        }

        item {
            SkillCategory(
                title = "Technical Skills",
                skills = listOf(
                    "ซ่อมคอมพิวเตอร์",
                    "อัพเกรดฮาร์ดแวร์",
                    "ติดตั้งโปรแกรม",
                    "ติดตั้ง Windows",
                    "บำรุงรักษาโน๊ตบุ๊ค"
                )
            )
        }
    }
}

@Composable
fun SkillCategory(title: String, skills: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF03DAC6),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            skills.forEach { skill ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF03DAC6),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = skill,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ExperienceScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Experience",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            ExperienceCard(
                year = "2568",
                company = "BST Eneos Elastomer Co., Ltd.",
                description = "ฝึกงานในตำแหน่งช่างเทคนิค"
            )
        }

        item {
            ExperienceCard(
                year = "2566",
                company = "ร้านสตาร์ไอที",
                description = "ร้านซ่อมและจำหน่ายคอมพิวเตอร์โน๊ตบุ๊ค\nฝึกงานด้านการซ่อมบำรุงและการให้บริการลูกค้า"
            )
        }
    }
}

@Composable
fun ExperienceCard(year: String, company: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            // Year Badge
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF6200EE))
            ) {
                Text(
                    text = year,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = company,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ContactScreen() {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Contact",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            ContactItem(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = "080-970-5845",
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:0809705845")
                    }
                    context.startActivity(intent)
                }
            )
        }

        item {
            ContactItem(
                icon = Icons.Default.Email,
                label = "Email",
                value = "nai9876543210@gmail.com",
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:nai9876543210@gmail.com")
                    }
                    context.startActivity(intent)
                }
            )
        }

        item {
            ContactItem(
                icon = Icons.Default.LocationOn,
                label = "Address",
                value = "99/201 ต.ระยอง อ.เมือง จ.ระยอง",
                onClick = {
                    val gmmIntentUri = Uri.parse("geo:0,0?q=ระยอง")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    context.startActivity(mapIntent)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Let's Connect!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF03DAC6)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Feel free to reach out for collaborations or just a friendly hello",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ContactItem(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = Color(0xFF03DAC6),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = value,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun InfoCard(title: String, content: List<Pair<String, String>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF03DAC6),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            content.forEach { (label, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "$label:",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.width(100.dp)
                    )
                    Text(
                        text = value,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

package ru.rsue.computerclub

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import ru.rsue.computerclub.api.RetrofitClient
import ru.rsue.computerclub.models.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.init(this)
        setContent {
            MaterialTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    val context = LocalContext.current
    var currentScreen by rememberSaveable { mutableStateOf("main") }
    var showSettings by rememberSaveable { mutableStateOf(false) }
    var showHelp by rememberSaveable { mutableStateOf(false) }

    when {
        showSettings -> {
            SettingsScreen(
                currentIp = RetrofitClient.getCurrentIp(context),
                onSave = { newIp ->
                    RetrofitClient.setServerIp(context, newIp)
                    showSettings = false
                    Toast.makeText(context, "IP сохранён, перезапустите приложение", Toast.LENGTH_LONG).show()
                },
                onBack = { showSettings = false }
            )
        }
        showHelp -> {
            HelpScreen(
                onBack = { showHelp = false }
            )
        }
        currentScreen == "visitors" -> {
            VisitorListScreen(
                onBack = { currentScreen = "main" },
                onSettingsClick = { showSettings = true }
            )
        }
        currentScreen == "computers" -> {
            ComputerScreen(
                onBack = { currentScreen = "main" },
                onSettingsClick = { showSettings = true }
            )
        }
        currentScreen == "visits" -> {
            VisitScreen(
                onBack = { currentScreen = "main" },
                onSettingsClick = { showSettings = true }
            )
        }
        currentScreen == "statuses" -> {
            ComputerStatusScreen(
                onBack = { currentScreen = "main" },
                onSettingsClick = { showSettings = true }
            )
        }
        else -> {
            MainMenuScreen(
                onVisitorsClick = { currentScreen = "visitors" },
                onComputersClick = { currentScreen = "computers" },
                onVisitsClick = { currentScreen = "visits" },
                onStatusesClick = { currentScreen = "statuses" },
                onSettingsClick = { showSettings = true },
                onHelpClick = { showHelp = true },
                onExitClick = {
                    (context as? ComponentActivity)?.finishAffinity()
                }
            )
        }
    }
}

// ==================== MAIN MENU ====================

@Composable
fun MainMenuScreen(
    onVisitorsClick: () -> Unit,
    onComputersClick: () -> Unit,
    onVisitsClick: () -> Unit,
    onStatusesClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onHelpClick: () -> Unit,
    onExitClick: () -> Unit
) {
    val context = LocalContext.current
    var showExitDialog by rememberSaveable { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Диалог подтверждения выхода
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Подтверждение выхода") },
            text = { Text("Вы уверены, что хотите выйти из приложения?") },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        onExitClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Выйти")
                }
            },
            dismissButton = {
                Button(onClick = { showExitDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    if (isLandscape) {
        // Горизонтальная ориентация - два столбца
        Row(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Левая колонка - кнопки страниц
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Страницы", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = onVisitorsClick, modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text("Посетители")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onComputersClick, modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text("Компьютеры")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onVisitsClick, modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text("Посещения")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onStatusesClick, modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text("Статусы")
                }
            }

            // Правая колонка - кнопки управления
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Управление", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(24.dp))

                TextButton(
                    onClick = onSettingsClick,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Настройки сервера", style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    onClick = onHelpClick,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Помощь", style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    onClick = { showExitDialog = true },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Выход", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    } else {
        // Вертикальная ориентация - оригинальное расположение
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Компьютерный клуб", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onVisitorsClick, modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Посетители")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onComputersClick, modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Компьютеры")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onVisitsClick, modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Посещения")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onStatusesClick, modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Статусы")
            }
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onSettingsClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Настройки сервера")
                }
                TextButton(
                    onClick = onHelpClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Помощь")
                }
                TextButton(
                    onClick = { showExitDialog = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Выход")
                }
            }
        }
    }
}

// ==================== HELP SCREEN ====================

@Composable
fun HelpScreen(onBack: () -> Unit) {
    val sections = listOf(
        "Инструкция по проекту" to """
            На главной странице находятся кнопки:
            • Посетители - просмотр и управление данными о посетителях
            • Компьютеры - просмотр и управление данными о компьютерах
            • Посещения - просмотр и управление данными о посещениях
            • Статусы - просмотр и управление статусами компьютеров
            
            Внизу экрана расположены кнопки:
            • Настройки сервера - для изменения IP-адреса сервера
            • Помощь - инструкция по работе с приложением
            • Выход - закрывает приложение
        """.trimIndent(),

        "Инструкция по настройке сервера" to """
            На экране настроек представлено поле ввода IP-адреса сервера.
            
            По умолчанию стоит IP-адрес 10.0.2.2, который позволяет подключаться к серверу Tomcat через эмулятор Android Studio.
            
            Чтобы приложение работало на физическом телефоне, необходимо:
            1. Узнать IP-адрес компьютера, к которому подключен телефон (в командной строке введите ipconfig)
            2. Ввести этот IP-адрес в поле ввода
            3. Нажать кнопку "Сохранить"
            4. Перезапустить приложение
            
            Важно! IP-адрес должен быть в формате xxx.xxx.xxx.xxx, где каждое число от 0 до 255.
        """.trimIndent(),

        "Инструкция по странице Посетители" to """
            Страница отображает список всех посетителей компьютерного клуба.
            
            Для каждого посетителя отображается:
            • Фамилия и имя
            • Номер телефона
            • Адрес (при наличии)
            
            Доступные действия:
            • Нажмите на значок "✏️" для редактирования - откроется окно, где можно изменить ФИО, телефон, адрес и паспортные данные
            • Нажмите на значок "🗑️" для удаления - потребуется подтверждение
            • Нажмите на значок "+" в верхней панели для добавления нового посетителя
            • Нажмите на значок "⚙️" для перехода к настройкам сервера
            
            При редактировании и добавлении все поля можно прокручивать, даже когда открыта клавиатура.
            При повороте экрана введенные данные сохраняются.
        """.trimIndent(),

        "Инструкция по странице Компьютеры" to """
            Страница отображает список всех компьютеров компьютерного клуба.
            
            Для каждого компьютера отображается:
            • Название компьютера
            • Описание (при наличии)
            • Текущий статус (например, "Свободен", "Занят", "В ремонте")
            
            Доступные действия:
            • Нажмите на значок "✏️" для редактирования - откроется окно, где можно изменить название, описание и выбрать статус из списка
            • Нажмите на значок "🗑️" для удаления - потребуется подтверждение
            • Нажмите на значок "+" в верхней панели для добавления нового компьютера
            • Нажмите на значок "⚙️" для перехода к настройкам сервера
            
            Статус компьютера выбирается из отдельного экрана со списком всех доступных статусов. 
            В экране выбора есть поле поиска для быстрого нахождения нужного статуса.
        """.trimIndent(),

        "Инструкция по странице Посещения" to """
            Страница отображает историю всех посещений компьютерного клуба.
            
            Для каждого посещения отображается:
            • Посетитель (ФИО)
            • Компьютер (название)
            • Дата посещения
            • Длительность в минутах
            • Сумма оплаты в рублях
            
            Доступные действия:
            • Нажмите на значок "✏️" для редактирования - откроется окно с возможностью изменить посетителя, компьютер, дату, длительность и сумму
            • Нажмите на значок "🗑️" для удаления - потребуется подтверждение
            • Нажмите на значок "+" в верхней панели для добавления нового посещения
            • Нажмите на значок "⚙️" для перехода к настройкам сервера
            
            Особенности ввода:
            • Посетитель и компьютер выбираются из отдельных экранов с поиском
            • Дата выбирается через удобный календарь
            • Длительность принимает только цифры
            • Сумма принимает только цифры и точку (для копеек)
            • При открытии клавиатуры все поля можно прокручивать, кнопки остаются внизу
        """.trimIndent(),

        "Инструкция по странице Статусы" to """
            Страница отображает список возможных статусов компьютеров.
            
            Примеры статусов:
            • Свободен
            • Занят
            • В ремонте
            • Техническое обслуживание
            
            Доступные действия:
            • Нажмите на значок "✏️" для редактирования - можно изменить название статуса
            • Нажмите на значок "🗑️" для удаления - потребуется подтверждение
            • Нажмите на значок "+" в верхней панели для добавления нового статуса
            • Нажмите на значок "⚙️" для перехода к настройкам сервера
            
            Внимание! Статусы, которые используются в компьютерах, удалять не рекомендуется, 
            так как это может нарушить работу приложения.
        """.trimIndent()
    )

    var selectedSection by rememberSaveable { mutableStateOf(sections[0].first) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text("Помощь", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Разделы инструкции:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(0.4f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(sections) { (title, _) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedSection = title },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedSection == title)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Содержание:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = sections.find { it.first == selectedSection }?.second ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

// ==================== SETTINGS SCREEN ====================

@Composable
fun SettingsScreen(currentIp: String, onSave: (String) -> Unit, onBack: () -> Unit) {
    var ip by rememberSaveable { mutableStateOf(currentIp) }
    var ipError by rememberSaveable { mutableStateOf<String?>(null) }

    fun isValidIp(ip: String): Boolean {
        val parts = ip.split(".")
        if (parts.size != 4) return false
        return parts.all { part ->
            part.isNotEmpty() && part.all { it.isDigit() } && part.toIntOrNull() in 0..255
        }
    }

    fun onIpChange(newIp: String) {
        val filtered = newIp.filter { it.isDigit() || it == '.' }
        val dotCount = filtered.count { it == '.' }
        if (dotCount <= 3) {
            ip = filtered
            if (filtered.isNotEmpty() && filtered != "." && !filtered.endsWith(".")) {
                if (isValidIp(filtered)) {
                    ipError = null
                } else {
                    ipError = "Неверный формат IP-адреса"
                }
            } else if (filtered.isNotEmpty() && filtered != ".") {
                ipError = "Неверный формат IP-адреса"
            } else {
                ipError = null
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Настройки сервера", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = ip,
            onValueChange = { onIpChange(it) },
            label = { Text("IP адрес сервера") },
            placeholder = { Text("192.168.1.100") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = ipError != null,
            supportingText = {
                if (ipError != null) {
                    Text(ipError ?: "")
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("Порт: 8081", style = MaterialTheme.typography.bodySmall)
        Text("Пример: 10.0.2.2 (для эмулятора)", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onBack) {
                Text("Отмена")
            }
            Button(
                onClick = { onSave(ip) },
                enabled = isValidIp(ip)
            ) {
                Text("Сохранить")
            }
        }
    }
}

// ==================== VISITOR ====================

@Composable
fun VisitorListScreen(onBack: () -> Unit, onSettingsClick: () -> Unit) {
    val context = LocalContext.current
    var visitors by remember { mutableStateOf<List<Visitor>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var isEditMode by rememberSaveable { mutableStateOf(false) }
    var editingVisitorId by rememberSaveable { mutableStateOf<Long?>(null) }
    var deletingVisitorId by rememberSaveable { mutableStateOf<Long?>(null) }

    var formFirstName by rememberSaveable { mutableStateOf("") }
    var formLastName by rememberSaveable { mutableStateOf("") }
    var formPatronymic by rememberSaveable { mutableStateOf("") }
    var formPhone by rememberSaveable { mutableStateOf("") }
    var formAddress by rememberSaveable { mutableStateOf("") }
    var formIdentityDocument by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    fun loadData() {
        coroutineScope.launch {
            isLoading = true
            try {
                visitors = RetrofitClient.api.getVisitors()
                error = null
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) { loadData() }

    val editingVisitor = visitors.find { it.id == editingVisitorId }
    val deletingVisitor = visitors.find { it.id == deletingVisitorId }

    LaunchedEffect(editingVisitorId, showDialog) {
        if (showDialog && isEditMode && editingVisitorId != null && editingVisitor != null) {
            formFirstName = editingVisitor.firstName
            formLastName = editingVisitor.lastName
            formPatronymic = editingVisitor.patronymic ?: ""
            formPhone = editingVisitor.phone
            formAddress = editingVisitor.address ?: ""
            formIdentityDocument = editingVisitor.identityDocument ?: ""
        }
    }

    fun openAddDialog() {
        isEditMode = false
        editingVisitorId = null
        formFirstName = ""
        formLastName = ""
        formPatronymic = ""
        formPhone = ""
        formAddress = ""
        formIdentityDocument = ""
        showDialog = true
    }

    fun openEditDialog(visitorId: Long) {
        isEditMode = true
        editingVisitorId = visitorId
        showDialog = true
    }

    fun closeDialog() {
        showDialog = false
        isEditMode = false
        editingVisitorId = null
        formFirstName = ""
        formLastName = ""
        formPatronymic = ""
        formPhone = ""
        formAddress = ""
        formIdentityDocument = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text("Посетители", style = MaterialTheme.typography.headlineLarge)
            Row {
                IconButton(onClick = { openAddDialog() }) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить")
                }
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Настройки")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Ошибка: $error")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { loadData() }) {
                        Text("Повторить")
                    }
                }
            }
            else -> {
                LazyColumn {
                    items(visitors) { visitor ->
                        VisitorCard(
                            visitor = visitor,
                            onEdit = { openEditDialog(visitor.id) },
                            onDelete = {
                                deletingVisitorId = visitor.id
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        VisitorDialog(
            isEdit = isEditMode,
            firstName = formFirstName,
            lastName = formLastName,
            patronymic = formPatronymic,
            phone = formPhone,
            address = formAddress,
            identityDocument = formIdentityDocument,
            onFirstNameChange = { formFirstName = it },
            onLastNameChange = { formLastName = it },
            onPatronymicChange = { formPatronymic = it },
            onPhoneChange = { formPhone = it },
            onAddressChange = { formAddress = it },
            onIdentityDocumentChange = { formIdentityDocument = it },
            onDismiss = { closeDialog() },
            onSave = { visitor ->
                coroutineScope.launch {
                    try {
                        if (isEditMode) {
                            RetrofitClient.api.updateVisitor(editingVisitorId!!, visitor.copy(id = editingVisitorId!!))
                            Toast.makeText(context, "Изменения сохранены", Toast.LENGTH_SHORT).show()
                        } else {
                            RetrofitClient.api.addVisitor(visitor)
                            Toast.makeText(context, "Посетитель добавлен", Toast.LENGTH_SHORT).show()
                        }
                        loadData()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    closeDialog()
                }
            }
        )
    }

    if (showDeleteDialog && deletingVisitor != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                deletingVisitorId = null
            },
            title = { Text("Подтверждение удаления") },
            text = { Text("Вы уверены, что хотите удалить посетителя ${deletingVisitor.lastName} ${deletingVisitor.firstName}?") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                RetrofitClient.api.deleteVisitor(deletingVisitor.id)
                                loadData()
                                Toast.makeText(context, "Посетитель удален", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                            showDeleteDialog = false
                            deletingVisitorId = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDeleteDialog = false
                    deletingVisitorId = null
                }) {
                    Text("Отмена")
                }
            }
        )
    }
}

@Composable
fun VisitorCard(visitor: Visitor, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${visitor.lastName} ${visitor.firstName}", style = MaterialTheme.typography.titleMedium)
                Text(visitor.phone, style = MaterialTheme.typography.bodyMedium)
                visitor.address?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Удалить")
                }
            }
        }
    }
}

@Composable
fun VisitorDialog(
    isEdit: Boolean,
    firstName: String,
    lastName: String,
    patronymic: String,
    phone: String,
    address: String,
    identityDocument: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPatronymicChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onIdentityDocumentChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (Visitor) -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = if (isEdit) "Редактировать посетителя" else "Добавить посетителя",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = onLastNameChange,
                        label = { Text("Фамилия") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = onFirstNameChange,
                        label = { Text("Имя") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = patronymic,
                        onValueChange = onPatronymicChange,
                        label = { Text("Отчество") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = onPhoneChange,
                        label = { Text("Телефон") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = address,
                        onValueChange = onAddressChange,
                        label = { Text("Адрес") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = identityDocument,
                        onValueChange = onIdentityDocumentChange,
                        label = { Text("Документ") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.weight(0.4f),
                            colors = ButtonDefaults.outlinedButtonColors()
                        ) {
                            Text("Отмена")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                if (lastName.isNotBlank() && firstName.isNotBlank()) {
                                    onSave(
                                        Visitor(
                                            id = 0L,
                                            firstName = firstName,
                                            lastName = lastName,
                                            patronymic = patronymic.ifEmpty { null },
                                            phone = phone,
                                            address = address.ifEmpty { null },
                                            identityDocument = identityDocument.ifEmpty { null }
                                        )
                                    )
                                } else {
                                    Toast.makeText(context, "Заполните фамилию и имя", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.weight(0.4f),
                            enabled = lastName.isNotBlank() && firstName.isNotBlank()
                        ) {
                            Text("Сохранить")
                        }
                    }
                }
            }
        }
    }
}

// ==================== COMPUTER ====================

@Composable
fun ComputerScreen(onBack: () -> Unit, onSettingsClick: () -> Unit) {
    val context = LocalContext.current
    var computers by remember { mutableStateOf<List<Computer>>(emptyList()) }
    var statuses by remember { mutableStateOf<List<ComputerStatus>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var showStatusPicker by rememberSaveable { mutableStateOf(false) }
    var isEditMode by rememberSaveable { mutableStateOf(false) }
    var editingComputerId by rememberSaveable { mutableStateOf<Long?>(null) }
    var deletingComputerId by rememberSaveable { mutableStateOf<Long?>(null) }

    var formComputerName by rememberSaveable { mutableStateOf("") }
    var formDescription by rememberSaveable { mutableStateOf("") }
    var formSelectedStatusId by rememberSaveable { mutableStateOf(0L) }

    val coroutineScope = rememberCoroutineScope()

    fun loadData() {
        coroutineScope.launch {
            isLoading = true
            try {
                computers = RetrofitClient.api.getComputers()
                statuses = RetrofitClient.api.getComputerStatuses()
                error = null
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) { loadData() }

    val editingComputer = computers.find { it.id == editingComputerId }
    val deletingComputer = computers.find { it.id == deletingComputerId }

    LaunchedEffect(editingComputerId, showDialog) {
        if (showDialog && isEditMode && editingComputerId != null && editingComputer != null) {
            formComputerName = editingComputer.computerName
            formDescription = editingComputer.description ?: ""
            formSelectedStatusId = editingComputer.statusId
        }
    }

    fun openAddDialog() {
        isEditMode = false
        editingComputerId = null
        formComputerName = ""
        formDescription = ""
        formSelectedStatusId = statuses.firstOrNull()?.id ?: 0L
        showDialog = true
    }

    fun openEditDialog(computerId: Long) {
        isEditMode = true
        editingComputerId = computerId
        showDialog = true
    }

    fun closeDialog() {
        showDialog = false
        isEditMode = false
        editingComputerId = null
        formComputerName = ""
        formDescription = ""
        formSelectedStatusId = statuses.firstOrNull()?.id ?: 0L
    }

    if (showStatusPicker) {
        StatusPickerScreen(
            statuses = statuses,
            onSelect = { status ->
                formSelectedStatusId = status.id
                showStatusPicker = false
            },
            onBack = { showStatusPicker = false }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                }
                Text("Компьютеры", style = MaterialTheme.typography.headlineLarge)
                Row {
                    IconButton(onClick = { openAddDialog() }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить")
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Настройки")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Ошибка: $error")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { loadData() }) {
                            Text("Повторить")
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        items(computers) { computer ->
                            val status = statuses.find { it.id == computer.statusId }
                            ComputerCard(
                                computer = computer,
                                statusName = status?.statusName ?: "—",
                                onEdit = { openEditDialog(computer.id) },
                                onDelete = {
                                    deletingComputerId = computer.id
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }

        if (showDialog) {
            ComputerDialog(
                isEdit = isEditMode,
                computerName = formComputerName,
                description = formDescription,
                selectedStatusId = formSelectedStatusId,
                selectedStatusName = statuses.find { it.id == formSelectedStatusId }?.statusName ?: "",
                onComputerNameChange = { formComputerName = it },
                onDescriptionChange = { formDescription = it },
                onStatusClick = { showStatusPicker = true },
                onDismiss = { closeDialog() },
                onSave = { computer ->
                    coroutineScope.launch {
                        try {
                            if (isEditMode) {
                                RetrofitClient.api.updateComputer(editingComputerId!!, computer.copy(id = editingComputerId!!))
                                Toast.makeText(context, "Изменения сохранены", Toast.LENGTH_SHORT).show()
                            } else {
                                RetrofitClient.api.addComputer(computer)
                                Toast.makeText(context, "Компьютер добавлен", Toast.LENGTH_SHORT).show()
                            }
                            loadData()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                        closeDialog()
                    }
                }
            )
        }

        if (showDeleteDialog && deletingComputer != null) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                    deletingComputerId = null
                },
                title = { Text("Подтверждение удаления") },
                text = { Text("Вы уверены, что хотите удалить компьютер ${deletingComputer.computerName}?") },
                confirmButton = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                try {
                                    RetrofitClient.api.deleteComputer(deletingComputer.id)
                                    loadData()
                                    Toast.makeText(context, "Компьютер удален", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                                showDeleteDialog = false
                                deletingComputerId = null
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Удалить")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showDeleteDialog = false
                        deletingComputerId = null
                    }) {
                        Text("Отмена")
                    }
                }
            )
        }
    }
}

@Composable
fun ComputerCard(computer: Computer, statusName: String, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(computer.computerName, style = MaterialTheme.typography.titleMedium)
                computer.description?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
                Text("Статус: $statusName", style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Удалить")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComputerDialog(
    isEdit: Boolean,
    computerName: String,
    description: String,
    selectedStatusId: Long,
    selectedStatusName: String,
    onComputerNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onStatusClick: () -> Unit,
    onDismiss: () -> Unit,
    onSave: (Computer) -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = if (isEdit) "Редактировать компьютер" else "Добавить компьютер",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = computerName,
                        onValueChange = onComputerNameChange,
                        label = { Text("Название компьютера") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = onDescriptionChange,
                        label = { Text("Описание") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Статус", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        value = selectedStatusName,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Выберите статус") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            TextButton(onClick = onStatusClick) {
                                Text("Выбрать")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.weight(0.4f),
                            colors = ButtonDefaults.outlinedButtonColors()
                        ) {
                            Text("Отмена")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                if (computerName.isNotBlank() && selectedStatusId != 0L) {
                                    onSave(Computer(
                                        id = 0L,
                                        computerName = computerName,
                                        description = description.ifEmpty { null },
                                        statusId = selectedStatusId,
                                        statusName = selectedStatusName
                                    ))
                                } else {
                                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.weight(0.4f),
                            enabled = computerName.isNotBlank() && selectedStatusId != 0L
                        ) {
                            Text("Сохранить")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusPickerScreen(
    statuses: List<ComputerStatus>,
    onSelect: (ComputerStatus) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val filteredStatuses = if (searchQuery.isEmpty()) {
        statuses
    } else {
        statuses.filter {
            it.statusName.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text("Выбор статуса", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск по названию статуса") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredStatuses) { status ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(status) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            status.statusName,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

// ==================== VISIT ====================

@Composable
fun VisitScreen(onBack: () -> Unit, onSettingsClick: () -> Unit) {
    val context = LocalContext.current
    var visits by remember { mutableStateOf<List<Visit>>(emptyList()) }
    var visitors by remember { mutableStateOf<List<Visitor>>(emptyList()) }
    var computers by remember { mutableStateOf<List<Computer>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var showVisitorPicker by rememberSaveable { mutableStateOf(false) }
    var showComputerPicker by rememberSaveable { mutableStateOf(false) }
    var isEditMode by rememberSaveable { mutableStateOf(false) }
    var editingVisitId by rememberSaveable { mutableStateOf<Long?>(null) }
    var deletingVisitId by rememberSaveable { mutableStateOf<Long?>(null) }

    var formSelectedVisitorId by rememberSaveable { mutableStateOf(0L) }
    var formSelectedComputerId by rememberSaveable { mutableStateOf(0L) }
    var formVisitDateMillis by rememberSaveable { mutableStateOf(System.currentTimeMillis()) }
    var formDuration by rememberSaveable { mutableStateOf("") }
    var formPayment by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    fun loadData() {
        coroutineScope.launch {
            isLoading = true
            try {
                visits = RetrofitClient.api.getVisits()
                visitors = RetrofitClient.api.getVisitors()
                computers = RetrofitClient.api.getComputers()
                error = null
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) { loadData() }

    val editingVisit = visits.find { it.id == editingVisitId }
    val deletingVisit = visits.find { it.id == deletingVisitId }

    LaunchedEffect(editingVisitId, showDialog) {
        if (showDialog && isEditMode && editingVisitId != null && editingVisit != null) {
            formSelectedVisitorId = editingVisit.visitorId
            formSelectedComputerId = editingVisit.computerId
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = dateFormat.parse(editingVisit.visitDate)
                formVisitDateMillis = date?.time ?: System.currentTimeMillis()
            } catch (e: Exception) {
                formVisitDateMillis = System.currentTimeMillis()
            }
            formDuration = editingVisit.duration.toString()
            formPayment = editingVisit.payment.toString()
        }
    }

    fun openAddDialog() {
        isEditMode = false
        editingVisitId = null
        formSelectedVisitorId = visitors.firstOrNull()?.id ?: 0L
        formSelectedComputerId = computers.firstOrNull()?.id ?: 0L
        formVisitDateMillis = System.currentTimeMillis()
        formDuration = ""
        formPayment = ""
        showDialog = true
    }

    fun openEditDialog(visitId: Long) {
        isEditMode = true
        editingVisitId = visitId
        showDialog = true
    }

    fun closeDialog() {
        showDialog = false
        isEditMode = false
        editingVisitId = null
    }

    if (showVisitorPicker) {
        VisitorPickerScreen(
            visitors = visitors,
            onSelect = { visitor ->
                formSelectedVisitorId = visitor.id
                showVisitorPicker = false
            },
            onBack = { showVisitorPicker = false }
        )
    } else if (showComputerPicker) {
        ComputerPickerScreen(
            computers = computers,
            onSelect = { computer ->
                formSelectedComputerId = computer.id
                showComputerPicker = false
            },
            onBack = { showComputerPicker = false }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                }
                Text("Посещения", style = MaterialTheme.typography.headlineLarge)
                Row {
                    IconButton(onClick = { openAddDialog() }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить")
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Настройки")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Ошибка: $error")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { loadData() }) {
                            Text("Повторить")
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        items(visits) { visit ->
                            VisitCard(
                                visit = visit,
                                visitors = visitors,
                                computers = computers,
                                onEdit = { openEditDialog(visit.id) },
                                onDelete = {
                                    deletingVisitId = visit.id
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }

        if (showDialog) {
            VisitDialog(
                isEdit = isEditMode,
                selectedVisitorId = formSelectedVisitorId,
                selectedComputerId = formSelectedComputerId,
                visitDateMillis = formVisitDateMillis,
                duration = formDuration,
                payment = formPayment,
                selectedVisitorName = visitors.find { it.id == formSelectedVisitorId }?.let { "${it.lastName} ${it.firstName}" } ?: "",
                selectedComputerName = computers.find { it.id == formSelectedComputerId }?.computerName ?: "",
                onVisitorClick = { showVisitorPicker = true },
                onComputerClick = { showComputerPicker = true },
                onDateChange = { formVisitDateMillis = it },
                onDurationChange = { formDuration = it },
                onPaymentChange = { formPayment = it },
                onDismiss = { closeDialog() },
                onSave = { visit ->
                    coroutineScope.launch {
                        try {
                            if (isEditMode) {
                                RetrofitClient.api.updateVisit(editingVisitId!!, visit.copy(id = editingVisitId!!))
                                Toast.makeText(context, "Изменения сохранены", Toast.LENGTH_SHORT).show()
                            } else {
                                RetrofitClient.api.addVisit(visit)
                                Toast.makeText(context, "Посещение добавлено", Toast.LENGTH_SHORT).show()
                            }
                            loadData()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                        closeDialog()
                    }
                }
            )
        }

        if (showDeleteDialog && deletingVisit != null) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                    deletingVisitId = null
                },
                title = { Text("Подтверждение удаления") },
                text = { Text("Вы уверены, что хотите удалить это посещение?") },
                confirmButton = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                try {
                                    RetrofitClient.api.deleteVisit(deletingVisit.id)
                                    loadData()
                                    Toast.makeText(context, "Посещение удалено", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                                showDeleteDialog = false
                                deletingVisitId = null
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Удалить")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showDeleteDialog = false
                        deletingVisitId = null
                    }) {
                        Text("Отмена")
                    }
                }
            )
        }
    }
}

@Composable
fun VisitCard(visit: Visit, visitors: List<Visitor>, computers: List<Computer>, onEdit: () -> Unit, onDelete: () -> Unit) {
    val visitor = visitors.find { it.id == visit.visitorId }
    val computer = computers.find { it.id == visit.computerId }

    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${visitor?.lastName} ${visitor?.firstName}", style = MaterialTheme.typography.titleMedium)
                Text(computer?.computerName ?: "Компьютер ${visit.computerId}", style = MaterialTheme.typography.bodyMedium)
                Text("Дата: ${visit.visitDate}", style = MaterialTheme.typography.bodySmall)
                Text("Длительность: ${visit.duration} мин", style = MaterialTheme.typography.bodySmall)
                Text("Оплата: ${visit.payment} руб.", style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Удалить")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitDialog(
    isEdit: Boolean,
    selectedVisitorId: Long,
    selectedComputerId: Long,
    visitDateMillis: Long,
    duration: String,
    payment: String,
    selectedVisitorName: String,
    selectedComputerName: String,
    onVisitorClick: () -> Unit,
    onComputerClick: () -> Unit,
    onDateChange: (Long) -> Unit,
    onDurationChange: (String) -> Unit,
    onPaymentChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (Visit) -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var showDatePicker by remember { mutableStateOf(false) }

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(visitDateMillis))

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = if (isEdit) "Редактировать посещение" else "Добавить посещение",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Посетитель", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        value = selectedVisitorName,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Выберите посетителя") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            TextButton(onClick = onVisitorClick) {
                                Text("Выбрать")
                            }
                        }
                    )

                    Text("Компьютер", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        value = selectedComputerName,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Выберите компьютер") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            TextButton(onClick = onComputerClick) {
                                Text("Выбрать")
                            }
                        }
                    )

                    Text("Дата", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        value = formattedDate,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Дата посещения") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Выбрать дату")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = duration,
                        onValueChange = {
                            if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                                onDurationChange(it)
                            }
                        },
                        label = { Text("Длительность (мин)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = payment,
                        onValueChange = {
                            if (it.isEmpty() || it.all { char -> char.isDigit() || char == '.' }) {
                                onPaymentChange(it)
                            }
                        },
                        label = { Text("Сумма (руб.)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.weight(0.4f),
                            colors = ButtonDefaults.outlinedButtonColors()
                        ) {
                            Text("Отмена")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                if (selectedVisitorId != 0L && selectedComputerId != 0L && duration.isNotEmpty() && payment.isNotEmpty()) {
                                    val dateFormatDb = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    val dateStr = dateFormatDb.format(Date(visitDateMillis))

                                    onSave(Visit(
                                        id = 0L,
                                        visitorId = selectedVisitorId,
                                        computerId = selectedComputerId,
                                        visitorFullName = null,
                                        computerName = null,
                                        visitDate = dateStr,
                                        duration = duration.toIntOrNull() ?: 0,
                                        payment = payment.toDoubleOrNull() ?: 0.0
                                    ))
                                } else {
                                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.weight(0.4f),
                            enabled = selectedVisitorId != 0L && selectedComputerId != 0L && duration.isNotEmpty() && payment.isNotEmpty()
                        ) {
                            Text("Сохранить")
                        }
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Отмена")
                }
            }
        ) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = visitDateMillis
            )

            DatePicker(
                state = datePickerState
            )

            LaunchedEffect(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let {
                    onDateChange(it)
                }
            }
        }
    }
}

@Composable
fun VisitorPickerScreen(
    visitors: List<Visitor>,
    onSelect: (Visitor) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val filteredVisitors = if (searchQuery.isEmpty()) {
        visitors
    } else {
        visitors.filter {
            it.lastName.contains(searchQuery, ignoreCase = true) ||
                    it.firstName.contains(searchQuery, ignoreCase = true) ||
                    "${it.lastName} ${it.firstName}".contains(searchQuery, ignoreCase = true) ||
                    it.phone.contains(searchQuery, ignoreCase = true) ||
                    it.identityDocument?.contains(searchQuery, ignoreCase = true) == true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text("Выбор посетителя", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск по фамилии, имени, телефону или документу") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredVisitors) { visitor ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(visitor) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            "${visitor.lastName} ${visitor.firstName}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            visitor.phone,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        visitor.address?.let {
                            Text(
                                it,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        visitor.identityDocument?.let {
                            Text(
                                "Документ: $it",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComputerPickerScreen(
    computers: List<Computer>,
    onSelect: (Computer) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val filteredComputers = if (searchQuery.isEmpty()) {
        computers
    } else {
        computers.filter {
            it.computerName.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text("Выбор компьютера", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск по названию") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredComputers) { computer ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(computer) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            computer.computerName,
                            style = MaterialTheme.typography.titleMedium
                        )
                        computer.description?.let {
                            Text(
                                it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==================== COMPUTER STATUS ====================

@Composable
fun ComputerStatusScreen(onBack: () -> Unit, onSettingsClick: () -> Unit) {
    val context = LocalContext.current
    var statuses by remember { mutableStateOf<List<ComputerStatus>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var isEditMode by rememberSaveable { mutableStateOf(false) }
    var editingStatusId by rememberSaveable { mutableStateOf<Long?>(null) }
    var deletingStatusId by rememberSaveable { mutableStateOf<Long?>(null) }

    var formStatusName by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    fun loadData() {
        coroutineScope.launch {
            isLoading = true
            try {
                statuses = RetrofitClient.api.getComputerStatuses()
                error = null
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) { loadData() }

    val editingStatus = statuses.find { it.id == editingStatusId }
    val deletingStatus = statuses.find { it.id == deletingStatusId }

    LaunchedEffect(editingStatusId, showDialog) {
        if (showDialog && isEditMode && editingStatusId != null && editingStatus != null) {
            formStatusName = editingStatus.statusName
        }
    }

    fun openAddDialog() {
        isEditMode = false
        editingStatusId = null
        formStatusName = ""
        showDialog = true
    }

    fun openEditDialog(statusId: Long) {
        isEditMode = true
        editingStatusId = statusId
        showDialog = true
    }

    fun closeDialog() {
        showDialog = false
        isEditMode = false
        editingStatusId = null
        formStatusName = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text("Статусы", style = MaterialTheme.typography.headlineLarge)
            Row {
                IconButton(onClick = { openAddDialog() }) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить")
                }
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Настройки")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Ошибка: $error")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { loadData() }) {
                        Text("Повторить")
                    }
                }
            }
            else -> {
                LazyColumn {
                    items(statuses) { status ->
                        ComputerStatusCard(
                            status = status,
                            onEdit = { openEditDialog(status.id) },
                            onDelete = {
                                deletingStatusId = status.id
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        ComputerStatusDialog(
            isEdit = isEditMode,
            statusName = formStatusName,
            onStatusNameChange = { formStatusName = it },
            onDismiss = { closeDialog() },
            onSave = { status ->
                coroutineScope.launch {
                    try {
                        if (isEditMode) {
                            RetrofitClient.api.updateComputerStatus(editingStatusId!!, status.copy(id = editingStatusId!!))
                            Toast.makeText(context, "Изменения сохранены", Toast.LENGTH_SHORT).show()
                        } else {
                            RetrofitClient.api.addComputerStatus(status)
                            Toast.makeText(context, "Статус добавлен", Toast.LENGTH_SHORT).show()
                        }
                        loadData()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    closeDialog()
                }
            }
        )
    }

    if (showDeleteDialog && deletingStatus != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                deletingStatusId = null
            },
            title = { Text("Подтверждение удаления") },
            text = { Text("Вы уверены, что хотите удалить статус \"${deletingStatus.statusName}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                RetrofitClient.api.deleteComputerStatus(deletingStatus.id)
                                loadData()
                                Toast.makeText(context, "Статус удален", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                            showDeleteDialog = false
                            deletingStatusId = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDeleteDialog = false
                    deletingStatusId = null
                }) {
                    Text("Отмена")
                }
            }
        )
    }
}

@Composable
fun ComputerStatusCard(status: ComputerStatus, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(status.statusName, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Удалить")
                }
            }
        }
    }
}

@Composable
fun ComputerStatusDialog(
    isEdit: Boolean,
    statusName: String,
    onStatusNameChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (ComputerStatus) -> Unit
) {
    val scrollState = rememberScrollState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = if (isEdit) "Редактировать статус" else "Добавить статус",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .imePadding(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = statusName,
                        onValueChange = onStatusNameChange,
                        label = { Text("Название статуса") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.weight(0.4f),
                            colors = ButtonDefaults.outlinedButtonColors()
                        ) {
                            Text("Отмена")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                if (statusName.isNotBlank()) {
                                    onSave(ComputerStatus(
                                        id = 0L,
                                        statusName = statusName
                                    ))
                                }
                            },
                            modifier = Modifier.weight(0.4f),
                            enabled = statusName.isNotBlank()
                        ) {
                            Text("Сохранить")
                        }
                    }
                }
            }
        }
    }
}

// ==================== HELPER ====================

fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return String.format("%04d-%02d-%02d", year, month, day)
}
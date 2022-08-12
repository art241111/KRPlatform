package navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Navigation {
    private val _screens = MutableStateFlow(listOf(Screens.HOME))

    private val _actualScreen = MutableStateFlow(_screens.value.last())
    val actualScreen: StateFlow<Screens> = _actualScreen

    private val _showPluginName = MutableStateFlow("")
    val showPluginName: StateFlow<String> = _showPluginName

    private val _showAdditionalWindow = MutableStateFlow(listOf<Windows>())
    val showAdditionalWindow: StateFlow<List<Windows>> = _showAdditionalWindow

    fun back() {
        val oldScreen = _screens.value.toMutableList()
        oldScreen.removeLast()
        _screens.value = oldScreen
        _actualScreen.value = oldScreen.last()
    }

    fun openConnectToRobotWindow() {
        val list = _showAdditionalWindow.value.toMutableList()
        list.add(Windows.ROBOT_CONNECT)
        _showAdditionalWindow.value = list
    }

    fun closeConnectToRobotWindow() {
        val list = _showAdditionalWindow.value.toMutableList()
        if (list.contains(Windows.ROBOT_CONNECT)) {
            list.remove(Windows.ROBOT_CONNECT)
        }
        _showAdditionalWindow.value = list
    }

    fun goHome() {
        replaceScreen(Screens.HOME)
    }

    fun showPlugin(name: String) {
        _showPluginName.value = name
        replaceScreen(Screens.SHOW_PLUGIN)
    }

    fun loadPlugin() {
        addScreen(Screens.LOAD_PLUGIN)
    }

    private fun replaceScreen(screens: Screens) {
        _screens.value = listOf(screens)
        _actualScreen.value = screens
    }

    private fun addScreen(screens: Screens) {
        val oldScreen = _screens.value.toMutableList()
        oldScreen.add(screens)
        _actualScreen.value = screens
        _screens.value = oldScreen
    }
}


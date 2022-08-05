package navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Navigation {
    private val _screens = MutableStateFlow(listOf(Screens.HOME))
    val screens: StateFlow<List<Screens>> = _screens

    private val _actualScreen = MutableStateFlow(_screens.value.last())
    val actualScreen: StateFlow<Screens> = _actualScreen

    private val _showPluginName = MutableStateFlow("")
    val showPluginName: StateFlow<String> = _showPluginName

    fun goHome() {
        _screens.value = listOf(Screens.HOME)
        _actualScreen.value = Screens.HOME
    }

    fun showPlugin(name: String) {
        _showPluginName.value = name
        _screens.value = listOf(Screens.SHOW_PLUGIN)
        _actualScreen.value = Screens.SHOW_PLUGIN
    }

    fun loadPlugin() {
        addScreen(Screens.LOAD_PLUGIN)
    }

    fun back() {
        val oldScreen = _screens.value.toMutableList()
        oldScreen.removeLast()
        _screens.value = oldScreen
        _actualScreen.value = oldScreen.last()
    }

    private fun addScreen(screens: Screens) {
        val oldScreen = _screens.value.toMutableList()
        oldScreen.add(screens)
        _actualScreen.value = screens
        _screens.value = oldScreen
    }
}


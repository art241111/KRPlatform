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

    fun back() {
        val oldScreen = _screens.value.toMutableList()
        oldScreen.removeLast()
        _screens.value = oldScreen
        _actualScreen.value = oldScreen.last()
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


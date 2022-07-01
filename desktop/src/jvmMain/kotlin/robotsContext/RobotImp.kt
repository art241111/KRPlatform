package robotsContext

import kotlinx.coroutines.flow.StateFlow
import robot.Robot

class RobotImp: Robot {
    override val dataHandler: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val isConnect: StateFlow<Boolean>
        get() = TODO("Not yet implemented")

    override fun connect() {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }

    override fun send(message: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendWithRequest(message: String): String {
        TODO("Not yet implemented")
    }
}
package robotsContext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import robot.Robot
import robot.RobotsContext

class RobotsContextImp(
    private val coroutineScope: CoroutineScope,
    private val connectToRobotWindow: (onConnect: (robot: Robot) -> Unit) -> Unit,
) : RobotsContext {
    private val _robots = mutableListOf<Robot>()
    val robots: List<Robot> = _robots

    override suspend fun connect(onConnect: (robot: Robot) -> Unit) {
        connectToRobotWindow(onConnect)
    }

    override fun connect(ip: String, port: Int): Robot {
        var robot = isConnected(ip, port)
        return if (robot != null) {
            robot
        } else {
            robot = KawasakiRobotImp(coroutineScope, ip, port)
            robot.connect()
            _robots.add(robot)
            robot
        }
    }

    fun connect(
        ip: String,
        port: Int,
        dataReadStatus: MutableStateFlow<String>?,
        onConnect: (robot: Robot) -> Unit
    ): Robot {
        var robot = isConnected(ip, port)
        return if (robot != null) {
            robot
        } else {
            robot = KawasakiRobotImp(coroutineScope, ip, port)
            robot.connect(
                dataReadStatus,
                onConnect = {
                    onConnect(robot)
                }
            )
            _robots.add(robot)
            robot
        }
    }

    override fun disconnect() {
        disconnect(_robots.last() as KawasakiRobotImp)
    }

    override fun disconnect(ip: String, port: Int, endMessage: String) {
        val _robot = isConnected(ip, port)
        if (_robot != null) {
            disconnect(_robot as KawasakiRobotImp, endMessage)
        }
    }

    private fun disconnect(robot: KawasakiRobotImp, endMessage: String = "") {
        robot.disconnect(endMessage)
        _robots.remove(robot)
    }

    override fun isConnected(ip: String, port: Int): Robot? {
        val robot = _robots.filter { robot -> robot.ip == ip && robot.port == port }
        return if (robot.isEmpty()) {
            null
        } else {
            if (!robot.first().isConnect.value) {
                _robots.remove(robot.first())
                null
            } else {
                robot.first()
            }
        }
    }

    fun disconnectAll() {
        _robots.forEach {
            (it as KawasakiRobotImp).disconnect()
        }
        _robots.clear()
    }

}
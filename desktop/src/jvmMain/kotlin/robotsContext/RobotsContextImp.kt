package robotsContext

import kotlinx.coroutines.CoroutineScope
import robot.Robot
import robot.RobotsContext

class RobotsContextImp(private val coroutineScope: CoroutineScope) : RobotsContext {
    private val _robots = mutableListOf<Robot>()
    val robots: List<Robot> = _robots
    override fun connect(): Robot {
        return connect("localhost", 9105)
    }

    override fun connect(ip: String, port: Int): Robot {
        var robot = isConnected(ip, port)
        return if (robot != null) {
            robot
        } else {
            robot = RobotImp(coroutineScope, ip, port)
            robot.connect()
            _robots.add(robot)
            robot
        }
    }

    override fun disconnect() {
        disconnect(_robots.last() as RobotImp)
    }

    override fun disconnect(ip: String, port: Int, endMessage: String) {
        val _robot = isConnected(ip, port)
        if (_robot != null) {
            disconnect(_robot as RobotImp, endMessage)
        }
    }

    private fun disconnect(robot: RobotImp, endMessage: String = "") {
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

}
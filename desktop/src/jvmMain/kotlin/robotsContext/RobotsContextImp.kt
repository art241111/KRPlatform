package robotsContext

import kotlinx.coroutines.CoroutineScope
import robot.Robot
import robot.RobotsContext

class RobotsContextImp(private val coroutineScope: CoroutineScope) : RobotsContext {
    private val _robots = mutableListOf<Robot>()
    val robots: List<Robot> = _robots
    override fun connect(): Robot {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun disconnect(ip: String, port: Int, endMessage: String) {
        val _robot = isConnected(ip, port)
        if (_robot != null) {
            (_robot as RobotImp).disconnect(endMessage)
            this._robots.remove(_robot)
        }
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
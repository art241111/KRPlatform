package robotsContext

import kotlinx.coroutines.CoroutineScope
import robot.Robot
import robot.RobotsContext

class RobotsContextImp(private val coroutineScope: CoroutineScope) : RobotsContext {
    private val _robots = mutableMapOf<String, Robot>()
    val robots: Map<String, Robot> = _robots

    override fun addRobot(name: String, ip: String, port: Int) {
        _robots[name] = RobotImp(coroutineScope, ip, port)
    }

    override fun changeRobotName(oldName: String, newName: String) {

    }

    override fun deleteRobot(name: String) {
        _robots.remove(name)
    }

    override fun getRobot(name: String): Robot {
        return _robots[name]!!
    }

    override fun getRobotsName(): List<String> {
        return _robots.keys.toList()
    }
}
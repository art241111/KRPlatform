package robotsContext

import robot.Robot
import robot.RobotsContext

class RobotsContextImp : RobotsContext {
    private val _robots = mutableMapOf<String, Robot>()
    val robots: Map<String, Robot> = _robots

    override fun addRobot(name: String, robot: Robot) {
        _robots[name] = robot
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
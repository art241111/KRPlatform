package plugin.conetexts.robotsContext

import Data
import robot.RobotData

class RobotDataImpl(
    override val axesCount: Int,
    override val backup: List<String>,
    override val brakeCounter: Int,
    override val eStopCounter: Int,
    override val motorOnCounter: Int,
    override val motorsMoveAngles: List<Double?>,
    override val motorsMoveTimes: List<Double?>,
    override val robotType: String,
    override val serialNumber: String,
    override val uptimeController: Double,
    override val uptimeServo: Double
) : RobotData {
    companion object {
        fun create(data: Data): RobotData {
            with(data) {
                return RobotDataImpl(
                    axesCount,
                    backup,
                    brakeCounter,
                    eStopCounter,
                    motorOnCounter,
                    motorsMoveAngles,
                    motorsMoveTimes,
                    robotType,
                    serialNumber,
                    uptimeController,
                    uptimeServo
                )
            }
        }
    }
}
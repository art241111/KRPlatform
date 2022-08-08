package robotsContext

import KRobot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import robot.Robot
import robot.RobotData

class KawasakiRobotImp(
    val coroutineScope: CoroutineScope,
    override val ip: String = "197.0.0.1",
    override val port: Int = 9
) : Robot {
    val kRobot = KRobot(coroutineScope)

    override val dataHandler: SharedFlow<String> = kRobot.incomingText
    override val isConnect: StateFlow<Boolean> = kRobot.isConnect
    override fun getInformation(): RobotData? {
        return kRobot.data?.let { RobotDataImpl.create(it) }
    }

    fun connect() {
        coroutineScope.launch(Dispatchers.IO) {
            kRobot.connect(ip, port)
        }
    }

    fun disconnect(endMessage: String) {
        kRobot.disconnect(endMessage)
    }

    override fun send(message: String) {
        kRobot.send(message)
    }

//    override suspend fun sendWithRequest(message: String): String {
//
//    }
}
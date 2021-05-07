package com.rye.catcher.gooddemos

import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.HashMap

/**
 * Create by rye
 * at 2021/3/15
 * @description: 监听器；注册事件
 */
object PlayerV2EventManager {
    const val TAG = "PlayerV2EventManager"

    private val mReceiversMap = Collections.synchronizedMap(HashMap<String, MutableList<PlayerV2EventReceiver>>())

    fun register(receiver: PlayerV2EventReceiver, vararg events: String) {
        if (events.isEmpty()) return
        events.forEach {
            var receivers = mReceiversMap[it]
            if (receivers == null) {
                receivers = CopyOnWriteArrayList<PlayerV2EventReceiver>()
            }
            if (!receivers.contains(receiver)) {
                receivers?.add(receiver)
                mReceiversMap[it] = receivers
            }
        }
    }

    fun unregister(receiver: PlayerV2EventReceiver) {
        val iterator = mReceiversMap.keys.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            val receivers = mReceiversMap[entry]
            if (receivers?.isNotEmpty() == true && receivers?.contains(receiver)) {
                receivers.remove(receiver)
                if (receivers.isEmpty()) {
                    iterator.remove()
                }
            }
        }
    }

    fun dispatchEventV2(type: String, vararg data: Any?) {
        val receivers = mReceiversMap[type]
        receivers?.forEach {
            it.onEvent(type, *data)
        }
    }

}

interface PlayerV2EventReceiver {
    fun onEvent(type: String, vararg data: Any?)
}
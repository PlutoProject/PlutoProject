package plutoproject.framework.paper.api.statistic

import plutoproject.framework.common.util.inject.Koin

interface StatisticProvider {
    companion object : StatisticProvider by Koin.get()

    val type: StatisticProviderType

    fun getLoadLevel(): LoadLevel?

    fun getTicksPerSecond(time: MeasuringTime): Double?

    fun getMillsPerTick(time: MeasuringTime): Double?

    fun getCpuUsageSystem(time: MeasuringTime): Double?

    fun getCpuUsageProcess(time: MeasuringTime): Double?
}

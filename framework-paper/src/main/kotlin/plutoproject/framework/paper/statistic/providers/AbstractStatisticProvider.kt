package plutoproject.framework.paper.statistic.providers

import plutoproject.framework.paper.api.statistic.LoadLevel
import plutoproject.framework.paper.api.statistic.MeasuringTime
import plutoproject.framework.paper.api.statistic.StatisticProvider

abstract class AbstractStatisticProvider : StatisticProvider {
    override fun getLoadLevel(): LoadLevel? {
        val millsPerTick = getMillsPerTick(MeasuringTime.SECONDS_10) ?: return null
        return when {
            millsPerTick < 25.0 -> LoadLevel.LOW
            millsPerTick in 25.0..50.0 -> LoadLevel.MODERATE
            millsPerTick > 50 -> LoadLevel.HIGH
            else -> error("Unreachable")
        }
    }
}

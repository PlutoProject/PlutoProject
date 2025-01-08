package plutoproject.feature.paper.dynamicScheduling

import plutoproject.framework.common.api.options.EntryValueType
import plutoproject.framework.common.api.options.dsl.OptionDescriptor

val DynamicViewDistanceOptionDescriptor = OptionDescriptor<Boolean> {
    key = "hypervisor.dynamic_view_distance"
    type = EntryValueType.BOOLEAN
    defaultValue = false
}

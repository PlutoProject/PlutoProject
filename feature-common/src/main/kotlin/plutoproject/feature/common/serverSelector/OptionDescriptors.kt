package plutoproject.feature.common.serverSelector

import plutoproject.framework.common.api.options.EntryValueType
import plutoproject.framework.common.api.options.dsl.OptionDescriptor

val AutoJoinOptionDescriptor = OptionDescriptor<Boolean> {
    key = "server_selector.auto_join"
    type = EntryValueType.BOOLEAN
    defaultValue = false
}

package plutoproject.framework.common.api.options.factory

import plutoproject.framework.common.api.options.EntryValueType
import plutoproject.framework.common.api.options.Limitation
import plutoproject.framework.common.api.options.OptionDescriptor
import plutoproject.framework.common.util.inject.Koin

interface OptionDescriptorFactory {
    companion object : OptionDescriptorFactory by Koin.get()

    fun <T> create(
        key: String,
        type: EntryValueType,
        defaultValue: T? = null,
        objectClass: Class<*>? = null,
        limitation: Limitation<T>? = null
    ): OptionDescriptor<T>
}

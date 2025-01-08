package plutoproject.feature.paper.api.menu.factory

import plutoproject.feature.paper.api.menu.descriptor.ButtonDescriptor
import plutoproject.framework.common.util.inject.Koin

interface ButtonDescriptorFactory {
    companion object : ButtonDescriptorFactory by Koin.get()

    fun create(id: String): ButtonDescriptor
}

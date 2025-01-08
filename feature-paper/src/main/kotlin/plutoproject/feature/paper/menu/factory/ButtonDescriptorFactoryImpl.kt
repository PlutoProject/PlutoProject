package plutoproject.feature.paper.menu.factory

import plutoproject.feature.paper.api.menu.descriptor.ButtonDescriptor
import plutoproject.feature.paper.api.menu.factory.ButtonDescriptorFactory
import plutoproject.feature.paper.menu.descriptor.ButtonDescriptorImpl

class ButtonDescriptorFactoryImpl : ButtonDescriptorFactory {
    override fun create(id: String): ButtonDescriptor {
        return ButtonDescriptorImpl(id)
    }
}

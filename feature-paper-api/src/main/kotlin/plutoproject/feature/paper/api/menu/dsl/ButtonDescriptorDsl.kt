package plutoproject.feature.paper.api.menu.dsl

import plutoproject.feature.paper.api.menu.descriptor.ButtonDescriptor
import plutoproject.feature.paper.api.menu.factory.ButtonDescriptorFactory

class ButtonDescriptorDsl {
    var id: String? = null

    fun build(): ButtonDescriptor = ButtonDescriptorFactory.create(
        id = id ?: error("Id not set")
    )
}

inline fun ButtonDescriptor(block: ButtonDescriptorDsl.() -> Unit): ButtonDescriptor =
    ButtonDescriptorDsl().apply(block).build()

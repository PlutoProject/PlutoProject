package plutoproject.feature.paper.api.menu.factory

import net.kyori.adventure.text.Component
import org.bukkit.Material
import plutoproject.feature.paper.api.menu.descriptor.PageDescriptor
import plutoproject.framework.common.util.inject.Koin

interface PageDescriptorFactory {
    companion object : PageDescriptorFactory by Koin.get()

    fun create(
        id: String,
        icon: Material,
        name: Component,
        description: List<Component>,
        customPagingButtonId: String? = null
    ): PageDescriptor
}

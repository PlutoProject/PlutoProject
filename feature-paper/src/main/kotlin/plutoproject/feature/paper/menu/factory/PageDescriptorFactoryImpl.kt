package plutoproject.feature.paper.menu.factory

import net.kyori.adventure.text.Component
import org.bukkit.Material
import plutoproject.feature.paper.api.menu.descriptor.PageDescriptor
import plutoproject.feature.paper.api.menu.factory.PageDescriptorFactory
import plutoproject.feature.paper.menu.descriptor.PageDescriptorImpl

class PageDescriptorFactoryImpl : PageDescriptorFactory {
    override fun create(
        id: String,
        icon: Material,
        name: Component,
        description: List<Component>,
        customPagingButtonId: String?
    ): PageDescriptor = PageDescriptorImpl(id, icon, name, description, customPagingButtonId)
}

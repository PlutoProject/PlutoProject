package plutoproject.feature.paper.menu.descriptor

import net.kyori.adventure.text.Component
import org.bukkit.Material
import plutoproject.feature.paper.api.menu.descriptor.PageDescriptor

data class PageDescriptorImpl(
    override val id: String,
    override val icon: Material,
    override val name: Component,
    override val description: List<Component>,
    override val customPagingButtonId: String?
) : PageDescriptor

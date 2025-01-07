package plutoproject.framework.paper.api.toast

import net.kyori.adventure.text.Component
import org.bukkit.Material
import plutoproject.framework.common.util.inject.Koin

interface ToastFactory {
    companion object : ToastFactory by Koin.get()

    fun of(
        icon: Material,
        message: Component,
        type: ToastType,
        frame: ToastFrame
    ): Toast
}

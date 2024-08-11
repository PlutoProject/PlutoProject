package ink.pmc.essentials.screens.examples

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ink.pmc.advkt.component.component
import ink.pmc.advkt.component.italic
import ink.pmc.advkt.component.text
import ink.pmc.interactive.api.LocalPlayer
import ink.pmc.interactive.api.inventory.components.Item
import ink.pmc.interactive.api.inventory.components.Spacer
import ink.pmc.interactive.api.inventory.components.canvases.Chest
import ink.pmc.interactive.api.inventory.jetpack.Arrangement
import ink.pmc.interactive.api.inventory.layout.Box
import ink.pmc.interactive.api.inventory.layout.Column
import ink.pmc.interactive.api.inventory.layout.Row
import ink.pmc.interactive.api.inventory.modifiers.Modifier
import ink.pmc.interactive.api.inventory.modifiers.click.clickable
import ink.pmc.interactive.api.inventory.modifiers.fillMaxSize
import ink.pmc.interactive.api.inventory.modifiers.fillMaxWidth
import ink.pmc.interactive.api.inventory.modifiers.height
import ink.pmc.utils.time.ticks
import ink.pmc.utils.visual.*
import kotlinx.coroutines.delay
import net.kyori.adventure.text.Component
import org.bukkit.Material
import kotlin.math.floor

class ExampleScreen1 : Screen {

    override val key: ScreenKey = "essentials_example_1"

    @Composable
    override fun Content() {
        var title by rememberSaveable { mutableStateOf(0.0) }

        LaunchedEffect(Unit) {
            val runtime = Runtime.getRuntime()
            while (true) {
                val total = runtime.totalMemory()
                val free = runtime.freeMemory()
                val used = total - free
                val percentage = (used.toDouble() / total.toDouble()) * 100
                title = floor(percentage)
                delay(1.ticks)
            }
        }

        Chest(
            title = Component.text("测试页面 1 | 服务器内存使用率 $title%"),
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                InnerContents()
            }
        }
    }

    @Composable
    @Suppress("FunctionName")
    private fun InnerContents() {
        val player = LocalPlayer.current
        val navigator = LocalNavigator.currentOrThrow
        var arrange by rememberSaveable { mutableStateOf(Arrangement.Start) }

        fun nextArrange() {
            when (arrange) {
                Arrangement.Start -> arrange = Arrangement.Center
                Arrangement.Center -> arrange = Arrangement.End
                Arrangement.End -> arrange = Arrangement.Start
            }
        }

        Box(modifier = Modifier.fillMaxWidth().height(1)) {
            Row(modifier = Modifier.fillMaxSize()) {
                repeat(9) {
                    Item(
                        material = Material.GRAY_STAINED_GLASS_PANE,
                        name = component { text("占位符") with mochaSubtext0 without italic() }
                    )
                }
            }
            Row(horizontalArrangement = arrange, modifier = Modifier.fillMaxSize()) {
                Item(
                    material = Material.RED_STAINED_GLASS_PANE,
                    name = component { text("关闭菜单") with mochaMaroon without italic() },
                    modifier = Modifier.clickable {
                        player.closeInventory()
                    }
                )
                Item(
                    material = Material.GREEN_STAINED_GLASS_PANE,
                    name = component { text("去往下一页") with mochaGreen without italic() },
                    modifier = Modifier.clickable {
                        navigator.push(ExampleScreen2())
                    }
                )
                Item(
                    material = Material.YELLOW_STAINED_GLASS_PANE,
                    name = component { text("切换排列方式") with mochaYellow without italic() },
                    modifier = Modifier.clickable {
                        nextArrange()
                    }
                )
            }
        }
        Column(modifier = Modifier.fillMaxWidth().height(4), verticalArrangement = Arrangement.Center) {
            var clicks by rememberSaveable { mutableStateOf(0) }
            Row(modifier = Modifier.fillMaxWidth().height(1), horizontalArrangement = Arrangement.Center) {
                Item(
                    material = Material.GREEN_STAINED_GLASS_PANE,
                    name = component { text("增加点击次数") with mochaGreen without italic() },
                    modifier = Modifier.clickable {
                        clicks++
                    }
                )
                Item(
                    material = Material.PAPER,
                    name = component { text("你点击了 $clicks 下") with mochaPink without italic() }
                )
                Item(
                    material = Material.RED_STAINED_GLASS_PANE,
                    name = component { text("减少点击次数") with mochaRed without italic() },
                    modifier = Modifier.clickable {
                        if (clicks == 0) {
                            return@clickable
                        }
                        clicks--
                    }
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth().height(1))
        }
        Row(modifier = Modifier.fillMaxWidth().height(1)) {
            repeat(9) {
                Item(
                    material = Material.GRAY_STAINED_GLASS_PANE,
                    name = component { text("占位符") with mochaSubtext0 without italic() }
                )
            }
        }
    }

}
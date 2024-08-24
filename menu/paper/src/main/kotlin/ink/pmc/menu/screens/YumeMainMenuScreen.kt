package ink.pmc.menu.screens

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ink.pmc.daily.screens.DailyCalenderScreen
import ink.pmc.essentials.RANDOM_TELEPORT_COST_BYPASS
import ink.pmc.essentials.api.home.Home
import ink.pmc.essentials.api.home.HomeManager
import ink.pmc.essentials.api.teleport.TeleportManager
import ink.pmc.essentials.api.teleport.random.RandomTeleportManager
import ink.pmc.essentials.api.warp.Warp
import ink.pmc.essentials.api.warp.WarpManager
import ink.pmc.essentials.screens.HomeViewerScreen
import ink.pmc.interactive.api.LocalPlayer
import ink.pmc.interactive.api.inventory.components.Background
import ink.pmc.interactive.api.inventory.components.Item
import ink.pmc.interactive.api.inventory.components.Space
import ink.pmc.interactive.api.inventory.components.VerticalGrid
import ink.pmc.interactive.api.inventory.components.canvases.Chest
import ink.pmc.interactive.api.inventory.jetpack.Arrangement
import ink.pmc.interactive.api.inventory.layout.Box
import ink.pmc.interactive.api.inventory.layout.Column
import ink.pmc.interactive.api.inventory.layout.Row
import ink.pmc.interactive.api.inventory.modifiers.*
import ink.pmc.interactive.api.inventory.modifiers.click.clickable
import ink.pmc.interactive.api.inventory.stateTransition
import ink.pmc.menu.CO_NEAR_COMMAND
import ink.pmc.menu.economy
import ink.pmc.menu.inspecting
import ink.pmc.menu.messages.*
import ink.pmc.utils.chat.MESSAGE_SOUND
import ink.pmc.utils.chat.UI_INVALID_SOUND
import ink.pmc.utils.chat.UI_SUCCEED_SOUND
import org.bukkit.Material
import org.bukkit.event.inventory.ClickType
import org.koin.compose.koinInject

private const val PANE_COLUMNS = 4
private const val PANE_COLUME_WIDTH = 7
private const val PANE_GRIDS = PANE_COLUMNS * PANE_COLUME_WIDTH

class YumeMainMenuScreen : Screen {

    override val key: ScreenKey = "menu_yume_main"

    @Composable
    override fun Content() {
        Chest(title = YUME_MAIN_TITLE, modifier = Modifier.height(6)) {
            Box(modifier = Modifier.fillMaxSize()) {
                Background()
                Column(modifier = Modifier.fillMaxSize()) {
                    InnerContents()
                }
            }
        }
    }

    @Composable
    @Suppress("FunctionName")
    private fun InnerContents() {
        TopBar()
        Pane()
    }

    @Composable
    @Suppress("FunctionName")
    private fun TopBar() {
        Row(modifier = Modifier.height(1).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Common()
        }
    }

    /*
    * 常用菜单页
    */
    @Composable
    @Suppress("FunctionName")
    private fun Common() {
        Item(
            material = Material.CAMPFIRE,
            name = YUME_MAIN_ITEM_COMMON,
            lore = YUME_MAIN_TAB_LORE
        )
    }

    @Composable
    @Suppress("FunctionName")
    private fun Pane() {
        Row(
            modifier = Modifier.height(PANE_COLUMNS).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxHeight().width(PANE_COLUME_WIDTH)) {
                VerticalGrid(modifier = Modifier.fillMaxSize()) {
                    repeat(PANE_GRIDS) {
                        Space()
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(modifier = Modifier.fillMaxWidth().height(1), horizontalArrangement = Arrangement.Center) {
                        Home()
                        Spawn()
                        Teleport()
                        RandomTeleport()
                        Lookup()
                    }
                    Row(modifier = Modifier.fillMaxWidth().height(1), horizontalArrangement = Arrangement.Center) {
                        Daily()
                        Coin()
                        Wiki()
                    }
                }
            }
        }
    }

    /*
    * 家
    */
    @Composable
    @Suppress("FunctionName")
    private fun Home() {
        val player = LocalPlayer.current
        val navigator = LocalNavigator.currentOrThrow
        /*
        * 0 -> 正常状态
        * 1 -> 无首选家
        * */
        val state = remember { mutableStateOf(0) }
        val manager = koinInject<HomeManager>()
        var preferred by rememberSaveable { mutableStateOf<Home?>(null) }

        // 预加载首选家，避免点击时等待
        LaunchedEffect(Unit) {
            preferred = manager.getPreferredHome(player)
        }

        Item(
            material = Material.LANTERN,
            name = YUME_MAIN_ITEM_HOME,
            lore = when (state.value) {
                0 -> YUME_MAIN_ITEM_HOME_LORE
                1 -> YUME_MAIN_ITEM_HOME_LORE_NO_PREFER
                else -> error("Unreachable")
            },
            enchantmentGlint = state.value > 0,
            modifier = Modifier.clickable {
                if (state.value != 0) return@clickable
                when (clickType) {
                    ClickType.LEFT -> {
                        preferred?.let {
                            player.closeInventory()
                            it.teleport(player)
                            return@clickable
                        }
                        state.stateTransition(1)
                        player.playSound(UI_INVALID_SOUND)
                    }

                    ClickType.RIGHT -> {
                        navigator.push(HomeViewerScreen(player))
                    }

                    else -> {}
                }
            }
        )
    }

    /*
    * 家
    */
    @Composable
    @Suppress("FunctionName")
    private fun Spawn() {
        val player = LocalPlayer.current
        val manager = koinInject<WarpManager>()
        var spawn by rememberSaveable { mutableStateOf<Warp?>(null) }

        // 预加载
        LaunchedEffect(Unit) {
            spawn = manager.getPreferredSpawn(player)
        }

        Item(
            material = Material.COMPASS,
            name = YUME_MAIN_ITEM_SPAWN,
            lore = YUME_MAIN_ITEM_SPAWN_LORE,
            modifier = Modifier.clickable {
                if (clickType != ClickType.LEFT) return@clickable
                spawn?.teleport(player)
            }
        )
    }

    /*
    * 玩家间传送
    */
    @Composable
    @Suppress("FunctionName")
    private fun Teleport() {
        val player = LocalPlayer.current
        val navigator = LocalNavigator.currentOrThrow
        val manager = koinInject<TeleportManager>()
        /*
        * 0 -> 正常状态
        * 1 -> 有未接受的请求
        * */
        val state = remember { mutableStateOf(0) }
        Item(
            material = Material.MINECART,
            name = YUME_MAIN_ITEM_TP,
            lore = when (state.value) {
                0 -> YUME_MAIN_ITEM_TP_LORE
                1 -> YUME_MAIN_ITEM_TP_EXISTED_LORE
                else -> error("Unreachable")
            },
            enchantmentGlint = state.value > 0,
            modifier = Modifier.clickable {
                if (state.value != 0) return@clickable
                if (clickType != ClickType.LEFT) return@clickable

                if (manager.hasUnfinishedRequest(player)) {
                    state.stateTransition(1)
                    player.playSound(UI_INVALID_SOUND)
                    return@clickable
                }

                navigator.push(TeleportRequestScreen())
            }
        )
    }

    /*
    * 随机传送
    */
    @Composable
    @Suppress("FunctionName")
    private fun RandomTeleport() {
        val player = LocalPlayer.current
        /*
        * 0 -> 正常状态
        * 1 -> 货币不足
        * 2 -> 该世界未启用
        * */
        val state = remember { mutableStateOf(0) }
        val manager = koinInject<RandomTeleportManager>()

        Item(
            material = Material.AMETHYST_SHARD,
            name = YUME_MAIN_ITEM_HOME_RTP,
            lore = when (state.value) {
                0 -> YUME_MAIN_ITEM_HOME_RTP_LORE
                1 -> YUME_MAIN_ITEM_HOME_RTP_NOT_ENABLED_LORE
                2 -> YUME_MAIN_ITEM_HOME_RTP_COIN_NOT_ENOUGH_LORE
                else -> error("Unreachable")
            },
            enchantmentGlint = state.value > 0,
            modifier = Modifier.clickable {
                if (state.value != 0) return@clickable
                if (clickType != ClickType.LEFT) return@clickable
                val balance = economy.getBalance(player)
                val cost = manager.defaultOptions.cost
                val world = player.world

                if (!manager.isEnabled(world)) {
                    state.stateTransition(1)
                    player.playSound(UI_INVALID_SOUND)
                    return@clickable
                }

                if (balance < cost && !player.hasPermission(RANDOM_TELEPORT_COST_BYPASS)) {
                    state.stateTransition(2)
                    player.playSound(UI_INVALID_SOUND)
                    return@clickable
                }

                player.closeInventory()
                manager.launch(player, player.world)
            }
        )
    }

    /*
    * 查询周围
    */
    @Composable
    @Suppress("FunctionName")
    private fun Lookup() {
        val player = LocalPlayer.current
        /*
        * 0 -> 正常展示
        * 1 -> 开启
        * 2 -> 已开启（过渡）
        * 3 -> 已关闭（过渡）
        * */
        val state = rememberSaveable { mutableStateOf(if (!player.inspecting) 0 else 1) }
        Item(
            material = Material.SPYGLASS,
            name = when (state.value) {
                0 -> YUME_MAIN_ITEM_HOME_LOOKUP
                1 -> YUME_MAIN_ITEM_HOME_LOOKUP
                2 -> YUME_MAIN_ITEM_HOME_LOOKUP_ENABLE
                3 -> YUME_MAIN_ITEM_HOME_LOOKUP_DISABLE
                else -> error("Unreachable")
            },
            lore = when (state.value) {
                0 -> YUME_MAIN_ITEM_HOME_LOOKUP_LORE
                1 -> YUME_MAIN_ITEM_HOME_LOOKUP_ENABLED_LORE
                2 -> listOf()
                3 -> listOf()
                else -> error("Unreachable")
            },
            enchantmentGlint = state.value > 0,
            modifier = Modifier.clickable {
                if (state.value > 1) return@clickable

                if (state.value == 0) {
                    when (clickType) {
                        ClickType.LEFT -> {
                            player.inspecting = true
                            state.stateTransition(2, resume = 1)
                            player.playSound(UI_SUCCEED_SOUND)
                            return@clickable
                        }

                        ClickType.RIGHT -> {
                            player.performCommand(CO_NEAR_COMMAND)
                            player.closeInventory()
                            player.playSound(UI_SUCCEED_SOUND)
                        }

                        else -> {}
                    }
                    return@clickable
                }

                if (state.value == 1 && clickType == ClickType.LEFT && player.inspecting) {
                    player.inspecting = false
                    state.stateTransition(3, resume = 0)
                    player.playSound(UI_SUCCEED_SOUND)
                    return@clickable
                }
            }
        )
    }

    /*
    * 每日签到
    */
    @Composable
    @Suppress("FunctionName")
    private fun Daily() {
        val navigator = LocalNavigator.currentOrThrow
        Item(
            material = Material.NAME_TAG,
            name = YUME_MAIN_ITEMS_DAILY,
            lore = YUME_MAIN_ITEMS_DAILY_LORE,
            modifier = Modifier.clickable {
                if (clickType != ClickType.LEFT) return@clickable
                navigator.push(DailyCalenderScreen())
            }
        )
    }

    /*
    * 货币信息
    */
    @Composable
    @Suppress("FunctionName")
    private fun Coin() {
        val player = LocalPlayer.current
        Item(
            material = Material.SUNFLOWER,
            name = YUME_MAIN_ITEM_COINS,
            lore = YUME_MAIN_ITEM_COINS_LORE(player),
        )
    }

    /*
    * Wiki
    */
    @Composable
    @Suppress("FunctionName")
    private fun Wiki() {
        val player = LocalPlayer.current
        Item(
            material = Material.BOOK,
            name = YUME_MAIN_ITEM_WIKI,
            lore = YUME_MAIN_ITEM_WIKI_LORE,
            modifier = Modifier.clickable {
                if (clickType != ClickType.LEFT) return@clickable
                player.closeInventory()
                player.sendMessage(YUME_MAIN_WIKI)
                player.playSound(MESSAGE_SOUND)
            }
        )
    }

}
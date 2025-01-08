package plutoproject.feature.paper.menu.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import plutoproject.feature.paper.api.menu.MenuPrebuilt
import plutoproject.feature.paper.api.menu.MenuScreenModel

class MenuScreenModelImpl : ScreenModel, MenuScreenModel {
    override var currentPageId by mutableStateOf(MenuPrebuilt.Pages.HOME_PAGE_ID)
}

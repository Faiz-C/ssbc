package org.verse.ssbc.views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
class ViewContainer(private val views: List<View>) : View {

  @Composable
  @Preview
  override fun render() {
    val pagerState: PagerState = rememberPagerState()

    Scaffold {
      Column {
        tabBar(pagerState)
        HorizontalPager(state = pagerState, count = views.size) {
          views[it].render()
        }
      }
    }
  }

  override fun name() = "Container"

  @Composable
  private fun tabBar(pagerState: PagerState) {
    val scope: CoroutineScope = rememberCoroutineScope()

    TabRow(
      selectedTabIndex = pagerState.currentPage,
      backgroundColor = Color.Magenta,
      contentColor = Color.White,
      indicator = {
        TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, it))
      }
    ) {
      this.views.forEachIndexed { i, view ->
        Tab(
          text = { Text(view.name()) },
          selected = pagerState.currentPage == i,
          onClick = {
            scope.launch {
              pagerState.scrollToPage(i)
            }
          }
        )
      }
    }
  }


}
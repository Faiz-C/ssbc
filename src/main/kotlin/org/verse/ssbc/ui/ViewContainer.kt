package org.verse.ssbc.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
      backgroundColor = MaterialTheme.colors.primary,
      indicator = {
        TabRowDefaults.Indicator(
          modifier = Modifier.pagerTabIndicatorOffset(pagerState, it),
          height = 0.5.dp,
          color = MaterialTheme.colors.secondary
        )
      }
    ) {
      this.views.forEachIndexed { i, view ->
        Tab(
          text = {
            Text(
              text = view.name(),
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colors.onPrimary
            )
          },
          selected = pagerState.currentPage == i,
          onClick = {
            scope.launch {
              pagerState.scrollToPage(i)
            }
          },
        )
      }
    }
  }

}
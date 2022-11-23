package com.teclu.mobility2.ui.screens.markings

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun PaginatedMarcacionesScreen(
//    navController: NavController,
//    scaffoldState: ScaffoldState,
//    viewModel : MarkingPViewModel = hiltViewModel()
){
//    val paginItems = rememberFlowWithLifecycle(flow = viewModel.pagingState).collectAsLazyPagingItems()
//    LazyColumn(modifier = Modifier.fillMaxSize()){
//        item {
//            Text(text = paginItems.itemCount.toString())
//        }
//        items(items = paginItems){item->
//            MarcacionItem(
//                item = item,
//                onClick = { navController.navigate(MainDestination.USER_DETAIL + "/${item?.cardImageUser?.userGui}"){
//                    launchSingleTop = true
//                } }
//            )
//            Divider()
//        }
//
//        if (paginItems.loadState.append == LoadState.Loading) {
//            item {
//                Box(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(24.dp)
//                ) {
//                    CircularProgressIndicator(Modifier.align(Alignment.Center))
//                }
//            }
//        }
//
//    }
}
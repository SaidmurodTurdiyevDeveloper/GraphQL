package uz.graphql.rickyandmorty.preseenter.model

import androidx.annotation.DrawableRes
import uz.graphql.rickyandmorty.R

/**
 * Created by Saidmurod Turdiyev (S.M.T) on 2/13/2023 12:55 PM for Rick And Morty GraphQL.
 */
data class StartingData(
    val image: String?,
    @DrawableRes
    val imageRes: Int,
    val title: String,
    val description: String
)

val pagerList: List<StartingData> = listOf(
    StartingData(
        image = "https://i.pinimg.com/474x/da/5e/8d/da5e8d42a0d1cd3f15f6ae07314a023b.jpg",
        imageRes = R.drawable.ricky,
        title = "Ricky",
        description = "Richard \"Rick\" Sanchez, voiced by Justin Roiland, is a sociopathic, nihilistic, narcissistic, self-centered, alcoholic mad scientist who is the father of Beth Smith and the maternal grandfather of Morty and Summer. Roiland considers his voice for Rick to be a \"horrible Doc Brown manic impression\""
    ),
    StartingData(
        image = "https://i.pinimg.com/originals/70/5e/09/705e09f726b7015445a976ef8b7a044e.jpg",
        imageRes = R.drawable.morty,
        title = "Morty",
        description = "Mortimer \"Morty\" Smith, Sr., voiced by Justin Roiland, is Rick's neurotic 14-year-old grandson, son of Jerry and Beth Smith and younger brother of Summer Smith, who is frequently dragged into Rick's misadventures. One of Morty's interdimensional counterparts, President Morty Smith (nicknamed \"Evil Morty\" by fans and the media), serves as the main antagonist of the series. Roiland considers his initial voice for Morty to be a \"horrible Marty McFly impression\""
    ),
    StartingData(
        image = "https://s.yimg.com/ny/api/res/1.2/8BHnkR_z.3BT58Cs8P49yg--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD02NzU-/https://s.yimg.com/os/creatr-uploaded-images/2023-01/4f3d5e00-9397-11ed-815f-35cc0d5d248b",
        imageRes = R.drawable.ricky_and_morty,
        title = "Ricky and Morty",
        description = "Rick and Morty is an American animated science-fiction comedy franchise, owned by Warner Bros. Discovery, whose eponymous duo consists of Rick Sanchez and Morty Smith. Rick and Morty were created by cartoonist Justin Roiland for a 2006 parody film of Back to the Future for Channel 101, a short film festival co-founded by Dan Harmon"
    )
)

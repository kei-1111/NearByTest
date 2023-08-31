package com.example.nearbyconnectionstest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

data class CardsObj(
    //@DocumentId val id: String? = "",
    val id: String = "",
    val avater: Int? = null,
    var name: String? = "",
    var name_roma: String? = "",
    var gender: String? = "",
    var job: String? = "",
    var birthday: String? = "",
    var message: String? = "",
    var friendlist: MutableList<CardsObj> = mutableListOf()
)

val card1 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
    null,
    "田中 春香",
    "Tanaka Haruka",
    "女性",
    "UIデザイナー",
    "1990.04.15",
    "UIで人々の生活を豊かにする！",
    mutableListOf()
)

val card2 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
   null,
    "山田 たけし",
    "Yamada Takashi",
    "男性",
    "イラストレーター",
    "1985.09.03",
    "絵を描くことで、世界に色を添えています。",
    mutableListOf()
)

val card3 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
    null,
    "中村 みさき",
    "Nakamura Misaki",
    "女性",
    "学生",
    "2002.07.20",
    "言葉の力で人々の心に感動を届けたい。",
    mutableListOf()
)

val card4 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
    null,
    "佐藤 けんじ",
    "Sata Kenji",
    "男性",
    "エンジニア",
    "1995.02.08",
    "コードを書くことで、未来を創り出すエンジニアです。",
    mutableListOf()
)

val card5 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
    null,
    "高橋 愛子",
    "Takahashi Aiko",
    "女性",
    "マネージャー",
    "1988.11.25",
    "人を支えるのが好きだからこの仕事に付きました。",
    mutableListOf()
)

val card6 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
    null,
    "伊藤 高志",
    "Ito Takashi",
    "男性",
    "レストランシェフ",
    "1977.06.12",
    "食材の魔法で心温まる料理を提供します",
    mutableListOf()
)

val card7 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
    null,
    "木村 優希",
    "Kimura Yuki",
    "女性",
    "ミュージシャン",
    "1993.08.01",
    "音楽は私の魂のメロディです！",
    mutableListOf()
)

val card8 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
    null,
    "渡辺 圭太",
    "Watanabe Keta",
    "男性",
    "プロスケーター",
    "2000.01.30",
    "4回転が良き。",
    mutableListOf()
)

val card9 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
    null,
    "加藤 みゆき",
    "Kato Miyuki",
    "女性",
    "医師",
    "1980.05.09",
    "健康第一",
    mutableListOf()
)

val card10 = CardsObj(
    "h3PzMEypQlRXno4NkXZV14uw0J12",
    null,
    "佐々木 太郎",
    "Sasaki Taro",
    "男性",
    "パイロット",
    "1987.12.18",
    "高く大空を飛び、新たな冒険を楽しむ毎日です。",
    mutableListOf()
)

val allCards: Set<CardsObj> =
    setOf(
        card1,
        card2,
        card3,
        card4,
        card5,
        card6,
        card7,
        card8,
        card9,
        card10,
    )

@Composable
fun BusinessCard(data: CardsObj, cardSize: String) {

    val name_lenght = data.name?.length

    var cardHeight = 0.dp
    var cardWidth = 0.dp
    if (cardSize == "small") {
        cardHeight = 100.dp
        cardWidth = 161.80339887499.dp
    } else if (cardSize == "big") {
        cardHeight = 207.04138623121.dp
        cardWidth = 335.dp
    }
    Box(
        modifier = Modifier
            .height(cardHeight)
            .width(cardWidth)
            .background(color = Color(0x6600ffaa), shape = RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.9f)
            ) {
                Row {
                    Column(
                        modifier = Modifier
                            .height((cardWidth / 10.dp) * (3.dp)),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Column {
                            data.name?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier
                                        .fillMaxWidth(0.7f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = dpToSp((cardWidth / 10.dp) * (1.dp))
                                )
                            }
                            data.name_roma?.let {
                                if (name_lenght != null) {
                                    Text(
                                        text = it,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .width((cardWidth / 10.dp) * (name_lenght.dp)),
                                        fontSize = dpToSp((cardWidth / 10.dp) * (0.6.dp)),
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .background(
                                    color = Color(0xf5f5f5ff),
                                    shape = CircleShape
                                )
                                .fillMaxWidth(0.5f),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row {
                                data.gender?.let {
                                    Text(
                                        text = it,
                                        fontSize = dpToSp((cardWidth / 10.dp) * (0.3.dp))
                                    )
                                }
                            }
                            Row {

                            }
                        }
                    }
                    data.avater?.let { painterResource(id = it) }?.let {
                        Image(
                            painter = it,
                            contentDescription = "Contact profile picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(cardWidth / 8.dp))
                                .size((cardWidth / 10.dp) * (3.dp)),
                        )
                    }
                }
                Row {
                    data.message?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .background(
                                    color = Color(0xf5f5f5ff),
                                    shape = RoundedCornerShape(cardWidth / 20.dp)
                                )
                                .padding(start = 5.dp, top = 1.dp, end = 5.dp, bottom = 1.dp)
                                .fillMaxWidth(0.7f)
                                .fillMaxHeight(0.5f),
                            fontSize = dpToSp((cardWidth / 10.dp) * (0.4.dp))
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                    }
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                }
            }
        }
    }
}

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }
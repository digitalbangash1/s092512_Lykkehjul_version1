package com.example.s092512_lykkehjul


class Data {
    fun loadPoints():List<Point>{
       return listOf<Point>(
           Point("1000"),
           Point("Loss turn"),
           Point("300"),
           Point("100"),
           Point("200"),
           Point("Bankrupt"),
           Point("500"),
           Point("Loss Life"))
    }

    fun loadRandomWords():List<String>{
        return listOf<String>(
            "one","hi","hvad","klassen","skole"
        )
    }

}
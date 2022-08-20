package com.myplaylists.dto

/**
 * https://developers.google.com/youtube/v3/docs/search#resource
 */
class YoutubeDto(
    var songs: ArrayList<Map<String, *>> = arrayListOf()
): BaseResponse() {
    fun add(song: Map<String, *>) = songs.add(song)
}

class YoutubeResponse(
    val items: List<YoutubeItem>
) {
}

class YoutubeItem(
    val id: ItemId,
    val snippet: Snippet,
)

class ItemId(
    val videoId: String,
)

class Snippet(
    val title: String
)
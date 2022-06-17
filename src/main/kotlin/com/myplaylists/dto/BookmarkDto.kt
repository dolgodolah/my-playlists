package com.myplaylists.dto

class BookmarkDto(
    val isBookmark: Boolean
): BaseResponse() {

    companion object {
        fun of(isBookmark: Boolean) = BookmarkDto(
            isBookmark = isBookmark
        )
    }

}
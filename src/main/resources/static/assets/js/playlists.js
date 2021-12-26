function getPlaylists(page, url) {
    $.ajax({
        type: "GET",
        url: url,
        data: "page=" + page,
        success: function (response) {
            let playlistsHtml = "";
            $(response.playlists).each(function (idx, playlist) {
                playlistsHtml += `
                        <li class="clearfix">
                            <a onClick="getPlaylistDetail(${playlist.playlistId})">
                                <div class="about">
                                    <div class="name">${playlist.title}</div>
                                    <div class="status">${playlist.description}</div>
                                </div>
                            </a>
                        </li>
                    `
            });
            $("#playlists").html(playlistsHtml)

            const previousClass = page === 0 ? "page-link disabled" : "page-link";
            const nextClass = response.last ? "page-link disabled" : "page-link";
            const paginationHtml = `
                <a class="my-info" href="/me">내 정보</a>
                <a id="previous" class="${previousClass}">◀</a>
                <a class="fa fa-home" href="/"></a>
                <a id="next" class="${nextClass}">▶</a>
                <a class="logout-btn" href="/logout" role="button">로그아웃</a>
                `
            $("#pagination").html(paginationHtml)

            $("#next").click(function () {
                getPlaylists(page + 1, url)
            })
            $("#previous").click(function () {
                getPlaylists(page - 1, url)
            })
        }
    })
}

function getPlaylistDetail(playlistId) {
    $.ajax({
        type: "GET",
        url: "/playlist/" + playlistId,
        success: function (playlist) {
            console.log(playlist);
            $(".search").css("visibility", "hidden");

            let songsHtml = "";
            $(playlist.songs).each(function (idx, song) {
                songsHtml +=
                    `<li class="clearfix">
                        <a href="@{'/playlist/'+${playlistId}+'/'+${song.id}}">
                            <div class="about">
                                <div class="name">${song.title}</div>
                                <div class="status">${song.createdDate}</div>
                            </div>
                        </a>
                    </li>`

            });
            $("#playlists").html(songsHtml)

            let detailHtml =
                `<div class="chat-header clearfix">
                    <div class="chat-about">
                        <div class="chat-with">${playlist.title}</div>
                        <div class="chat-num-messages">${playlist.author}의 플레이리스트</div>
                    </div>
                    <form action="@{'/playlist/'+${playlist.id}}" method="post" name="deletePlaylistForm">
                        <input type="hidden" name="_method" value="delete"/>
                        <button class="trash-button" type="button" onclick="checkPlaylistDeletion()"><i class="fa fa-trash"></i></button>
                    </form>
<!--                    세션 검증해서 본인 것만 삭제, 노래 추가 버튼 보이게 해야되고, 즐겨찾기 여부도 판단해야됨 -->
                    <a href="@{'/playlist/'+${playlist.id} + '/add'}"><i class="fa fa-plus"></i></a>
                    <button id="star-button" class="star-button"><i id="star" class="fa fa-star"></i></button>
                </div>
                <div class="chat-history">
                    <div class="chat-num-messages">${playlist.description}</div>
                </div>`

            $("#detail").html(detailHtml)
        }
    })
}
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
                            <a href="/playlist/${playlist.playlistId}">
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
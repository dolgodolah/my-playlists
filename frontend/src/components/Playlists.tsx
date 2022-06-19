import { Icon } from "@iconify/react";
import {Link, useSearchParams} from "react-router-dom";
import { PlaylistProps } from "../shared/Props";
import React, {useCallback, useEffect, useState} from "react";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import HeaderLogo from "./HeaderLogo";
import moment from "moment";

interface PageProps {
  page: string;
}

// TODO: 중복 코드들 컴포넌트로 분리 필요

export const MyPlaylists = () => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]);
  const [keyword, setKeyword] = useState(params.get('keyword') || "");
  const [pageIndex, setPageIndex] = useState(0);
  const [isLastPlaylists, setLastPlaylists] = useState(false);

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/search?keyword=" + keyword;
    }
  }

  useEffect(() => {
    axios.get(`/my_playlists?page=${pageIndex}`).then( (res) => {
      const response = res.data
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          setLastPlaylists(response.isLast)
          break;
        default:
          alertError(response.message)
          break;
      }
    });

  }, [pageIndex])

  const handleScroll = (e: React.UIEvent) => {
    const isLastHeight = e.currentTarget.scrollHeight - e.currentTarget.scrollTop === e.currentTarget.clientHeight;
    if (isLastHeight && !isLastPlaylists) {
      setPageIndex(pageIndex + 1)
    }
  }

  return (
    <>
      <div className="header__container">
        <HeaderLogo />
        <div className="search__container">
          <input
            type="text"
            placeholder="플레이리스트 검색"
            className="search__input--header"
            value={keyword}
            onChange={onChangeKeyword}
            onKeyPress={onPressEnter}
          />
        </div>
      </div>

      <div className="lists__container" onScroll={handleScroll}>
        {playlists.map((playlist: PlaylistProps) => (
          <Link
            key={playlist.playlistId}
            to="/playlist"
            state={{
              page: "showSongs",
              playlist: playlist
            }}
            className="playlist__link"
          >
            <div className="playlist__container">
              <div className="playlist__container--left">
                <span className="playlist__span--title">{playlist.title}</span>
                <div>
              <span className="playlist__span--updatedDate">
                {moment(playlist.updatedDate).format("YYYY년 MM월 DD일")}
              </span>
                  <span className="playlist__span--author">{playlist.author}</span>
                </div>
              </div>
              <div className="playlist__container--right">
                <span className="playlist__span--amount">{playlist.songCount}</span>
                <div className="playlist-icon__container">
                  <Icon icon="bxs:playlist" />
                </div>
              </div>
              <div className="hidden-background__container">
                <span className="hidden-background__container__span">
                  <Icon icon="codicon:debug-start" />
                </span>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </>
  )
};

export const AllPlaylists = () => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]);
  const [keyword, setKeyword] = useState(params.get('keyword') || "");
  const [pageIndex, setPageIndex] = useState(0);
  const [isLastPlaylists, setLastPlaylists] = useState(false);

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/search?keyword=" + keyword;
    }
  }

  useEffect(() => {
    axios.get(`/all_playlists?page=${pageIndex}`).then( (res) => {
      const response = res.data
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          setLastPlaylists(response.isLast)
          break;
        default:
          alertError(response.message)
          break;
      }
    });
  }, [pageIndex])

  const handleScroll = (e: React.UIEvent) => {
    const isLastHeight = e.currentTarget.scrollHeight - e.currentTarget.scrollTop === e.currentTarget.clientHeight;
    if (isLastHeight && !isLastPlaylists) {
      setPageIndex(pageIndex + 1)
    }
  }

  return (
    <>
      <div className="header__container">
        <HeaderLogo />
        <div className="search__container">
          <input
            type="text"
            placeholder="플레이리스트 검색"
            className="search__input--header"
            value={keyword}
            onChange={onChangeKeyword}
            onKeyPress={onPressEnter}
          />
        </div>
      </div>

      <div className="lists__container" onScroll={handleScroll}>
        {playlists.map((playlist: PlaylistProps) => (
          <Link
            key={playlist.playlistId}
            to="/playlist"
            state={{
              page: "showSongs",
              playlist: playlist
            }}
            className="playlist__link"
          >
            <div className="playlist__container">
              <div className="playlist__container--left">
                <span className="playlist__span--title">{playlist.title}</span>
                <div>
              <span className="playlist__span--updatedDate">
                {moment(playlist.updatedDate).format("YYYY년 MM월 DD일")}
              </span>
                  <span className="playlist__span--author">{playlist.author}</span>
                </div>
              </div>
              <div className="playlist__container--right">
                <span className="playlist__span--amount">{playlist.songCount}</span>
                <div className="playlist-icon__container">
                  <Icon icon="bxs:playlist" />
                </div>
              </div>
              <div className="hidden-background__container">
                <span className="hidden-background__container__span">
                  <Icon icon="codicon:debug-start" />
                </span>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </>
  )
};

export const Bookmarks = () => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]);
  const [keyword, setKeyword] = useState(params.get('keyword') || "");
  const [pageIndex, setPageIndex] = useState(0);
  const [isLastPlaylists, setLastPlaylists] = useState(false);

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/search?keyword=" + keyword;
    }
  }

  useEffect(() => {
    axios.get(`/bookmarks?page=${pageIndex}`).then( (res) => {
      const response = res.data
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          setLastPlaylists(response.isLast)
          break;
        default:
          alertError(response.message)
          break;
      }
    });
  }, [pageIndex])

  const handleScroll = (e: React.UIEvent) => {
    const isLastHeight = e.currentTarget.scrollHeight - e.currentTarget.scrollTop === e.currentTarget.clientHeight;
    if (isLastHeight && !isLastPlaylists) {
      setPageIndex(pageIndex + 1)
    }
  }

  return (
    <>
      <div className="header__container">
        <HeaderLogo />
        <div className="search__container">
          <input
            type="text"
            placeholder="플레이리스트 검색"
            className="search__input--header"
            value={keyword}
            onChange={onChangeKeyword}
            onKeyPress={onPressEnter}
          />
        </div>
      </div>

      <div className="lists__container" onScroll={handleScroll}>
        {playlists.map((playlist: PlaylistProps) => (
          <Link
            key={playlist.playlistId}
            to="/playlist"
            state={{
              page: "showSongs",
              playlist: playlist
            }}
            className="playlist__link"
          >
            <div className="playlist__container">
              <div className="playlist__container--left">
                <span className="playlist__span--title">{playlist.title}</span>
                <div>
              <span className="playlist__span--updatedDate">
                {moment(playlist.updatedDate).format("YYYY년 MM월 DD일")}
              </span>
                  <span className="playlist__span--author">{playlist.author}</span>
                </div>
              </div>
              <div className="playlist__container--right">
                <span className="playlist__span--amount">{playlist.songCount}</span>
                <div className="playlist-icon__container">
                  <Icon icon="bxs:playlist" />
                </div>
              </div>
              <div className="hidden-background__container">
                <span className="hidden-background__container__span">
                  <Icon icon="codicon:debug-start" />
                </span>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </>
  )
};

export const SearchPlaylists = () => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]);
  const [keyword, setKeyword] = useState(params.get('keyword') || "");
  const [pageIndex, setPageIndex] = useState(0);
  const [isLastPlaylists, setLastPlaylists] = useState(false);

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/search?keyword=" + keyword;
    }
  }

  useEffect(() => {
    axios.get(`/playlist/search?page=${pageIndex}`, { params: { keyword: keyword } }).then((res) => {
      const response = res.data
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          setLastPlaylists(response.isLast)
          break;
        default:
          alertError(response.message)
          break;
      }
    })
  }, [pageIndex])

  const handleScroll = (e: React.UIEvent) => {
    const isLastHeight = e.currentTarget.scrollHeight - e.currentTarget.scrollTop === e.currentTarget.clientHeight;
    if (isLastHeight && !isLastPlaylists) {
      setPageIndex(pageIndex + 1)
    }
  }

  return (
    <>
      <div className="header__container">
        <HeaderLogo />
        <div className="search__container">
          <input
            type="text"
            placeholder="플레이리스트 검색"
            className="search__input--header"
            value={keyword}
            onChange={onChangeKeyword}
            onKeyPress={onPressEnter}
          />
        </div>
      </div>

      <div className="lists__container" onScroll={handleScroll}>
        {playlists.map((playlist: PlaylistProps) => (
          <Link
            key={playlist.playlistId}
            to="/playlist"
            state={{
              page: "showSongs",
              playlist: playlist
            }}
            className="playlist__link"
          >
            <div className="playlist__container">
              <div className="playlist__container--left">
                <span className="playlist__span--title">{playlist.title}</span>
                <div>
              <span className="playlist__span--updatedDate">
                {moment(playlist.updatedDate).format("YYYY년 MM월 DD일")}
              </span>
                  <span className="playlist__span--author">{playlist.author}</span>
                </div>
              </div>
              <div className="playlist__container--right">
                <span className="playlist__span--amount">{playlist.songCount}</span>
                <div className="playlist-icon__container">
                  <Icon icon="bxs:playlist" />
                </div>
              </div>
              <div className="hidden-background__container">
                <span className="hidden-background__container__span">
                  <Icon icon="codicon:debug-start" />
                </span>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </>
  )
};
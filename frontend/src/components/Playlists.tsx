import { useSearchParams } from "react-router-dom";
import { PlaylistProps } from "../shared/Props";
import React, { useCallback, useEffect, useState } from "react";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import Playlist from "./Playlist";
import PlaylistsHeader from "./PlaylistsHeader";

export const MyPlaylists = () => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]);
  const [keyword, setKeyword] = useState(params.get("keyword") || "");
  const [pageIndex, setPageIndex] = useState(0);
  const [lastPlaylist, setLastPlaylist] = useState<HTMLAnchorElement | null>();

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/search?keyword=" + keyword;
    }
  };

  const onIntersect: IntersectionObserverCallback = (playlists, observer) => {
    playlists.forEach((playlist) => {
      if (playlist.isIntersecting) {
        setPageIndex(pageIndex + 1);
        observer.unobserve(playlist.target);
      }
    });
  };

  useEffect(() => {
    let observer: IntersectionObserver;
    if (lastPlaylist) {
      observer = new IntersectionObserver(onIntersect, { threshold: 0.5 });
      observer.observe(lastPlaylist);
    }
    return () => observer && observer.disconnect();
  }, [lastPlaylist]);

  useEffect(() => {
    axios.get(`/my_playlists?page=${pageIndex}`).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          break;
        default:
          alertError(response.message);
          break;
      }
    });
  }, [pageIndex]);

  return (
    <>
      <div className="header__container">
        <PlaylistsHeader keyword={keyword} onChangeKeyword={onChangeKeyword} onPressEnter={onPressEnter} />
      </div>
      <div className="lists__container">
        {playlists.map((playlist: PlaylistProps) => (
          <Playlist key={playlist.playlistId} playlist={playlist} setLastPlaylist={setLastPlaylist} />
        ))}
      </div>
    </>
  );
};

export const AllPlaylists = () => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]);
  const [keyword, setKeyword] = useState(params.get("keyword") || "");
  const [pageIndex, setPageIndex] = useState(0);
  const [lastPlaylist, setLastPlaylist] = useState<HTMLAnchorElement | null>();

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/search?keyword=" + keyword;
    }
  };

  const onIntersect: IntersectionObserverCallback = (playlists, observer) => {
    playlists.forEach((playlist) => {
      if (playlist.isIntersecting) {
        setPageIndex(pageIndex + 1);
        observer.unobserve(playlist.target);
      }
    });
  };

  useEffect(() => {
    let observer: IntersectionObserver;
    if (lastPlaylist) {
      observer = new IntersectionObserver(onIntersect, { threshold: 0.5 });
      observer.observe(lastPlaylist);
    }
    return () => observer && observer.disconnect();
  }, [lastPlaylist]);

  useEffect(() => {
    axios.get(`/all_playlists?page=${pageIndex}`).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          break;
        default:
          alertError(response.message);
          break;
      }
    });
  }, [pageIndex]);

  return (
    <>
      <div className="header__container">
        <PlaylistsHeader keyword={keyword} onChangeKeyword={onChangeKeyword} onPressEnter={onPressEnter} />
      </div>
      <div className="lists__container">
        {playlists.map((playlist: PlaylistProps) => (
          <Playlist key={playlist.playlistId} playlist={playlist} setLastPlaylist={setLastPlaylist} />
        ))}
      </div>
    </>
  );
};

export const Bookmarks = () => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]);
  const [keyword, setKeyword] = useState(params.get("keyword") || "");
  const [pageIndex, setPageIndex] = useState(0);
  const [lastPlaylist, setLastPlaylist] = useState<HTMLAnchorElement | null>();

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/search?keyword=" + keyword;
    }
  };

  const onIntersect: IntersectionObserverCallback = (playlists, observer) => {
    playlists.forEach((playlist) => {
      if (playlist.isIntersecting) {
        setPageIndex(pageIndex + 1);
        observer.unobserve(playlist.target);
      }
    });
  };

  useEffect(() => {
    let observer: IntersectionObserver;
    if (lastPlaylist) {
      observer = new IntersectionObserver(onIntersect, { threshold: 0.5 });
      observer.observe(lastPlaylist);
    }
    return () => observer && observer.disconnect();
  }, [lastPlaylist]);

  useEffect(() => {
    axios.get(`/bookmarks?page=${pageIndex}`).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          break;
        default:
          alertError(response.message);
          break;
      }
    });
  }, [pageIndex]);

  return (
    <>
      <div className="header__container">
        <PlaylistsHeader keyword={keyword} onChangeKeyword={onChangeKeyword} onPressEnter={onPressEnter} />
      </div>
      <div className="lists__container">
        {playlists.map((playlist: PlaylistProps) => (
          <Playlist key={playlist.playlistId} playlist={playlist} setLastPlaylist={setLastPlaylist} />
        ))}
      </div>
    </>
  );
};

export const SearchPlaylists = () => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]);
  const [keyword, setKeyword] = useState(params.get("keyword") || "");
  const [pageIndex, setPageIndex] = useState(0);
  const [lastPlaylist, setLastPlaylist] = useState<HTMLAnchorElement | null>();

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/search?keyword=" + keyword;
    }
  };

  const onIntersect: IntersectionObserverCallback = (playlists, observer) => {
    playlists.forEach((playlist) => {
      if (playlist.isIntersecting) {
        setPageIndex(pageIndex + 1);
        observer.unobserve(playlist.target);
      }
    });
  };

  useEffect(() => {
    let observer: IntersectionObserver;
    if (lastPlaylist) {
      observer = new IntersectionObserver(onIntersect, { threshold: 0.5 });
      observer.observe(lastPlaylist);
    }
    return () => observer && observer.disconnect();
  }, [lastPlaylist]);

  useEffect(() => {
    axios.get(`/playlist/search?page=${pageIndex}`, { params: { keyword: keyword } }).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          break;
        default:
          alertError(response.message);
          break;
      }
    });
  }, [pageIndex]);

  return (
    <>
      <div className="header__container">
        <PlaylistsHeader keyword={keyword} onChangeKeyword={onChangeKeyword} onPressEnter={onPressEnter} />
      </div>
      <div className="lists__container">
        {playlists.map((playlist: PlaylistProps) => (
          <Playlist key={playlist.playlistId} playlist={playlist} setLastPlaylist={setLastPlaylist} />
        ))}
      </div>
    </>
  );
};

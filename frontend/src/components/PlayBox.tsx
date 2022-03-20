import React from "react";
import { PlayBoxProps } from "../shared/Props";
import {songs, user} from "../test/user";
import Header from "./Header";
import ProfileMenu from "./ProfileMenu";
import PlaylistCategory from "./PlaylistCategory";
import EditBox from "./EditBox";
import Song from "./Song";
import Playlist from "./Playlist";

export const PlaylistBox = ({page, playlists} : PlayBoxProps) => {
  return (
    <div className="play-box__container">
      <Header />
      <div className="play-box__container--top">
              <span className="page-title__span">
                <PlaylistCategory page={page} />
              </span>
        <EditBox page={page} />
      </div>
      <div className="play-box__container--left">
        <div className="my-playlists__container">
          { playlists?.map((playlist, index) => (
            <Playlist key={index} playlist={playlist} />
          )) }
        </div>
      </div>
      <div className="play-box__container--right"></div>
      <ProfileMenu name={user.nickname} />
    </div>
  );
};

export const SongBox = ({page, songs} : PlayBoxProps) => {
  return (
    <div className="play-box__container">
      <Header />
      <div className="play-box__container--top">
              <span className="page-title__span">
                {/*  플리 title, description */}
              </span>
        <EditBox page={page} />
      </div>
      <div className="play-box__container--left">
        <div className="my-playlists__container">
          { songs?.map((song, index) => (
            <Song key={index} song={song} />
          )) }
        </div>
      </div>
      <div className="play-box__container--right"></div>
      <ProfileMenu name={user.nickname} />
    </div>
  );
};

export const PlaySong = ({page, songs} : PlayBoxProps) => {
  return (
    <div className="play-box__container">
      <Header />
      <div className="play-box__container--top">
              <span className="page-title__span">
                {/*  플리 title, description */}
              </span>
        <EditBox page={page} />
      </div>
      <div className="play-box__container--left">
        <div className="my-playlists__container">
          { songs?.map((song, index) => (
            <Song key={index} song={song} />
          )) }
        </div>
      </div>
      <div className="play-box__container--right">
        {/* 유튜브 영상 위치 */}
      </div>
      <ProfileMenu name={user.nickname} />
    </div>
  );
};

import {Icon} from "@iconify/react";
import React from "react";
import Link from "next/link";

const HeaderLogo = () => {
  const goToHome = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault()
    location.href = "/playlists"
  }

  const goToPlaylistAddForm = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault()
    location.href = "/playlists/add"
  }

  return (
    <>
      <div className="logo__container">
        <a href="#" onClick={goToHome}>내플리스</a>
      </div>
      <div className="button__container--header">
        <a href="#" onClick={goToPlaylistAddForm} className="add__button--header">
          <Icon icon="ic:baseline-playlist-add"/>
        </a>
      </div>
    </>
  )
}

export default HeaderLogo;
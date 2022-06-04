import { ReactNode } from "react";

export interface PlayBoxProps {
  left: ReactNode;
  right: ReactNode;
}

export interface PlaylistCategoryProps {
  page: string;
}

export interface PlaylistProps {
  author: string;
  bookmark: boolean;
  description: string;
  playlistId: number;
  title: string;
  updatedDate: string;
  visibility: boolean;
  songCount: number;
}

export interface SongProps {
  songId: number;
  title: string;
  videoId: string;
  description: string;
  createdDate: string;
}

import { ReactNode } from "react";

export interface PlayBoxProps {
  top: ReactNode;
  left: ReactNode;
  right: ReactNode;
}

export interface PlaylistCategoryProps {
  page: string;
}

export interface PlaylistProps {
  playlist: Record<string, any>;
}

export interface SongProps {
  song: Record<string, any>;
}

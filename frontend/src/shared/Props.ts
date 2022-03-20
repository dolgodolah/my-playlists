export interface PlayBoxProps {
  page: string
  songs?: Array<SongProps>
  playlists?: Array<PlaylistProps>
}

export interface PlaylistCategoryProps {
  page: string;
}

export interface EditBoxProps {
  page: string;
}

export interface PlaylistProps {
  playlist: Record<string, any>;
}

export interface SongProps {
  song: Record<string, any>;
}
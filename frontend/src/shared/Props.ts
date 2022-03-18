export interface PlayBoxProps {
  children: React.ReactNode;
}

export interface PlaylistCategoryProps {
  pathname: string;
}

export interface EditBoxProps {
  pathname: string;
}

export interface PlaylistProps {
  playlist: Record<string, any>;
}

export interface SongProps {
  song: Record<string, any>
}

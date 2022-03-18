export interface PlayBoxProps {
  children: React.ReactNode;
}

export interface PlaylistCategoryProps {
  pathname: string;
}

export interface EditBoxProps {
  pathname: string;
}

export interface PlaylistResponseProps {
  author: string;
  bookmark: boolean;
  description: string;
  playlistId: number;
  songs: Array<SongProps>;
  title: string;
  updatedDate: string;
  visibility: boolean;
}

export interface SongProps {
  createdDate: string;
  description: string;
  title: string;
  videoId: string;
}

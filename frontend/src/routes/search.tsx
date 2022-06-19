import PlayBox from "../components/PlayBox";
import {SearchPlaylists} from "../components/Playlists";

const Search = () => {
  return (
    <PlayBox left={<SearchPlaylists />} right={null} />
  );
};

export default Search;

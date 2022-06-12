import PlayBox from "../components/PlayBox";
import Playlists from "../components/Playlists";

const Search = () => {
  return (
    <PlayBox left={<Playlists page={"search"} />} right={null} />
  );
};

export default Search;

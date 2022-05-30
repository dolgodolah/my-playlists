import PlayBox from "../components/PlayBox";
import Playlist from "../components/Playlist";

const Search = () => {
  return (
    <PlayBox top={null} left={<Playlist page={"search"} />} right={null} />
  );
};

export default Search;

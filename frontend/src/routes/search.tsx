import PlayBox from "../components/PlayBox";
import Playlists from "../components/Playlists";
import Header from "../components/Header";
import SearchType from "../shared/SearchType";

const Search = () => {
  return (
    <PlayBox
      left={
        <>
          <Header type={SearchType.PLAYLIST}/>
          <Playlists page={"search"} />
        </>
      }
      right={null}
    />
  );
};

export default Search;

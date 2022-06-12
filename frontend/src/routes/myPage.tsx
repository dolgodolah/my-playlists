import MyPageForm from "../components/MyPageForm";
import PlayBox from "../components/PlayBox";
import Playlists from "../components/Playlists";
import Header from "../components/Header";
import SearchType from "../shared/SearchType";

const MyPage = () => {
  return (
    <PlayBox
      left={
        <>
          <Header type={SearchType.PLAYLIST} />
          <Playlists page={"myPlaylist"} />
        </>
      }
      right={<MyPageForm />}
    />
  );
};

export default MyPage;

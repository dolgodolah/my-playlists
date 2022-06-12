import MyPageForm from "../components/MyPageForm";
import PlayBox from "../components/PlayBox";
import Playlists from "../components/Playlists";

const MyPage = () => {
  return (
    <PlayBox left={<Playlists page={"myPlaylist"} />} right={<MyPageForm />} />
  );
};

export default MyPage;

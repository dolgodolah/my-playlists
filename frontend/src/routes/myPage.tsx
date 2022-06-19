import MyPageForm from "../components/MyPageForm";
import PlayBox from "../components/PlayBox";
import {MyPlaylists} from "../components/Playlists";

const MyPage = () => {
  return (
    <PlayBox left={<MyPlaylists />} right={<MyPageForm />} />
  );
};

export default MyPage;

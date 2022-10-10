import MyPageForm from "../components/MyPageForm";
import ContainerBox from "../components/ContainerBox";
import {MyPlaylists} from "../components/playlist/Playlists";

const Me = () => {
  return (
    <ContainerBox
      left={<MyPlaylists />}
      right={<MyPageForm />}
    />
  );
};

export default Me;

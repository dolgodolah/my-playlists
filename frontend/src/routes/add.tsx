import { AddPlaylistForm } from "../components/InputForm";
import PlayBox from "../components/PlayBox";
import PlaylistDetail from "../components/PlaylistDetail";

const Add = () => {
  return (
    <PlayBox
      top={
        <>
          <PlaylistDetail />
        </>
      }
      left={null}
      right={<AddPlaylistForm />}
    />
  );
};

export default Add;

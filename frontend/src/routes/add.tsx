import { AddPlaylistForm } from "../components/InputForm";
import PlayBox from "../components/PlayBox";
import PlaylistDetail from "../components/PlaylistDetail";

const Add = () => {
  return (
    <PlayBox
      left={null}
      right={
        <>
          <PlaylistDetail />
          <AddPlaylistForm />
        </>
      }
    />
  );
};

export default Add;

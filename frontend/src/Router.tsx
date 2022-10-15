import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Login from "./routes/login";
import Me from "./routes/me";
import { Playlist, PlaylistAdd, PlaylistSearch } from "./routes/playlist";
import { SongList } from "./routes/song";

const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/login/kakao" element={<Login loginType={"kakao"}/>} />
        <Route path="/login/google" element={<Login loginType={"google"}/>} />
        <Route path="/playlist" element={<Playlist />} />
        <Route path="/playlist/add" element={<PlaylistAdd />} />
        <Route path="/playlist/search" element={<PlaylistSearch />} />
        <Route path="/songs" element={<SongList />} />
        <Route path="/me" element={<Me />} />
        <Route path="*" element={<Navigate replace to="/playlist" />} />
      </Routes>
    </BrowserRouter>
  );
};
export default Router;

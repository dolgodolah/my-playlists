import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import AddPlaylist from "./routes/addPlaylist";
import Home from "./routes/home";
import Login from "./routes/login";
import Playlist from "./routes/playlist";
import Search from "./routes/search";

const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Home />} />
        <Route path="/playlist" element={<Playlist />} />
        <Route path="/playlist/add" element={<AddPlaylist />} />
        <Route path="/search" element={<Search />} />
        <Route path="*" element={<Navigate replace to="/" />} />
      </Routes>
    </BrowserRouter>
  );
};
export default Router;

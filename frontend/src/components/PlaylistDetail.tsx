import EditBox from "./EditBox";

const PlaylistDetail = () => {
  return (
    <div className="title__container">
      <EditBox />
      <span className="page__span--title">타이틀</span>
      <span className="page__span--sub">부제목</span>
    </div>
  );
};

export default PlaylistDetail;

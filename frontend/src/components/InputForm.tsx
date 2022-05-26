import { useCallback, useState } from "react";

export const AddPlaylistForm = () => {
  const [playlistName, setPlaylistName] = useState("");
  const [description, setDescription] = useState("");
  const [visibility, setVisibility] = useState("public");

  const changePlaylistName = useCallback((e) => {
    setPlaylistName(e.target.value);
  }, []);

  const changeIntroduction = useCallback((e) => {
    setDescription(e.target.value);
  }, []);

  const changeVisibility = useCallback((e) => {
    setVisibility(e.target.value);
  }, []);

  const onSubmit = () => {
    console.log(playlistName, description, visibility);
    alert(`${playlistName}플레이리스트 생성이 완료되었습니다.`);
  };

  return (
    <div className="form__container">
      <form onSubmit={onSubmit}>
        <div className="name-input__container">
          <input
            value={playlistName}
            onChange={changePlaylistName}
            type="text"
            placeholder="플레이리스트 이름"
            minLength={2}
            maxLength={20}
            className="name__input"
            required
          />
        </div>
        <div className="description-textarea__container">
          <textarea
            className="description__textarea"
            placeholder="플레이리스트 소개"
            value={description}
            onChange={changeIntroduction}
            maxLength={150}
            required
          />
        </div>
        <div className="visibility-input__container">
          <div>
            <input
              type="radio"
              name="visibility"
              value="public"
              id="public"
              checked={visibility === "public"}
              onChange={changeVisibility}
            />
            <label htmlFor="public">공개</label>
          </div>
          <div>
            <input
              type="radio"
              name="visibility"
              value="private"
              id="private"
              checked={visibility === "private"}
              onChange={changeVisibility}
            />
            <label htmlFor="private">비공개</label>
          </div>
        </div>
        <div className="button__container--inputForm">
          <button type="submit">생성</button>
        </div>
      </form>
    </div>
  );
};

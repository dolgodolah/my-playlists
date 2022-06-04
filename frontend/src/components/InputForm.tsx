import React, { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export const AddPlaylistForm = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [visibility, setVisibility] = useState(true);

  const navigate = useNavigate();

  const changeTitle = useCallback((e) => {
    setTitle(e.target.value);
  }, []);

  const changeIntroduction = useCallback((e) => {
    setDescription(e.target.value);
  }, []);

  const changeVisibility = useCallback((e) => {
    setVisibility(e.target.value);
  }, []);

  const pushPreviousPage = () => {
    navigate(-1);
  };

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("test")
    axios.post("/playlist", {
      title,
      description,
      visibility
    }).then((res) => {
      navigate("/")
    }).catch((error) => {
      console.log(error)
    })
  };

  return (
    <div className="form__container">
      <form onSubmit={onSubmit}>
        <div className="name-input__container">
          <input
            value={title}
            onChange={changeTitle}
            type="text"
            placeholder="플레이리스트 이름"
            minLength={2}
            maxLength={20}
            className="name__input"
            required
          />
        </div>
        <div className="description-textarea__container--input">
          <textarea
            className="description__textarea--input"
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
              checked={visibility === true}
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
              checked={visibility === false}
              onChange={changeVisibility}
            />
            <label htmlFor="private">비공개</label>
          </div>
        </div>
        <div className="button__container--inputForm">
          <button type="button" onClick={pushPreviousPage}>
            취소
          </button>
          <button type="submit">생성</button>
        </div>
      </form>
    </div>
  );
};

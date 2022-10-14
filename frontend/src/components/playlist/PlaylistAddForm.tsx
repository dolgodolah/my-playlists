import React, { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";
import classNames from "classnames";

const PlaylistAddForm = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [visibility, setVisibility] = useState("true");
  const [errorMessage, setErrorMessage] = useState("");
  const [error, setError] = useState(false);

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
    axios.post("/playlist", {
      title,
      description,
      visibility
    }).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          navigate("/")
          break;
        case StatusCode.BAD_REQUEST:
          setErrorMessage(response.message)
          setError(true)
          break;
        default:
          alertError(response)
          break;
      }
    })
  };

  return (
    <div className="form__container">
      <form onSubmit={onSubmit}>
        <div className="name-input__container">
          <p className="input-title__p">플레이리스트 타이틀</p>
          <input
            value={title}
            onChange={changeTitle}
            type="text"
            placeholder="플레이리스트 타이틀을 입력해 주세요."
            minLength={2}
            maxLength={50}
            className={classNames("name__input", {
              "input-error": error
            })}
            required
          />
          {error &&
          <span className="warning-message__span">
            {errorMessage}
          </span>
          }
        </div>
        <div className="description-textarea__container--input">
          <p className="input-title__p">플레이리스트 소개</p>
          <textarea
            className="description__textarea--input"
            placeholder="플레이리스트 소개를 입력해 주세요."
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
              value="true"
              id="public"
              checked={visibility === "true"}
              onChange={changeVisibility}
            />
            <label htmlFor="public">공개</label>
          </div>
          <div>
            <input
              type="radio"
              name="visibility"
              value="false"
              id="private"
              checked={visibility === "false"}
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

export default PlaylistAddForm;
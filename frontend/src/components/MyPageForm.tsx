import axios from "axios";
import classNames from "classnames";
import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import alertError from "../shared/Error";
import StatusCode from "../shared/StatusCode";

const MyPageForm = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [nickname, setNickname] = useState("");
  const [error, setError] = useState(false);

  const navigate = useNavigate();

  const changeNickname = useCallback((e) => {
    setNickname(e.target.value);
  }, []);

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    axios
      .post("/my-info", {
        name,
        email,
        nickname,
      })
      .then((res) => {
        const response = res.data;
        if (response.nickname) {
          navigate("/");
        } else {
          alertError(response.body);
        }
        // switch (response.statusCode) {
        //   case StatusCode.OK:
        //     navigate("/");
        //     break;
        //   case StatusCode.INVALID_NICKNAME:
        //     alertError(response.body.message);
        //     setError(true);
        //     break;
        //   default:
        //     alertError(response.body);
        //     break;
        // }
      });
  };

  const pushPreviousPage = () => {
    navigate(-1);
  };

  useEffect(() => {
    axios.get("/my-info").then((res) => {
      const response = res.data;
      if (response.name) {
        setName(response.name);
        setEmail(response.email);
        setNickname(response.nickname);
      } else {
        alertError(response.body);
      }
      // switch (response.statusCode) {
      //   case StatusCode.OK:
      //     setName(response.name);
      //     setEmail(response.email);
      //     break;
      //   default:
      //     alertError(response.body);
      //     break;
      // }
    });
  }, []);

  return (
    <div className="form__container">
      <form onSubmit={onSubmit}>
        <div className="user-name-input__container">
          <input
            value={name}
            type="text"
            className="user-name__input readonly"
            readOnly
          />
        </div>
        <div className="email-input__container">
          <input
            value={email}
            type="text"
            className="email__input readonly"
            readOnly
          />
        </div>
        <div className="nickname-input__container">
          <input
            value={nickname}
            onChange={changeNickname}
            type="text"
            placeholder="플레이리스트 이름"
            minLength={2}
            maxLength={20}
            className={classNames("nickname__input", {
              "input-error": error,
            })}
            required
          />
          {error ? (
            <span className="warning-message__span">
              잘못된 닉네임 입니다. 다시 설정해 주세요.
            </span>
          ) : (
            <span className="warning-message__span">
              닉네임을 설정하지 않으면 연동시킨 계정의 이름이 사용되므로
              유의해주세요.
            </span>
          )}
        </div>
        <div className="button__container--inputForm">
          <button type="button" onClick={pushPreviousPage}>
            취소
          </button>
          <button type="submit">수정</button>
        </div>
      </form>
    </div>
  );
};

export default MyPageForm;

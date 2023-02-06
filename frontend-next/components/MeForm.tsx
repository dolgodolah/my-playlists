import React, {useCallback, useState} from "react";
import classNames from "classnames";
import useClient from "./hooks/useClient";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";

interface MeFormProps {
  context: Record<string, any>
}

export const MeForm = ({ context }: MeFormProps) => {
  const client = useClient()
  const [name] = useState(context.name);
  const [email] = useState(context.email);
  const [nickname, setNickname] = useState(context.nickname);
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const changeNickname = useCallback((e: any) => {
    setNickname(e.target.value);
  }, []);

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    const res = await client.post("/me", {
      name,
      email,
      nickname
    })

    switch (res.statusCode) {
      case StatusCode.OK:
        location.href = "/playlists"
        break
      case StatusCode.BAD_REQUEST:
        setErrorMessage(res.message)
        setError(true)
        break;
      default:
        alertError(res)
        break;
    }
  }



  return (
    <div className="form__container">
      <form onSubmit={onSubmit}>
        <div className="user-name-input__container">
          <p className="input-title__p">이름</p>
          <input
            value={name}
            type="text"
            className="user-name__input readonly"
            readOnly
          />
        </div>
        <div className="email-input__container">
          <p className="input-title__p">이메일</p>
          <input
            value={email}
            type="text"
            className="email__input readonly"
            readOnly
          />
        </div>
        <div className="nickname-input__container">
          <p className="input-title__p">닉네임</p>
          <input
            value={nickname}
            onChange={changeNickname}
            type="text"
            placeholder="닉네임 입력"
            minLength={2}
            className={classNames("nickname__input", {
              "input-error": error,
            })}
            required
          />
          {error ? (
            <span className="warning-message__span">{errorMessage}</span>
          ) : (
            <span className="warning-message__span">
              닉네임을 설정하지 않으면 연동시킨 계정의 이름이 사용되므로
              유의해주세요.
            </span>
          )}
        </div>
        <div className="button__container--inputForm">
          <button type="button" onClick={() => history.back()}>
            취소
          </button>
          <button type="submit">수정</button>
        </div>
      </form>
    </div>
  )
}
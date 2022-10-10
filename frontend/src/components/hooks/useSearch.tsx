import {useSearchParams} from "react-router-dom";
import React, {useCallback, useState} from "react";

export const useSearch = () => {
  const [params] = useSearchParams();
  const [keyword, setKeyword] = useState(params.get("keyword") || "");

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/playlist/search?keyword=" + keyword;
    }
  };

  return { keyword: keyword, setKeyword: onChangeKeyword, search: onPressEnter };
}

export const useAsyncSearch = (callback: (keyword: string) => void) => {
  const [params] = useSearchParams();
  const [keyword, setKeyword] = useState(params.get("keyword") || "");

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
    callback(e.target.value)
  }, []);

  return { keyword: keyword, setKeyword: onChangeKeyword }
}

import React, {useCallback, useState} from "react";

interface useSearchProps {
  callback: () => void
}
export const useSearch = ({ callback }: useSearchProps) => {
  const [keyword, setKeyword] = useState("");

  const onChangeKeyword = useCallback((e: any) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      callback()
    }
  };

  return { keyword, setKeyword: onChangeKeyword, onPressEnter };
}

export const useAsyncSearch = (callback: (keyword: string) => void) => {
  const [keyword, setKeyword] = useState("");

  const onChangeKeyword = useCallback((e: any) => {
    setKeyword(e.target.value);
    callback(e.target.value)
  }, []);

  return { keyword: keyword, setKeyword: onChangeKeyword }
}

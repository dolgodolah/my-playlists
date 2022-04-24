import React, {useCallback, useState} from "react";

const onSubmit = (e: React.FormEvent) => {
  e.preventDefault()
  console.log("유튜브 노래 검색 api 호출")
  // 유튜브 노래 검색 api 호출
}

const SearchSongs = () => {
  const [keyword, setKeyword] = useState('')
  const onChange = useCallback((e) => {
    setKeyword(e.target.value)
  }, []);

  return (
    <div className="search-song__container">
      <form onSubmit={onSubmit}>
        <input
          placeholder="노래 검색"
          name="keyword"
          className="search-song__input"
          value={keyword}
          onChange={onChange}
        />
      </form>
    </div>
  );
};

export default SearchSongs;

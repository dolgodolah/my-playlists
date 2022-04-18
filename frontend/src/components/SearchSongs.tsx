const SearchSongs = () => {
  return (
    <div className="search-song__container">
      <form method="get" action="/playlist/search">
        <input
          type="text"
          placeholder="노래 검색"
          name="keyword"
          className="search-song__input"
        />
      </form>
    </div>
  );
};

export default SearchSongs;

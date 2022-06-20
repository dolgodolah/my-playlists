import HeaderLogo from "./HeaderLogo";

interface HeaderProps {
  keyword: string;
  onChangeKeyword: (e: any) => void;
  onPressEnter: (e: React.KeyboardEvent) => void;
}

const PlaylistsHeader = ({ keyword, onChangeKeyword, onPressEnter }: HeaderProps) => {
  return (
    <>
      <HeaderLogo />
      <div className="search__container">
        <input
          type="text"
          placeholder="플레이리스트 검색"
          className="search__input--header"
          value={keyword}
          onChange={onChangeKeyword}
          onKeyPress={onPressEnter}
        />
      </div>
    </>
  );
};
export default PlaylistsHeader;

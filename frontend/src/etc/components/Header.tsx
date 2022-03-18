import { Link } from "react-router-dom";

const Header = () => {
  return (
    <>
      <div className="header__container">
        <Link to="/" className="logo__container">
          내플리스
        </Link>
        {/* search box */}
      </div>
    </>
  );
};

export default Header;

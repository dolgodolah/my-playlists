import {LoginMenu} from "../components/Login"
import withPageContext from "../components/hoc/withPageContext"
import {isMobile} from "react-device-detect";

const LoginPage = () => {

  if (isMobile) {
    document.body.style.width = "100%";
  }

  return (
    <LoginMenu />
  )
};

export default withPageContext()(LoginPage);

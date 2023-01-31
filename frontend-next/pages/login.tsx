import {LoginMenu} from "../components/Login"
import withPageContext from "../components/hoc/withPageContext"

const LoginPage = () => {
  return (
    <LoginMenu />
  )
};

export default withPageContext()(LoginPage);

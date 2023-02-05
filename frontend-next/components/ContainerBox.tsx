import React, {ReactNode} from "react";
import ProfileMenu from "./ProfileMenu";

interface ContainerBoxProps {
  left: ReactNode;
  right: ReactNode;
}

const ContainerBox = ({ left, right }: ContainerBoxProps) => {
  return (
    <div className="container-box">
      <div className="container-box--left">
        {left}
        <ProfileMenu />
      </div>
      <div className="container-box--right">
        {right}
      </div>

    </div>
  );
};

export default ContainerBox;

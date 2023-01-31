import React from "react";
import {NextPage} from "next";

export const PageContext = React.createContext<Record<string, any>>({})

export default () => (WrappedPage: NextPage<any>) => {
  const Wrapper: NextPage<any, any> = ({ pageContext } : Record<string, any>) => {
    return (
      <PageContext.Provider value={ pageContext }>
        <WrappedPage />
      </PageContext.Provider>
    )
  }

  return Wrapper
}
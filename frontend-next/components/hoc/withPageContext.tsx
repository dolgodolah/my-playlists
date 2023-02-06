import React from "react";
import {NextPage} from "next";

export const PageContext = React.createContext<Record<string, any>>({})

export default () => (WrappedPage: NextPage<any>) => {
  const Wrapper: NextPage<any, any> = ({ pageContext } : Record<string, any>) => {
    return (
      <SafeHydrate>
        <PageContext.Provider value={ pageContext }>
          <WrappedPage />
        </PageContext.Provider>
      </SafeHydrate>
    )
  }

  return Wrapper
}

function SafeHydrate ({ children }: {children: any}) {
  return (
    <>
      {typeof window === 'undefined' ? null : children}
    </>
  )
}
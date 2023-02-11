import Document, { Html, Head, Main, NextScript } from 'next/document'
import { isMobile } from "react-device-detect"

class MyDocument extends Document {
  render() {
    return (
      <Html>
        <Head>
          <meta content="website" property="og:type" />
          <meta content="내플리스" property="og:title" />
          <meta content="/images/myplaylsits.png" property="og:image" />
        </Head>
        <body>
          <Main />
          <NextScript />
        </body>
      </Html>
    )
  }
}

export default MyDocument
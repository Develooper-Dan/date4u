import { Html, Head, Main, NextScript } from 'next/document'
import Script from 'next/script'

export default function Document() {
    return (
        <Html>
            <Head>
                <title>Date4U</title>
                <meta charSet="UTF-8"/>
                <link rel="icon" href="/favicon.ico" />
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@5.1.0/dist/quartz/bootstrap.min.css" />
            </Head>
                <body>
                    <Main />
                    <NextScript />
                    <Script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></Script>
                </body>
        </Html>
    )
}

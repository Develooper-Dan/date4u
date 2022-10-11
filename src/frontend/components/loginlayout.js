import LoginHeader from './loginheader'
import Footer from './footer'

export default function LoginLayout({ children }) {
    return (
        <div className="container">
            <LoginHeader />
            <main>{children}</main>
            <Footer />
        </div>
    )
}
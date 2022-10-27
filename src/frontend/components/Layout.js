import Navbar from './Navbar'
import Footer from './Footer'

export default function Layout({ children }) {
    return (
        <div className="container main">
            <Navbar />
            <main>{children}</main>
            <Footer />
        </div>
    )
}
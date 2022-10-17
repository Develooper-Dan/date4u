import Loginheader from './Loginheader'
import Footer from './Footer'

export default function Loginlayout({ children }) {
    return (
        <div className="container">
            <Loginheader />
            <main>{children}</main>
            <Footer />
        </div>
    )
}
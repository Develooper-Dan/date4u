import Loginheader from './Loginheader'
import Footer from './Footer'

export default function Loginlayout({ children }) {
    return (
        <div className="container main">
            <Loginheader />
            <main>
                <div className="d-flex flex-column align-items-center mt-2">{children}
                </div>
            </main>
            <Footer />
        </div>
    )
}
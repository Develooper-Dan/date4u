import Link from "next/link";

export default function Navbar(){
    return(
        <nav className="navbar navbar-expand-lg bg-dark navbar-dark">
            <div className="container-fluid">
                <div className="navbar-nav">
                    <Link href="/Users/Public"><a className="navbar-brand">Date4u</a></Link>
                    <Link href="/Users/Public"><a className="nav-item nav-link">Home</a></Link>
                    <Link href="/profile/[id]/edit"><a className="nav-item nav-link">Profile</a></Link>
                    <Link href="/search"><a className="nav-item nav-link">Search</a></Link>
                </div>
            </div>
        </nav>
    )
}

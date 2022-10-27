import Link from "next/link";
import Cookies from 'js-cookie'
import useUser from "../hooks/useUser";


export default function Navbar(){
   const token = Cookies.get('token');
   const { user } = useUser(token);

    if(user){
        return(
            <nav className="navbar navbar-expand-lg bg-dark navbar-dark">
                <div className="container-fluid">
                    <div className="navbar-nav w-100">
                        <Link href="/"><a className="navbar-brand">Date4u</a></Link>
                        <Link href="/"><a className="nav-item nav-link">Home</a></Link>
                        <Link href= {`/profile/${user.profileID}`}><a className="nav-item nav-link">Profile</a></Link>
                        <Link href="/search"><a className="nav-item nav-link">Search</a></Link>
                        <Link href="/login" replace><a className="nav-item nav-link ms-auto" onClick={()=> Cookies.remove("token")}>Logout</a></Link>
                    </div>
                </div>
            </nav>
        )
    }
}

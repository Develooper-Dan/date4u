import Cookies from 'js-cookie'
import useUser from "../hooks/useUser";
import {useRouter} from "next/router";
import {useEffect} from "react";


export default function AuthProvider({children}) {

    const router = useRouter();
    const token = Cookies.get("token");

    const { user, error , isLoading} = useUser(token);

    useEffect(() => {
        if (router.pathname !== "/login" && router.pathname !== "/signup") {
            if(error && error.status  === 401){
                router.push("/login");
            }
        }
    }, [user, error]);

    return (
        <>
            {children}
        </>
    )

}


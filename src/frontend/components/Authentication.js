import { useSession } from "next-auth/react";
import { useRouter } from 'next/router';
import { useEffect } from "react";


/*export const getServerSideProps = (context) => {
    const { data: session, status } = useSession({required: true});
    console.log(status)
    if (status !== "authenticated") {
        return {
            props: {},
            redirect: {destination: "/login"}
        }
    }
}*/
export default function Authentication({ children }) {
    const { data: session, status } = useSession({required: true});
    console.log(session)
    return (
        <>
            {children}
        </>
    )
}





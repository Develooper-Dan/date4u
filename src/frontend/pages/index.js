import Layout from "../components/layout";
import {useSession} from "next-auth/react";

export default function Home() {
    const { data: session, status } = useSession()
    console.log(session);

    if (status === "authenticated") {
        return (
            <Layout>
                <div className="jumbotron">
                    <h1 className="display-4">Welcome {session.user.email}</h1>
                    <p>There's a total of ... profiles in the database</p>
                </div>
            </Layout>
        )
    }
}


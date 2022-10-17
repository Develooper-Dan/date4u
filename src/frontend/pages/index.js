import Layout from "../components/Layout";
import {useSession} from "next-auth/react";
import Authentication from "../components/Authentication";

export default function Home() {
  /*  const { data: session, status } = useSession({required: true});
    console.log(session);
    console.log(status);*/

    return (
    <Authentication>
            <Layout>
                <div className="jumbotron">
                    <h1 className="display-4">Welcome {/*{session.user.email}*/}</h1>
                    <p>There's a total of ... profiles in the database</p>
                </div>
            </Layout>
    </Authentication>
        )

}


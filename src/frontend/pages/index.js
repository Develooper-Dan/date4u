import Layout from "../components/Layout";
import useUser from "../hooks/useUser";
import Cookies from "js-cookie";
import useProfile from "../hooks/useProfile";
import axios from "axios";
import fetcher from "../hooks/fetcher";
import useSWR from "swr";



export default function Home() {
    const token = Cookies.get('token');
    const { user } = useUser(token);
    const { profile, error } = useProfile(user?.profileID, token);

    const {
        data: total
    } = useSWR( [`${process.env.NEXT_PUBLIC_API_URL}/stat/total` , token] , fetcher, {
        revalidateIfStale: false,
    })

    if(profile) {
        return (
            <Layout>
                <div className="jumbotron">
                    <h1 className="display-4">Welcome {profile.nickname}</h1>
                    <p>There's a total of {total - 1 || "..."} other unicorns waiting for you!</p>
                </div>
            </Layout>
        )
    }


}


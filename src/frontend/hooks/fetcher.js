import axios from "axios";

export default function fetcher(url, jwt) {
    return axios.get(url,
        {headers: {"Accept": "application/json", "Authorization": "Bearer " + jwt}})
        .then((res) => res.data )
        .catch((err) => {
            const error = new Error(err.message);
            if(err.response) {
                error.status = err.response.status ;
            }
            throw error
        })
}
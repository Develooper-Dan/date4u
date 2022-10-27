import Cookies from "js-cookie";
import usePhoto from "../hooks/usePhoto";

export default function Photo({filename}) {
    const token = Cookies.get("token");
    const {base64PhotoUrl, error} = usePhoto(filename, token);

    if(base64PhotoUrl) {
        return <img src={`data:image/jpg;base64,${base64PhotoUrl}`} className="d-block w-100" alt="profile photo"/>
    }

}
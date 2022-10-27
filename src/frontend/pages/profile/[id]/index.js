import Layout from "../../../components/Layout";
import {useRouter} from "next/router";
import useProfile from "../../../hooks/useProfile";
import {useEffect} from "react";
import Cookies from "js-cookie";
import useUser from "../../../hooks/useUser";
import Link from "next/link";
import ProfilePhotos from "../../../components/ProfilePhotos";


export default function Profile(){
    const genderTranslation = { 0: "DIV", 1:"FEE", 2: "MA"};
    const token = Cookies.get("token");
    const router = useRouter();
    const  id  = parseInt(router.query.id);
    const { profile, error } = useProfile(id, token);
    const { user } = useUser(token);
    useEffect(() => {
        if(error && error.status  === 404 ){
            window.location.replace("/notfound");
        }
    }, [profile, error])


    if(profile) {
        return (
                <Layout>
                    <div className="jumbotron w-100 mt-2 mb-3">
                        <h1 className="display-4">{profile.nickname}'s profile</h1>
                    </div>
                    <section className = "row mb-3">
                        <div className="col-lg-4">
                            <ProfilePhotos photos={profile?.photos} isProfileOwner={user.profileID === id} />
                        </div>
                        <div className="col-lg-8">
                            <div className="mb-3">
                                <label htmlFor="nickname" className="form-label">Nickname</label>
                                <input type="text" value={profile.nickname} id="nickname"
                                       className="form-control" readOnly />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="hornlength" className="form-label">Hornlength</label>
                                <input type="text" defaultValue={profile?.hornlength} id="hornlength"
                                       className="form-control" readOnly/>
                            </div>

                            <div className="mb-3">
                                <label htmlFor="gender" className="form-label">Gender</label>
                                <p className="form-control" readOnly>{genderTranslation[profile.gender]}</p>
                            </div>

                            <div className="mb-3" >
                                <label htmlFor="attractedToGender" className="form-label">Attracted to gender</label>
                                <p className="form-control" readOnly>{genderTranslation[profile.attractedToGender] || "N/A"}</p>
                            </div>

                            <div className="mb-3">
                                <label htmlFor="birthdate" className="form-label">Birthdate</label>
                                <input type="date" defaultValue={profile?.birthdate} id="birthdateInput"
                                       className="form-control" readOnly/>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="description" className="form-label">Description</label>
                                <textarea defaultValue={profile?.description} id="description"
                                          className="form-control" readOnly/>
                            </div>
                            { (user.profileID === id) ?
                                <Link href={`/profile/${id}/edit`}><button className="btn btn-primary">Edit profile</button></Link>
                                : null
                            }
                        </div>
                    </section>
                </Layout>

        )
    }
}

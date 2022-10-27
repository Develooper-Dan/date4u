import Cookies from "js-cookie";
import axios from "axios";
import Layout from "../../../components/Layout";
import {useRouter} from "next/router";
import {useForm} from "react-hook-form";
import useProfile from "../../../hooks/useProfile";
import useUser from "../../../hooks/useUser";
import {useEffect, useState} from "react";
import Link from "next/link";
import ProfilePhotos from "../../../components/ProfilePhotos";


export default function EditProfile(){
    const token = Cookies.get("token")
    const router = useRouter();
    const id  = parseInt(router.query.id);
    const { profile, error, mutate } = useProfile(id, token);
    const { user } = useUser(token);
    const [validationError, setValidationError] = useState("");
    const { register, handleSubmit, formState: { errors } } = useForm();

    useEffect(() => {
        if(error && error.status  === 404 ){
            window.location.replace("/notfound");
        }
        if(user && user.profileID !== id){
            window.location.replace("/profile/" + id)
        }
    }, [profile, error])


    const formSubmit = (formData) => {
        axios.post(`${process.env.NEXT_PUBLIC_API_URL}/profiles/${id}/save`, formData,
            {headers: {"Content-Type": "application/json", "Authorization": "Bearer " + token}})
            .then(res => {
                if (res.status < 400 ) {
                    mutate({...formData, photos: profile.photos})
                    router.push("/profile/" + id);
            }
        }).catch(err => {
            if (err) {
                setValidationError(err.response.data)
            }
        })
    }

    if(profile){
        return (
            <Layout>
                <div className="jumbotron w-100 mb-3">
                    <h1 className="display-4">{profile.nickname}'s profile</h1>
                </div>
                <section className = "row">
                    <div className="col-lg-4">
                        <ProfilePhotos photos={profile?.photos} isProfileOwner={user.profileID === id} />

                    </div>
                    <div className="col-lg-8">
                        <form onSubmit= {handleSubmit(formSubmit)} >
                            <input type="text" id="id" {...register("id")} hidden readOnly value={profile.id }/>
                            <div className="mb-3">
                                <label htmlFor="nickname" className="form-label">Nickname</label>
                                <input type="text" className="form-control"
                                       defaultValue={profile.nickname}
                                       {...register("nickname", {
                                           required: "Nickname is required",
                                           maxLength: {value: 32, message: "only 32 characters allowed"}
                                       })} />
                                {errors.nickname &&
                                    <p className="invalid-feedback" role="alert">{errors.nickname?.message}</p>}
                                { validationError && validationError.nickname &&
                                    <p className="invalid-feedback" role="alert">{validationError.nickname}</p> }
                            </div>
                            <div className="mb-3">
                                <label htmlFor="hornlength" className="form-label">Hornlength</label>
                                <input type="number" className="form-control"
                                       defaultValue={profile.hornlength}
                                       {...register("hornlength", {
                                           required: "Hornlength is required"
                                       })} />
                                {errors.hornlength && <p className="invalid-feedback" role="alert">{errors.hornlength?.message}</p>}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="gender" className="form-label">Gender</label>
                                <select className="form-control" {...register("gender", {required: "Gender is required", valueAsNumber: true})} >
                                    <option selected={profile.gender === 0} value="0">DIV</option>
                                    <option selected={profile.gender === 1} value="1">FEE</option>
                                    <option selected={profile.gender === 2} value="2">MA</option>
                                </select>
                            </div>

                            <div className="mb-3">
                                <label htmlFor="attractedToGender" className="form-label">Attracted To</label>
                                <select className="form-control" {...register("attractedToGender", {valueAsNumber: true})} >
                                    <option selected={profile.attractedToGender === 0} value="0" >DIV</option>
                                    <option selected={profile.attractedToGender === 1}  value="1" >FEE</option>
                                    <option selected={profile.attractedToGender === 2}  value="2" >MA</option>
                                    <option selected={profile.attractedToGender === null}  value={null} >N/A</option>
                                </select>
                            </div>

                            <div className="mb-3">
                                <label htmlFor="birthdate" className="form-label">Birthdate</label>
                                <input type="date" className="form-control"
                                       defaultValue={profile.birthdate}
                                    {...register("birthdate", {required: "Birthdate is required"})} />
                                {errors.birthdate && <p className="invalid-feedback" role="alert">{errors.birthdate?.message}</p>}
                            </div>
                            <div className="mb-3">
                                <label htmlFor="description" className="form-label">Descrption</label>
                                <input type="textarea" className="form-control"
                                       defaultValue={profile.description}
                                       {...register("description", {maxLength: {value: 2048, message: "Only 2048 characters allowed"}})} />
                                {errors.description && <p className="invalid-feedback" role="alert">{errors.description?.message}</p>}
                            </div>
                                <button type="submit" className="btn btn-primary">Save changes</button>
                                <Link href={`/profile/${id}`}><button className="btn btn-primary">Cancel</button></Link>
                        </form>
                    </div>
                </section>
            </Layout>
        )
    }

}

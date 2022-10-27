import ProfileCarousel from "./ProfileCarousel";
import axios from "axios";
import {useForm} from "react-hook-form";
import {useRouter} from "next/router";
import Cookies from "js-cookie";
import Image from "next/image";
import placeholder from '../public/placeholder.jpg';
import {useState} from "react";


export default function ProfilePhotos(props){
    const router = useRouter();
    const id  = parseInt(router.query.id);
    const token = Cookies.get("token")
    const { register, handleSubmit, formState: { errors } } = useForm();
    const noPhoto = props.photos.length === 0;
/*    const [shouldUpdate, setShouldUpdate ] = useState(false);*/

    const uploadPhoto = ({photo}) => {
        const formData = new FormData();
        formData.append('file', photo[0]);
        formData.append('fileName', photo[0].name);
        axios.post(`${process.env.NEXT_PUBLIC_API_URL}/profiles/${id}/photos`, formData,
            {headers: {"Content-Type": "multipart/form-data", "Authorization": "Bearer " + token}})
            .then(res => {
                if (res.status < 400 ) {
   /*                 setShouldUpdate(true);*/
                    router.reload();
                }
            }).catch(err => {
            if (err && err.response) {
                console.error(err.message)
            }
        })
    }
    const returnPhotoUploadForm = () => {
        return(
            <div className="mt-3">
                <form onSubmit={handleSubmit(uploadPhoto)}>
                    <div>
                        <label htmlFor="photo" className="form-label">Add a new photo</label>
                        <input
                            type="file"
                            id="photo"
                            className={"form-control mb-2" + (errors.photo ? " is-invalid" : "")}
                            {...register("photo", {required: "Please select a photo"})}
                            accept="image/png, image/jpeg" />
                        <button  type="submit" className="btn btn-primary">
                            Upload
                        </button>
                        {errors.photo && <p className="invalid-feedback" role="alert">{errors.photo?.message}</p>}
                    </div>
                </form>
            {/* <form onSubmit={handleSubmit(uploadPhoto)}>
                    <button className="btn btn-primary">Change profile photo
                    </button>
                </form>*/}
            </div>

        )
    }

    return (
        <>
            {noPhoto ?
                <Image src={placeholder} alt="placeholder" />
                :
                <ProfileCarousel photos={props.photos}/>
            }
            {props.isProfileOwner ? returnPhotoUploadForm() : null}
        </>

    )
}
import Loginlayout from "../components/Loginlayout";
import Link from "next/link";
import {useForm} from "react-hook-form";
import {useState} from "react";
import {useRouter} from "next/router";
import axios from "axios";
import Cookies from "js-cookie";


export default function Signup() {

    const { register, handleSubmit, formState: { errors } } = useForm();
    const [registrationError, setRegistrationError] = useState("");
    const router = useRouter();

    const signUp = async (credentials)=> {
        console.log(errors)
        axios.post(`${process.env.NEXT_PUBLIC_API_URL}/auth/signup`, credentials,
            {headers: {"Content-Type": "application/json"}})
            .then(res => {
                if (res.status < 400 && res.data) {
                    Cookies.set("token", res.data.jwt, {path: "/"});
                    router.replace("/");
                }
            }).catch(err => {
            if (err) {
                setRegistrationError(err.response.data)
            }
        })
    }

    return (
        <Loginlayout>
            <div className="jumbotron mb-3">
                <h1 className="display-4">Sign up</h1>
            </div>
            <div className="login-container mb-2">
                <form onSubmit={handleSubmit(signUp)} className="mb-2" id="signup">
                    <div className="mb-2">
                        <label htmlFor="email" className="form-label">Email</label>
                        <input type="email" className={"form-control" + (errors.email ? " is-invalid" : "")}
                               placeholder="Yourfantasy@freeunico.rn"{...register("email", {required: "Email Address is required"})} />
                        {errors.email &&
                            <p className=" invalid-feedback" role="alert">{errors.email?.message}</p>}
                        { registrationError && registrationError.email &&
                            <p className="invalid-feedback" role="alert">{registrationError.email}</p> }
                    </div>
                    <div className="mb-2">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input type="password" className={"form-control" + (errors.password ? " is-invalid" : "")}
                               placeholder="yoursecret123"{...register("password", {required: "Password is required"})} />
                        {errors.password && <p className="invalid-feedback" role="alert">{errors.password?.message}</p>}
                    </div>
                    <div className="mb-2">
                        <label htmlFor="nickname" className="form-label">Nickname</label>
                        <input type="text" className={"form-control" + (errors.nickname ? " is-invalid" : "")}
                               placeholder="HornyDevil"
                               {...register("nickname", {
                                   required: "Nickname is required",
                                   maxLength: {value: 32, message: "only 32 characters allowed"}
                               })} />
                        {errors.nickname &&
                            <p className="invalid-feedback" role="alert">{errors.nickname?.message}</p>}
                        { registrationError && registrationError.nickname &&
                            <p className="invalid-feedback" role="alert">{registrationError.nickname}</p> }
                    </div>
                    <div className="mb-2">
                        <label htmlFor="birthdate" className="form-label">Birthdate</label>
                        <input type="date" className={"form-control" + (errors.birthdate ? " is-invalid" : "")}
                              {...register("birthdate", {required: "Birthdate is required"})} />
                        {errors.birthdate && <p className="invalid-feedback" role="alert">{errors.birthdate?.message}</p>}
                    </div>
                    <fieldset form="signup" className="d-flex justify-content-between mb-2">
                        <div className="d-flex flex-column w-25">
                            <label htmlFor="gender" className="form-label">Gender</label>
                            <select id="gender" {...register("gender", { valueAsNumber: true})} >
                                <option  value="0" >DIV</option>
                                <option  value="1" >FEE</option>
                                <option  value="2" >MA</option>
                            </select>
                        </div>
                        <div className="d-flex flex-column w-25">
                            <label htmlFor="attractedToGender" className="form-label">Attracted To</label>
                            <select {...register("attractedToGender", {valueAsNumber: true})} >
                                <option  value="0" >DIV</option>
                                <option  value="1" >FEE</option>
                                <option  value="2" >MA</option>
                                <option  value={null} >N/A</option>
                            </select>
                        </div>
                    </fieldset>
                    <div className="mb-2">
                        <label htmlFor="hornlength" className="form-label">Hornlength</label>
                        <input type="number" className={"form-control" + (errors.hornlength ? " is-invalid" : "")}
                               placeholder="0"{...register("hornlength", {
                                required: "Hornlength is required",
                                min: {value: 0, message: "Hornlength must be positive"},
                                max: {value: 40, message: "Maximum hornlength is 40"}}
                        )} />
                        {errors.hornlength && <p className="invalid-feedback" role="alert">{errors.hornlength?.message}</p>}
                    </div>
                    <div className="mb-2">
                        <label htmlFor="description" className="form-label">Description</label>
                        <textarea className={"form-control description" + (errors.description ? " is-invalid" : "")}
                               placeholder="Tell us sth about yourself..."
                               {...register("description", {maxLength: {value: 2048, message: "Only 2048 characters allowed"}})} />
                        {errors.description && <p className="invalid-feedback" role="alert">{errors.description?.message}</p>}
                    </div>

                    <div className="d-flex justify-content-center">
                        <button className="btn btn-primary" type="submit">Sign Up</button>
                    </div>
                </form>

                <p className="text-center">Already have a user account? <Link href='/login'>Click here</Link> to log in!</p>
            </div>
        </Loginlayout>
    )
}
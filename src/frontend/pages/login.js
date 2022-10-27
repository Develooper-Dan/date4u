import Link from "next/link";
import Loginlayout from "../components/Loginlayout";
import { useForm } from "react-hook-form";
import {useRouter} from "next/router";
import Cookies from 'js-cookie'
import axios from "axios";
import {useState} from "react";



export default function Login(){
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [authError, setAuthError] = useState("");
    const router = useRouter();

    const login = async (credentials)=> {
        axios.post(`${process.env.NEXT_PUBLIC_API_URL}/auth/login`, credentials,
            {headers: {"Content-Type": "application/json"}})
            .then(res => {
                if (res.status < 400 && res.data) {
                    Cookies.set("token", res.data.jwt, {path: "/"});
                    router.push("/");
                }
            }).catch(err => {
            if (err.response.status === 401) {
                setAuthError("Invalid username or password")
            } else {
                setAuthError("An error occurred processing your data")
            }
        })
    }

    return (
        <Loginlayout>
                <div className="jumbotron mb-3">
                    <h1 className="display-4">Sign in</h1>
                </div>
                <div className="login-container mb-2">
                    <form onSubmit={handleSubmit(login)} className="mb-2">
                        <div className="mb-2">
                            <input type="text" className={"form-control" + (errors.email ? " is-invalid" : "")}
                                   placeholder="Yourfantasy@freeunico.rn"{...register("email", {required: "Email Address is required"})} />
                            {errors.email && <p className="invalid-feedback" role="alert">{errors.email?.message}</p>}
                        </div>
                        <div className="mb-2">
                            <input type="password" className={"form-control" + (errors.password ? " is-invalid" : "")}
                                   placeholder="yoursecret123"{...register("password", {required: "Password is required"})} />
                            {errors.password && <p className="invalid-feedback" role="alert">{errors.password?.message}</p>}
                        </div>
                        <div >
                            { authError && <p role="alert">{authError}</p> }
                        </div>
                        <div className="d-flex justify-content-center">
                            <button className="btn btn-primary" type="submit">Sign In</button>
                        </div>
                    </form>
                    <p className="text-center">Don't have a user account? <Link href='/signup'>Click here</Link> to sign up!</p>
                </div>
        </Loginlayout>

    )
}

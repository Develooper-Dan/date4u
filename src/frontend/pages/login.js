import Link from "next/link";
import Loginlayout from "../components/Loginlayout";
import {signIn, useSession} from "next-auth/react"


export default function Login(){

        return (
            <Loginlayout>
                <div className="bounds">
                    <div className="grid-33 centered">
                        <h1>Sign In</h1>
                        <div>

                            <form onSubmit={(event) => {
                                event.preventDefault();
                                signIn("credentials", { email: event.target.email.value, password: event.target.password.value ,redirect: false}).then((res) => {console.log(res)})
                            }}>
                                <div>
                                    <input id="email" name="email" type="text"
                                           placeholder="Email Address"/>
                                </div>
                                <div>
                                    <input id="password" name="password" type="password" placeholder="Password"/>
                                </div>
                                <div className="grid-100 pad-bottom">
                                    <button className="button" type="submit">Sign In</button>
                                    <button className="button button-secondary"> Cancel</button>
                                </div>
                            </form>
                        </div>
                        <p>&nbsp;</p>
                        <p>Don't have a user account? <Link href='/signup'>Click here</Link> to sign up!</p>
                    </div>
                </div>
            </Loginlayout>
        )
}

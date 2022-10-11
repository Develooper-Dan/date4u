import NextAuth from "next-auth"
import CredentialsProvider from "next-auth/providers/credentials"

export const authOptions = {
  providers: [
  CredentialsProvider({
   name: "email",
/*   credentials: {
    email: {label: "Email", type: "text", placeholder: "your@email.address"},
    password: {label: "Password", type: "password",}
   },*/
   async authorize(credentials, request) {
     const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/login`, {
      method: "POST",
      body: JSON.stringify(credentials),
      headers: { "Accept": "application/json"}
     })
     const user = await res.json();

       console.log(user);
     if (res.ok && user) {
      return user
     }
     return null
   }
  })
 ],
 pages: {signIn: '../../login',}
}
export default NextAuth(authOptions)

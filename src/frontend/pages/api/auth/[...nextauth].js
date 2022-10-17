import NextAuth from "next-auth"
import CredentialsProvider from "next-auth/providers/credentials"

export const authOptions = {
  providers: [
  CredentialsProvider({
   name: "credentials",

   async authorize(credentials) {
     const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/login`, {
      method: "POST",
      body: JSON.stringify(credentials),
      headers: { "Content-Type": "application/json"}
     })
     const user = await res.json();

     if (res.ok && user) {
      return user
     }
     return null
   }
  })
 ],
    pages: {signIn: '../../login',},
    callbacks: {
        async jwt({ token, user }) {
            if(user){

                token.accessToken = user.jwt;
                token.user = user;
                console.log(token)
                return token;
            }

        },
        async session({ session, token, user }) {
            if(token || user){
                console.log("yay")
                session.accessToken = token.accessToken
                return session
            }

        }
    }
}
export default NextAuth(authOptions)

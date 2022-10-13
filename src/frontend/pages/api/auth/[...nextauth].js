import NextAuth from "next-auth"
import CredentialsProvider from "next-auth/providers/credentials"

export const authOptions = {
  providers: [
  CredentialsProvider({
   name: "credentials",

   async authorize(credentials, request) {
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
                /*console.log(user)*/
                token.jwt = user.jwt;
                token.id = user.id;
/*            if (user) {
                token.accessToken = account.access_token
                token.id = profile.id
            }*/
            return token;
        },
        async session({ session, token, user }) {
            session.user = user;
            /*console.log(token.jwt)*/
            return session
        }
    }
}
export default NextAuth(authOptions)

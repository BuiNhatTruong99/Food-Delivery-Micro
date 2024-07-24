import { NextAuthOptions } from 'next-auth';
import NextAuth from 'next-auth/next';
import GoogleProvider from 'next-auth/providers/google';

const authOptions: NextAuthOptions = {
  session: {
    strategy: 'jwt'
  },
  providers: [
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID!,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET!
    })
  ],
  callbacks: {
    async signIn({ user, account, email, profile }) {
      if (user) {
        console.log({
          user,
          account,
          email,
          profile
        });
        return true;
      } else {
        return false;
      }
    }
  }
};

export const handler = NextAuth(authOptions);

export { handler as GET, handler as POST };

import { IGoogleSignIn } from '@/domain';
import { googleSignInService } from '@/services';
import { NextAuthOptions } from 'next-auth';
import NextAuth from 'next-auth/next';
import GoogleProvider from 'next-auth/providers/google';
import { signIn } from 'next-auth/react';

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
    // async signIn({ account }) {
    //   const values: IGoogleSignIn = {
    //     token: account?.id_token!,
    //     provider: account?.provider!
    //   };

    //   try {
    //     const response = await googleSignInService(values);

    //     if (account && response.data) {
    //       account.access_token = response.data?.accessToken;
    //       account.refresh_token = response.data?.refreshToken;
    //     } else {
    //       signIn('google');
    //       return false;
    //     }
    //   } catch (error) {
    //     signIn('google');
    //     return false;
    //   }

    //   return true;
    // },
    async jwt({ token, account, user }) {
      if (account && user) {
        token.name = user.name;
        token.email = user.email;
        token.id_token = account.id_token;
        token.provider = account.provider;
      }
      return token;
    },
    async session({ session, token }) {
      if (token.id_token && session.user) {
        try {
          const values: IGoogleSignIn = {
            token: token.id_token as string,
            provider: token?.provider as string
          };
          const response = await googleSignInService(values);
          if (response.data) {
            session.user.userId = response.data.id;
            session.user.userEmail = response.data.email;
            session.user.role = response.data.role;
            session.user.isEmailVerified = response.data.isEmailVerified;
            session.user.accessToken = response.data.accessToken;
            session.user.refreshToken = response.data.refreshToken;
          }
        } catch (error) {
          console.log('error 2');
        }
      }

      return {
        ...session
        // ...session,
        // user: {
        //   ...session.user,
        //   accessToken: token.accessToken,
        //   refreshToken: token.refreshToken
        // }
      };
    }
  }
};

export const handler = NextAuth(authOptions);

export { handler as GET, handler as POST };

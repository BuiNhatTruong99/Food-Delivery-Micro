'use client';

import { AUTH_STORAGE_KEY } from '@/constant';
import { Button } from '@mui/material';
import { signOut, useSession } from 'next-auth/react';
import Image from 'next/image';

const Home = () => {
  const { data: session } = useSession();

  const handleSignOut = async () => {
    await signOut({ callbackUrl: '/sign-in' });
    localStorage.removeItem(AUTH_STORAGE_KEY);
  };

  return (
    <div className="">
      <h1>{session?.user?.name}</h1>
      <h1>{session?.expires}</h1>
      <h1>{session?.user?.email}</h1>
      <h1>{session?.user?.accessToken}</h1>
      <h1>{session?.user?.refreshToken}</h1>

      <Image
        src={session?.user?.image as string}
        alt="avatar"
        width={100}
        height={100}
      />
      <Button onClick={handleSignOut}>Out</Button>
    </div>
  );
};

export default Home;

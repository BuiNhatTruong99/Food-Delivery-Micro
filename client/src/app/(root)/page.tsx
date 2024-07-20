'use client';

import { Button } from '@mui/material';
import { signOut, useSession } from 'next-auth/react';
import Image from 'next/image';

const Home = () => {
  const { data: session } = useSession();
  return (
    <div className="">
      <h1>{session?.user?.name}</h1>
      <h1>{session?.user?.email}</h1>

      <h1>{session?.user?.image}</h1>
      <Image
        src={session?.user?.image as string}
        alt="avatar"
        width={100}
        height={100}
      />
      <Button onClick={() => signOut()}>Out</Button>
    </div>
  );
};

export default Home;

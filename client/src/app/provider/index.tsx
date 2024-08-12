'use client';

import useAuthenticated from '@/hooks/useAuthenticated';
import { SnackbarUtilsConfiguration } from '@/hooks/useMessageRef';
import ThemeProvider from '@/themes';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { SessionProvider } from 'next-auth/react';
import { SnackbarProvider } from 'notistack';
import { ReactNode, useState } from 'react';

interface ClientSessionProviderProps {
  children: ReactNode;
  session: any;
}

export const useAuthProvider = () => {
  const [client] = useState(new QueryClient());
  useAuthenticated();
  return { client };
};

export const ClientSessionProvider = ({
  children,
  session
}: ClientSessionProviderProps) => {
  return <SessionProvider session={session}>{children}</SessionProvider>;
};

export default function Providers({ children }: { children: React.ReactNode }) {
  const { client } = useAuthProvider();

  return (
    <SnackbarProvider preventDuplicate maxSnack={1}>
      <ThemeProvider>
        <QueryClientProvider client={client}>{children}</QueryClientProvider>
      </ThemeProvider>
      <SnackbarUtilsConfiguration />
    </SnackbarProvider>
  );
}

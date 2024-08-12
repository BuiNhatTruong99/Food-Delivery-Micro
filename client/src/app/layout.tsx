import '@styles/globals.css';
import MainLayout from './layouts/MainLayout';
import Providers, { ClientSessionProvider } from './provider';
import { getServerSession } from 'next-auth';

export default async function RootLayout({
  children
}: {
  children: React.ReactNode;
}) {
  const session = await getServerSession();

  return (
    <html lang="en">
      <head>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1"></meta>
      </head>
      <body>
        <Providers>
          <ClientSessionProvider session={session}>
            <MainLayout>{children}</MainLayout>
          </ClientSessionProvider>
        </Providers>
      </body>
    </html>
  );
}

export * from './layouts/MainLayout';

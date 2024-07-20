import { PageTransition } from '@/components/molecule';
import SessionProvider from '@/components/SessProvider';
import { getServerSession } from 'next-auth';

export default async function RootLayout({
  children
}: {
  children: React.ReactNode;
}) {
  const session = await getServerSession();

  return (
    <PageTransition>
      <SessionProvider session={session}>{children}</SessionProvider>
    </PageTransition>
  );
}

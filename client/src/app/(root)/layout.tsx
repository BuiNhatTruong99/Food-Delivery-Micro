import { PageTransition } from '@/components/molecule';

export default async function RootLayout({
  children
}: {
  children: React.ReactNode;
}) {
  return <PageTransition>{children}</PageTransition>;
}

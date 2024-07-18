import { PageTransition } from '@/components/molecule';

export default function RootLayout({
  children
}: {
  children: React.ReactNode;
}) {
  return <PageTransition>{children}</PageTransition>;
}

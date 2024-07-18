import MainLayout from './layouts/MainLayout';
import Providers from './provider';
import '@styles/globals.css';

export default function RootLayout({
  children
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <head>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1"></meta>
      </head>
      <body>
        <Providers>
          <MainLayout>{children}</MainLayout>
        </Providers>
      </body>
    </html>
  );
}

export * from './layouts/MainLayout';
